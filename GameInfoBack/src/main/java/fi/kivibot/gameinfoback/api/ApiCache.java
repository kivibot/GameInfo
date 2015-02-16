package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.ConfigManager;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.CurrentGame;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.Participant;
import fi.kivibot.gameinfoback.api.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.structures.RankedStats;
import fi.kivibot.gameinfoback.api.structures.RunePages;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class ApiCache {

    private class Result<A> {

        private final A value;
        private final Exception exception;

        public Result(A value, Exception exception) {
            this.value = value;
            this.exception = exception;
        }

        public A getValue() {
            return value;
        }

        public Exception getException() {
            return exception;
        }

    }

    private final Map<Long, CurrentGame> processingMap = new HashMap<>();
    private final Map<Long, CurrentGame> processedMap = new HashMap<>();
    private final Map<Long, Long> processingSummonersMap = new HashMap<>();
    private final Map<Long, Summoner> summonerMap = new HashMap<>();
    private final Map<Long, List<League>> leaguesMap = new HashMap<>();
    private final Map<Long, RunePages> runePagesMap = new HashMap<>();
    private final Map<Long, RankedStats> rankedStatsMap = new HashMap<>();
    private final Map<Long, List<PlayerStatsSummary>> playerStatsMap = new HashMap<>();

    private final ExecutorService pool = Executors.newFixedThreadPool((int) ConfigManager.getDefault().getLong("poolSize", Runtime.getRuntime().availableProcessors()));

    private final ApiHandler api;

    public ApiCache(ApiHandler api) {
        this.api = api;
    }

    public void submitGame(CurrentGame cg) {
        synchronized (this) {
            if (processingMap.containsKey(cg.getGameId()) || processedMap.containsKey(cg.getGameId())) {
                return;
            }
            processingMap.put(cg.getGameId(), cg);
        }
        pool.submit(() -> {
            List<String> names = new ArrayList<>();
            for (Participant p : cg.getParticipants()) {
                names.add(p.getSummonerName());
            }
            Map<String, Summoner> sm;
            try {
                sm = api.getSummonersByNames(names);
            } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            List<Long> ids = new ArrayList<>();
            for (Summoner s : sm.values()) {
                Summoner o = getSummoner(s.getId());
                if (o == null || s.getRevisionDate() != o.getRevisionDate()) {
                    summonerMap.put(s.getId(), s);
                    ids.add(s.getId());
                }
            }
            if (!ids.isEmpty()) {
                boolean[] b = new boolean[1];
                b[0] = false;
                pool.submit(() -> {
                    Map<String, List<League>> lm;
                    try {
                        lm = api.getLeaguesEntry(ids);
                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    for (Map.Entry<String, List<League>> e : lm.entrySet()) {
                        leaguesMap.put(Long.valueOf(e.getKey()), e.getValue());
                    }
                    synchronized (b) {
                        if (b[0]) {
                            processedMap.put(cg.getGameId(), cg);
                            processingMap.remove(cg.getGameId());
                            synchronized (cg) {
                                cg.notifyAll();
                            }
                        } else {
                            b[0] = true;
                        }
                    }
                });
                pool.submit(() -> {
                    Map<String, RunePages> rm;
                    try {
                        rm = api.getRunes(ids);
                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    for (Map.Entry<String, RunePages> e : rm.entrySet()) {
                        runePagesMap.put(Long.valueOf(e.getKey()), e.getValue());
                    }
                    synchronized (b) {
                        if (b[0]) {
                            processedMap.put(cg.getGameId(), cg);
                            processingMap.remove(cg.getGameId());
                            synchronized (cg) {
                                cg.notifyAll();
                            }
                        } else {
                            b[0] = true;
                        }
                    }
                });
                for (Long id : ids) {
                    Long l = id;
                    processingSummonersMap.put(l, l);
                    boolean[] b2 = new boolean[1];
                    b2[0] = false;
                    pool.submit(() -> {
                        RankedStats rs;
                        try {
                            rs = api.getRankedStats(id);
                        } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                        rankedStatsMap.put(id, rs);
                        synchronized (b2) {
                            if (b2[0]) {
                                processingSummonersMap.remove(l);
                                synchronized (l) {
                                    l.notifyAll();
                                }
                            } else {
                                b2[0] = true;
                            }
                        }
                    });
                    pool.submit(() -> {
                        List<PlayerStatsSummary> pss;
                        try {
                            pss = api.getStatsSummary(id);
                        } catch (IOException | RequestException | RateLimitException | RitoException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                        playerStatsMap.put(id, pss);
                        synchronized (b2) {
                            if (b2[0]) {
                                processingSummonersMap.remove(l);
                                synchronized (l) {
                                    l.notifyAll();
                                }
                            } else {
                                b2[0] = true;
                            }
                        }
                    });
                }
            }
        });
    }

    public CurrentGame getGame(long id) {
        CurrentGame cg = processingMap.get(id);
        if (cg != null) {
            synchronized (cg) {
                try {
                    cg.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return cg;
        }
        return processedMap.get(id);
    }

    public Summoner getSummoner(long id) {
        return summonerMap.get(id);
    }

    public List<League> getLeagues(long id) {
        return leaguesMap.get(id);
    }

    public RunePages getRunePages(long id) {
        return runePagesMap.get(id);
    }

    public RankedStats getRankedStats(long id) {
        Long l = processingSummonersMap.get(id);
        if (l != null) {
            synchronized (l) {
                try {
                    l.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rankedStatsMap.get(id);
    }

    public List<PlayerStatsSummary> getPlayerStats(long id) {
        Long l = processingSummonersMap.get(id);
        if (l != null) {
            synchronized (l) {
                try {
                    l.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return playerStatsMap.get(id);
    }
    
}
