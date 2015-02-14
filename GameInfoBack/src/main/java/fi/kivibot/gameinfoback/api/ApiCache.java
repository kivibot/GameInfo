package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.ConfigManager;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.RankedStats;
import fi.kivibot.gameinfoback.api.structures.RunePages;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pelottavaa rinnakkaisuuspurkkaa :D
 *
 * @author Nicklas
 */
public class ApiCache {

    private class SummonerResult {

        private final Summoner summoner;
        private final RankedStats rankedStats;
        private final Exception exception;

        public SummonerResult(Summoner summoner, RankedStats rankedStats, Exception exception) {
            this.summoner = summoner;
            this.rankedStats = rankedStats;
            this.exception = exception;
        }

        public Summoner getSummoner() {
            return summoner;
        }

        public RankedStats getRankedStats() {
            return rankedStats;
        }

        public Exception getException() {
            return exception;
        }
    }

    private class SingleResult<T> {

        private final T result;
        private final Exception exception;

        public SingleResult(T result, Exception exception) {
            this.result = result;
            this.exception = exception;
        }

        public T getResult() {
            return result;
        }

        public Exception getException() {
            return exception;
        }
    }

    private final ApiHandler api;
    private final Map<Long, Summoner> summonerMap = new HashMap<>();
    private final Map<Long, List<League>> leagueMap = new HashMap<>();
    private final Map<Long, RankedStats> rankeStatsMap = new HashMap<>();
    private final Map<Long, RunePages> runePagesMap = new HashMap<>();
    private final ExecutorService pool = Executors.newFixedThreadPool((int) ConfigManager.getDefault().getLong("poolSize", Runtime.getRuntime().availableProcessors()));

    public ApiCache(ApiHandler api) {
        this.api = api;
    }

    public void updateSummoners(List<String> summoners)
            throws IOException, RateLimitException, RequestException, RitoException {
        Map<String, Summoner> sumMap = api.getSummonersByNames(summoners);
        List<Long> ids = new ArrayList<>();
        List<Future<SummonerResult>> tasks = new ArrayList<>();
        long s = System.currentTimeMillis();
        Future<SingleResult<Map<String, List<League>>>> leaguesTask;
        Future<SingleResult<Map<String, RunePages>>> runesTask;
        synchronized (this) {
            for (Map.Entry<String, Summoner> e : sumMap.entrySet()) {
                Summoner os = summonerMap.get(e.getValue().getId());
                if (os == null || os.getRevisionDate() != e.getValue().getRevisionDate()) {
                    tasks.add(pool.submit(() -> {
                        RankedStats rs = null;
                        try {
                            rs = api.getRankedStats(e.getValue().getId());
                        } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                            return new SummonerResult(e.getValue(), null, ex);
                        }
                        return new SummonerResult(e.getValue(), rs, null);
                    }));
                    ids.add(e.getValue().getId());
                }
            }
            if (ids.isEmpty()) {
                return;
            }
            leaguesTask = pool.submit(() -> {
                Map<String, List<League>> lm = null;
                try {
                    lm = api.getLeaguesEntry(ids);
                } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                    return new SingleResult<>(null, ex);
                }
                return new SingleResult<>(lm, null);
            });
            runesTask = pool.submit(() -> {
                Map<String, RunePages> runePages = null;
                try {
                    runePages = api.getRunes(ids);
                } catch (IOException | RateLimitException | RequestException | RitoException ex) {
                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
                    return new SingleResult<>(null, ex);
                }
                return new SingleResult<>(runePages, null);
            });
        }
        SingleResult<Map<String, List<League>>> leares = null;
        SingleResult<Map<String, RunePages>> runres = null;
        try {
            leares = leaguesTask.get();
            runres = runesTask.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            //HANDLE!
            return;
        }
        if (leares.getException() != null) {
            throwe(leares.getException());
        }
        if (runres.getException() != null) {
            throwe(runres.getException());
        }
        List<SummonerResult> sumress = new ArrayList<>();
        for (Future t : tasks) {
            try {
                SummonerResult sr = (SummonerResult) t.get();
                if (sr.getException() != null) {
                    throwe(sr.exception);
                }
                sumress.add(sr);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (SummonerResult rs : sumress) {
            summonerMap.put(rs.getSummoner().getId(), rs.getSummoner());
            rankeStatsMap.put(rs.getSummoner().getId(), rs.getRankedStats());
        }
        for (Map.Entry<String, List<League>> e : leares.getResult().entrySet()) {
            leagueMap.put(Long.valueOf(e.getKey()), e.getValue());
        }
        for (Map.Entry<String, RunePages> e : runres.getResult().entrySet()) {
            runePagesMap.put(e.getValue().getSummonerId(), e.getValue());
        }
    }

    public RankedStats getRankedStats(long id) {
        return rankeStatsMap.get(id);
    }

    public List<League> getLeagueEntries(long id) {
        return leagueMap.get(id);
    }

    public RunePages getRunes(long id) {
        return runePagesMap.get(id);
    }

    private void throwe(Exception e) throws IOException, RateLimitException, RequestException, RitoException {
        if (e instanceof IOException) {
            throw (IOException) e;
        }
        if (e instanceof RateLimitException) {
            throw (RateLimitException) e;
        }
        if (e instanceof RequestException) {
            throw (RequestException) e;
        }
        if (e instanceof RitoException) {
            throw (RitoException) e;
        }
    }

}
