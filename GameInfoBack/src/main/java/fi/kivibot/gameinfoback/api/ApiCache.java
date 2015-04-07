package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.ConfigManager;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.currentgame.CurrentGame;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.currentgame.Participant;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 * 
 * Fun platform stuff
 */
public class ApiCache {
    
    private static class Pair<T0, T1> {
        
        private final T0 a;
        private final T1 b;
        
        public Pair(T0 a, T1 b) {
            this.a = a;
            this.b = b;
        }
        
        public T0 getA() {
            return a;
        }
        
        public T1 getB() {
            return b;
        }
        
    }
    
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
    
    private final Map<Long, CurrentGame> cgMap = new HashMap<>();
    
    private final Map<Long, Summoner> summonerMap2 = new HashMap<>();
    private final Map<Long, ApiFuture<Summoner>> summonerMap = new HashMap<>();
    private final Map<Long, ApiFuture<List<League>>> leaguesMap = new HashMap<>();
    private final Map<Long, List<League>> leaguesMap2 = new HashMap<>();
    private final Map<Long, ApiFuture<RunePages>> runePagesMap = new HashMap<>();
    private final Map<Long, RunePages> runePagesMap2 = new HashMap<>();
    private final Map<Long, Future<RankedStats>> rankedStatsMap = new HashMap<>();
    private final Map<Long, Future<List<PlayerStatsSummary>>> playerStatsMap = new HashMap<>();
    
    private final ExecutorService pool = Executors.newFixedThreadPool((int) ConfigManager.getDefault().getLong("poolSize", Runtime.getRuntime().availableProcessors()));
    
    private final ApiHandler api;
    private final Platform platform;
    
    public ApiCache(ApiHandler api, Platform p) {
        this.api = api;
        this.platform = p;
    }
    
    public void submitGame(CurrentGame cg) {
        synchronized (this) {
            if (cgMap.containsKey(cg.getGameId())) {
                return;
            }
            cgMap.put(cg.getGameId(), cg);
            for (Participant p : cg.getParticipants()) {
                summonerMap.put(p.getSummonerId(), new ApiFuture<>());
                runePagesMap.put(p.getSummonerId(), new ApiFuture<>());
                leaguesMap.put(p.getSummonerId(), new ApiFuture<>());
            }
        }
        pool.submit(() -> {
            List<String> names = new ArrayList<>();
            for (Participant p : cg.getParticipants()) {
                names.add(p.getSummonerName());
            }
            Map<String, Summoner> sm;
            try {
                sm = api.getSummonersByNames(platform, names);
            } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            List<Long> ids = new ArrayList<>();
            for (Summoner s : sm.values()) {
                Summoner o = summonerMap2.get(s.getId());
                if (o == null || s.getRevisionDate() != o.getRevisionDate()) {
                    summonerMap.get(s.getId()).set(s);
                    summonerMap2.put(s.getId(), s);
                    ids.add(s.getId());
                } else {
                    runePagesMap.get(s.getId()).set(runePagesMap2.get(s.getId()));
                    leaguesMap.get(s.getId()).set(leaguesMap2.get(s.getId()));
                }
            }
            if (!ids.isEmpty()) {
                pool.submit(() -> {
                    Map<String, List<League>> lm;
                    try {
                        lm = api.getLeaguesEntry(platform, ids);
                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        for (long l : ids) {
                            leaguesMap.get(l).set(null);
                        }
                        return;
                    }
                    for (long l : ids) {
                        leaguesMap.get(l).set(lm.get("" + l));
                    }
                });
                pool.submit(() -> {
                    Map<String, RunePages> rm;
                    try {
                        rm = api.getRunes(platform, ids);
                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        for (long l : ids) {
                            runePagesMap.get(l).set(null);
                        }
                        return;
                    }
                    for (long l : ids) {
                        runePagesMap.get(l).set(rm.get("" + l));
                    }
                });
                for (Long id : ids) {
                    rankedStatsMap.put(id, pool.submit(() -> {
                        RankedStats rs = null;
                        try {
                            rs = api.getRankedStats(platform, id);
                        } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return rs;
                    }));
                    playerStatsMap.put(id, pool.submit(() -> {
                        List<PlayerStatsSummary> pss;
                        try {
                            pss = api.getStatsSummary(platform, id);
                        } catch (IOException | RequestException | RateLimitException | RitoException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            return null;
                        }
                        return pss;
                    }));
                }
            }
        });
    }
    
    public CurrentGame getGame(long id) {
        return cgMap.get(id);
    }
    
    public Summoner getSummoner(long id) {
        Future<Summoner> f = summonerMap.get(id);
        if (f != null) {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public List<League> getLeagues(long id) {
        Future<List<League>> f = leaguesMap.get(id);
        if (f != null) {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public RunePages getRunePages(long id) {
        Future<RunePages> f = runePagesMap.get(id);
        if (f != null) {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public RankedStats getRankedStats(long id) {
        Future<RankedStats> f = rankedStatsMap.get(id);
        if (f != null) {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public List<PlayerStatsSummary> getPlayerStats(long id) {
        Future<List<PlayerStatsSummary>> f = playerStatsMap.get(id);
        if (f != null) {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}
