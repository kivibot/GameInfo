package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.storage.DatabaseException;
import fi.kivibot.gameinfoback.storage.DataManager;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.RiotAPI;
import fi.kivibot.gameinfoback.api.exception.RateLimitException;
import fi.kivibot.gameinfoback.api.exception.RequestException;
import fi.kivibot.gameinfoback.api.exception.RiotSideException;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameInfo;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameParticipant;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class Updater {

    private interface APIcaller {

        public void run() throws RiotSideException, RequestException, RateLimitException, IOException;
    }

    private class Container<T> {

        private T value;

        public Container(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

    }

    private static final long update_threshold = 60 * 1000;
    private static final long update_threshold2 = 5 * 60 * 1000;

    private final DataManager dataManager;
    private final RiotAPI api;
    private final RateLimitPolicy rlp;

    public Updater(DataManager dataManager, RiotAPI api, RateLimitPolicy rlp) {
        this.dataManager = dataManager;
        this.api = api;
        this.rlp = rlp;
    }

    public UpdateResult updateSummoners(Platform p, List<Long> ids) throws RequestException, RateLimitException, IOException, DatabaseException {
        if (ids.isEmpty()) {
            System.out.println("ret1");
            return new UpdateResult(new ArrayList<>(), new ArrayList<>(), false, false);
        }
        Map<Long, Summoner> cachedSums = dataManager.getSummoners(p, ids);

        List<Long> idsToUpdate = new ArrayList<>(ids.size());

        for (long id : ids) {
            if (cachedSums.containsKey(id)) {
                Summoner cachedSum = cachedSums.get(id);
                if (cachedSum.getLastUpdated() < System.currentTimeMillis() - update_threshold) {
                    idsToUpdate.add(id);
                }
            } else {
                idsToUpdate.add(id);
            }
        }

        Container<Boolean> rateLimited = new Container<>(false);
        Container<Boolean> riotSide = new Container<>(false);

        Map<String, Summoner> summoners = new HashMap<>();

        apiCall(rateLimited, riotSide, () -> {
            summoners.putAll(api.summoner.getSummonersByIds(p, idsToUpdate));
        });

        if (summoners.isEmpty()) {
            System.out.println("ret2: " + idsToUpdate.size());
            return new UpdateResult(new ArrayList<>(), new ArrayList<>(), rateLimited.getValue(), riotSide.getValue());
        }

        List<Summoner> rsids = new ArrayList<>();
        List<Long> leids = new ArrayList<>();
        List<Summoner> les = new ArrayList<>();

        for (Summoner s : summoners.values()) {
            if (cachedSums.containsKey(s.getId())) {
                Summoner cs = cachedSums.get(s.getId());
                s.setLeu(cs.getLeu());
                s.setRsu(cs.getRsu());
                if (s.getRevisionDate() != cs.getLeu() && cs.getLeu() < System.currentTimeMillis() - update_threshold2) {
                    leids.add(s.getId());
                    les.add(s);
                }
                if (s.getRevisionDate() != cs.getRsu() && cs.getRsu() < System.currentTimeMillis() - update_threshold2) {
                    rsids.add(s);
                }
            } else {
                leids.add(s.getId());
                les.add(s);
                rsids.add(s);
            }
            s.setLastUpdated(System.currentTimeMillis());
        }

        Map<String, List<League>> leaguesMap = new HashMap<>();

        apiCall(rateLimited, riotSide, () -> {
            leaguesMap.putAll(api.league.getLeagueEntriesByIds(p, leids));
            for (Summoner s : les) {
                s.setLeu(s.getRevisionDate());
            }
        });

        List<RankedStats> rankedStatsList = new ArrayList<>();

        for (Summoner s : rsids) {
            System.out.println(">>>>>> " + s.getName());
            apiCall(rateLimited, riotSide, () -> {
                rankedStatsList.add(api.stats.getRankedStats(p, s.getId()));
                s.setRsu(s.getRevisionDate());
            });
        }

        dataManager.put(p, summoners.values(), leaguesMap, rankedStatsList);

        return new UpdateResult(idsToUpdate, rankedStatsList, riotSide.getValue(), rateLimited.getValue());
    }

    private boolean apiCall(Container<Boolean> rateLimitted, Container<Boolean> riotSideProblem, APIcaller a) throws RequestException, IOException {
        do {
            try {
                a.run();
                return false;
            } catch (RateLimitException e) {
                if (rlp.equals(RateLimitPolicy.LOOP)) {
                    continue;
                } else if (rlp.equals(RateLimitPolicy.BREAK)) {
                    rateLimitted.setValue(rateLimitted.getValue() || true);
                    return true;
                } else if (rlp.equals(RateLimitPolicy.THROW)) {
                    throw e;
                }
            } catch (RiotSideException e) {
                riotSideProblem.setValue(riotSideProblem.getValue() || true);
            }
        } while (true);
    }
}
