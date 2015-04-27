package fi.kivibot.gameinfoback.api.old;

import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.ConfigManager;
import fi.kivibot.gameinfoback.DatabaseHandler;
import fi.kivibot.gameinfoback.api.old.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.old.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.old.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.old.structures.ChampionStats;
import fi.kivibot.gameinfoback.api.old.structures.currentgame.CurrentGame;
import fi.kivibot.gameinfoback.api.old.structures.League;
import fi.kivibot.gameinfoback.api.old.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.currentgame.Participant;
import fi.kivibot.gameinfoback.api.old.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.old.structures.RankedStats;
import fi.kivibot.gameinfoback.api.old.structures.RunePages;
import fi.kivibot.gameinfoback.api.old.structures.Summoner;
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
import java.util.concurrent.locks.Lock;
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

    private final ExecutorService pool = Executors.newFixedThreadPool((int) ConfigManager.getDefault().getLong("poolSize", Runtime.getRuntime().availableProcessors()));
    private final ApiHandler api;
    private final Platform platform;
    private final DatabaseHandler db;
    private final Cacher cache = new Cacher();
    private final Map<String, ApiLock> amap = new HashMap<>();

    public ApiCache(ApiHandler api, Platform p, DatabaseHandler dbh) {
        this.api = api;
        this.platform = p;
        this.db = dbh;
        cache.start();
    }

    public void submitGame(CurrentGame cg) throws IOException, RateLimitException, RequestException, RitoException {
        cache.set("cg-" + cg.getGameId(), cg);

        List<Long> sl = new ArrayList<>();
        for (Participant p : cg.getParticipants()) {
            sl.add(p.getSummonerId());
        }
        List<Summoner> participants = db.getUpdated(platform, sl);
        List<String> upl = new ArrayList<>();
        List<Long> upl2 = new ArrayList<>();

        cg.getParticipants().stream().filter((p) -> {
            for (Summoner s : participants) {
                if (s.getId() == p.getSummonerId()) {
                    return false;
                }
            }
            return true;
        }).forEach((p) -> {
            upl.add(p.getSummonerName());
            upl2.add(p.getSummonerId());
        });

        if (!upl.isEmpty()) {
            ApiLock af = new ApiLock();
            try {
                af.lock();
            } catch (InterruptedException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
            amap.put("ranks-" + cg.getGameId(), af);
            pool.submit(() -> {
                Map<String, Summoner> sm;
                try {
                    sm = api.getSummonersByNames(platform, upl);
                    sm.values().forEach((p) -> {
                        cache.set("s-" + p.getId(), p);
                        db.updateSummoner(platform, p);
                        ApiLock af2 = new ApiLock();
                        try {
                            af2.lock();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        amap.put("stats-" + p.getId(), af2);
                        pool.submit(() -> {
                            RankedStats rs;
                            try {
                                rs = api.getRankedStats(platform, p.getId());
                                db.updateRankedStats(platform, rs, p);
                                cache.set("rs-" + p.getId(), rs);
                            } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            List<PlayerStatsSummary> pssl;
                            try {
                                pssl = api.getStatsSummary(platform, p.getId());
                                for (PlayerStatsSummary pss : pssl) {
                                    db.updatePlayerStatsSummary(platform, pss, p);
                                }
                            } catch (IOException | RequestException | RateLimitException | RitoException ex) {
                                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            af2.unlock();
                            amap.remove("stats-" + p.getId());
                        });
                    });
                } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }

                participants.forEach((p) -> {
                    cache.set("s-" + p.getId(), p);
                });

                Map<String, RunePages> rm;
                try {
                    rm = api.getRunes(platform, upl2);
                    rm.values().forEach((rp) -> {
                        db.updateRunePages(platform, rp, new Summoner(rp.getSummonerId()));
                        cache.set("rps-" + rp.getSummonerId(), rp);
                    });
                } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }

                Map<String, List<League>> lm;
                try {
                    lm = api.getLeaguesEntry(platform, upl2);
                    for (long i : upl2) {
                        List<League> ls = lm.get("" + i);
                        for (League l : ls) {
                            if (l.getQueue().equals("RANKED_SOLO_5x5")) {
                                for (LeagueEntry le : l.getEntries()) {
                                    if (le.getPlayerOrTeamId().equals(i + "")) {
                                        SoloLeagueEntry sle = new SoloLeagueEntry(l.getTier(), le.getDivision(), le.isFreshBlood(),
                                                le.isHotStreak(), le.isInactive(), le.isVeteran(), le.getLeaguePoints(), le.getLosses(),
                                                le.getMiniSeries(), le.getPlayerOrTeamId(), le.getPlayerOrTeamName(), le.getWins());
                                        cache.set("sl-" + i, sle);
                                        db.updateSoloLeagueEntry(platform, l.getTier(), le, new Summoner(i));
                                    }
                                }
                                break;
                            }
                        }
                    }
                } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                }
                af.unlock();
                amap.remove("ranks-" + cg.getGameId());
            });
        }

    }

    public void waitFor(String key) {
        ApiLock af = amap.get(key);
        if (af != null) {
            System.out.println("wait " + key);
            try {
                af.waitForUnlock(20, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("waited " + key);
            amap.remove(key);
        }
    }

//    public void submitGame_old(CurrentGame cg) {
//        synchronized (this) {
//            if (cgMap.containsKey(cg.getGameId())) {
//                return;
//            }
//            cgMap.put(cg.getGameId(), cg);
//            List<Long> sl = new ArrayList<>();
//            for (Participant p : cg.getParticipants()) {
//                sl.add(p.getSummonerId());
//            }
//            for (Long sid : db.getOutdated(sl)) {
//                System.out.println("U: " + sid);
//                summonerMap.put(sid, new ApiFuture<>());
//                runePagesMap.put(sid, new ApiFuture<>());
//                leaguesMap.put(sid, new ApiFuture<>());
//            }
//            db.getSummoners(sl);
//        }
//        pool.submit(() -> {
//            List<String> names = new ArrayList<>();
//            for (Participant p : cg.getParticipants()) {
//                names.add(p.getSummonerName());
//            }
//            Map<String, Summoner> sm;
//            try {
//                sm = api.getSummonersByNames(platform, names);
//            } catch (IOException | RateLimitException | RequestException | RitoException ex) {
//                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                return;
//            }
//            List<Long> ids = new ArrayList<>();
//            for (Summoner s : sm.values()) {
//                Summoner o = summonerMap2.get(s.getId());
//                if (o == null || s.getRevisionDate() != o.getRevisionDate()) {
//                    summonerMap.get(s.getId()).set(s);
//                    summonerMap2.put(s.getId(), s);
//                    db.updateSummoner(platform, s);
//                    ids.add(s.getId());
//                } else {
//                    runePagesMap.get(s.getId()).set(runePagesMap2.get(s.getId()));
//                    leaguesMap.get(s.getId()).set(leaguesMap2.get(s.getId()));
//                }
//            }
//            if (!ids.isEmpty()) {
//                pool.submit(() -> {
//                    Map<String, List<League>> lm;
//                    try {
//                        lm = api.getLeaguesEntry(platform, ids);
//                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
//                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                        for (long l : ids) {
//                            leaguesMap.get(l).set(null);
//                        }
//                        return;
//                    }
//                    for (long l : ids) {
//                        leaguesMap.get(l).set(lm.get("" + l));
//                    }
//                });
//                pool.submit(() -> {
//                    Map<String, RunePages> rm;
//                    try {
//                        rm = api.getRunes(platform, ids);
//                    } catch (IOException | RateLimitException | RequestException | RitoException ex) {
//                        Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                        for (long l : ids) {
//                            runePagesMap.get(l).set(null);
//                        }
//                        return;
//                    }
//                    for (long l : ids) {
//                        runePagesMap.get(l).set(rm.get("" + l));
//                        db.updateRunePages(platform, rm.get("" + l), new Summoner(l));
//                    }
//                });
//                for (Long id : ids) {
//                    rankedStatsMap.put(id, pool.submit(() -> {
//                        RankedStats rs = null;
//                        try {
//                            rs = api.getRankedStats(platform, id);
//                        } catch (IOException | RateLimitException | RequestException | RitoException ex) {
//                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        for (ChampionStats cs : rs.getChampions()) {
//                            db.updateChampionStats(platform, cs, new Summoner(id));
//                        }
//                        return rs;
//                    }));
//                    playerStatsMap.put(id, pool.submit(() -> {
//                        List<PlayerStatsSummary> pss;
//                        try {
//                            pss = api.getStatsSummary(platform, id);
//                        } catch (IOException | RequestException | RateLimitException | RitoException ex) {
//                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                            return null;
//                        }
//                        if (pss != null) {
//                            for (PlayerStatsSummary p : pss) {
//                                db.updatePlayerStatsSummary(platform, p, new Summoner(id));
//                            }
//                        }
//                        return pss;
//                    }));
//                }
//            }
//        });
//    }
    public CurrentGame getGame(long id) {
        CurrentGame cg = cache.get("cg-" + id);
        return cg;
    }

    public Summoner getSummoner(long id) {
        Summoner s = cache.get("s-" + id);
        if (s == null) {
            List<Long> l = new ArrayList<>();
            l.add(id);
            List<Summoner> sl = db.getSummoners(platform, l);
            if (!sl.isEmpty()) {
                s = sl.get(0);
            }
            if (s == null) {
                // use api
            } else {
                cache.set("s-" + id, s);
            }
        }
        return s;
    }

    public SoloLeagueEntry getSoloLeagueEntry(long id) {
        SoloLeagueEntry sle = cache.get("sl-" + id);
        System.out.println(id);
        if (sle == null) {
            sle = db.getSoloLeagueEntry(platform, id);
            if (sle == null) {
                //api
            } else {
                cache.set("sl-" + id, sle);
            }
        }
        return sle;
    }

    public RunePages getRunePages(long id) {
        RunePages rps = cache.get("rps-" + id);
        if (rps == null) {
            rps = db.getRunePages(platform, id);
            System.out.println(rps);
            if (rps == null) {
                //api
            } else {
                cache.set("rps-" + id, rps);
            }
        }
        return rps;
    }

    public RankedStats getRankedStats(long id) {
        RankedStats rs = cache.get("rs-" + id);
        if (rs == null) {
            rs = db.getRankedStats(platform, id);
            if (rs == null) {
                //api
            } else {
                cache.set("rs-" + id, rs);
            }
        }
        return rs;
    }

    public List<PlayerStatsSummary> getPlayerStats(long id) {
        List<PlayerStatsSummary> ps = cache.get("ps-" + id);
        if (ps == null) {
            ps = db.getAllPlayerStatsSummaries(platform, id);
            if (ps == null) {
                //api
            } else {
                cache.set("ps-" + id, ps);
            }
        }
        return ps;
    }

}
