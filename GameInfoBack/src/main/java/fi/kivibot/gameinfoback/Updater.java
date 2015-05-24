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
    
    public UpdateResult updateSummoners(Platform p, List<Long> ids) throws RiotSideException, RequestException, RateLimitException, IOException, DatabaseException {
        if (ids.isEmpty()) {
            return new UpdateResult(new ArrayList<>(), new ArrayList<>(), false, false);
        }
        Map<Long, Summoner> cachedSums = dataManager.getSummoners(p, ids);
        
        List<Long> idsToUpdate = new ArrayList<>(ids.size());
        
        for (long id : ids) {
            if(cachedSums.containsKey(id)){
               Summoner cachedSum = cachedSums.get(id);
               if(cachedSum.getLastUpdated() < System.currentTimeMillis() - update_threshold){
                   idsToUpdate.add(id);
               }
            }
        }
        
    }
    
    @Deprecated
    public UpdateResult updateSummoners_(Platform p, List<Long> ids) throws RiotSideException, RequestException, RateLimitException, IOException, DatabaseException {
        if (ids.isEmpty()) {
            return new UpdateResult(new ArrayList<>(), new ArrayList<>(), false, false);
        }
        Map<Long, Summoner> cachedSums = dataManager.getSummoners(p, ids);
        List<Long> updatedIds = new ArrayList<>();
        List<Long> updatedLeguesIds = new ArrayList<>();
        List<Long> updatedRankedStatsIds = new ArrayList<>();
        boolean riotSideProblem = false;
        boolean rateLimited = false;
        for (long id : ids) {
            if (cachedSums.containsKey(id)) {
                if (cachedSums.get(id).getLastUpdated() < System.currentTimeMillis() - update_threshold) {
                    System.out.println(cachedSums.get(id).getLastUpdated() + " < " + (System.currentTimeMillis() - update_threshold));
                    updatedIds.add(id);
                    if (cachedSums.get(id).getRsu() < System.currentTimeMillis() - update_threshold2) {
                        System.out.println(cachedSums.get(id).getRsu() + " < " + (System.currentTimeMillis() - update_threshold));
                        updatedRankedStatsIds.add(id);
                    }
                    if (cachedSums.get(id).getLeu() < System.currentTimeMillis() - update_threshold2) {
                        System.out.println(cachedSums.get(id).getLeu() + " < " + (System.currentTimeMillis() - update_threshold));
                        updatedLeguesIds.add(id);
                    }
                }
            } else {
                System.out.println("!");
                updatedIds.add(id);
                updatedLeguesIds.add(id);
                updatedRankedStatsIds.add(id);
            }
        }
        Map<String, Summoner> sums;
        while (true) {
            try {
                sums = api.summoner.getSummonersByIds(p, updatedIds);
                break;
            } catch (RateLimitException e) {
                if (rlp.equals(RateLimitPolicy.THROW)) {
                    throw e;
                } else if (rlp.equals(RateLimitPolicy.BREAK)) {
                    sums = new HashMap<>(0);
                    rateLimited = true;
                    System.out.println(":|");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RiotSideException e) {
                sums = new HashMap<>(0);
                riotSideProblem = true;
                System.out.println(":(");
                break;
            }
        }
        for (Summoner s : sums.values()) {
            s.setLastUpdated(System.currentTimeMillis());
            if (cachedSums.containsKey(s.getId())) {
                if (cachedSums.get(s.getId()).getRevisionDate() != s.getRevisionDate()) {
                    updatedLeguesIds.remove(s.getId());
                    updatedRankedStatsIds.remove(s.getId());
                }
            }
        }

        Map<String, List<League>> leaguesMap;

        while (true) {
            try {
                leaguesMap = api.league.getLeagueEntriesByIds(p, updatedLeguesIds);
                for (Summoner s : sums.values()) {
                    s.setLeu(System.currentTimeMillis());
                }
                break;
            } catch (RateLimitException e) {
                if (rlp.equals(RateLimitPolicy.THROW)) {
                    throw e;
                } else if (rlp.equals(RateLimitPolicy.BREAK)) {
                    leaguesMap = new HashMap<>(0);
                    rateLimited = true;
                    System.out.println(":|");
                    for (Summoner s : sums.values()) {
                        if (cachedSums.containsKey(s.getId())) {
                            s.setLeu(cachedSums.get(s.getId()).getLeu());
                        } else {
                            s.setLeu(1000);
                        }
                    }
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RiotSideException e) {
                leaguesMap = new HashMap<>(0);
                riotSideProblem = true;
                System.out.println(":(");
                for (Summoner s : sums.values()) {
                    if (cachedSums.containsKey(s.getId())) {
                        s.setLeu(cachedSums.get(s.getId()).getLeu());
                    } else {
                        s.setLeu(1000);
                    }
                }
                break;
            }
        }

        List<RankedStats> rankedStatsList = new ArrayList<>();

        for (long id : updatedRankedStatsIds) {
            RankedStats rs = null;
            while (true) {
                try {
                    rs = api.stats.getRankedStats(p, id);
                    sums.get("" + id).setRsu(System.currentTimeMillis());
                    break;
                } catch (RateLimitException e) {
                    if (rlp.equals(RateLimitPolicy.THROW)) {
                        throw e;
                    } else if (rlp.equals(RateLimitPolicy.BREAK)) {
                        rateLimited = true;
                        System.out.println(":|");
                        if (cachedSums.containsKey(id)) {
                            sums.get("" + id).setRsu(cachedSums.get(id).getRsu());
                        } else {
                            sums.get("" + id).setRsu(1000);
                        }
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (RiotSideException e) {
                    riotSideProblem = true;
                    System.out.println(":(");
                    if (cachedSums.containsKey(id)) {
                        sums.get("" + id).setRsu(cachedSums.get(id).getRsu());
                    } else {
                        sums.get("" + id).setRsu(1000);
                    }
                    break;
                }
            }
            if (rs != null) {
                rankedStatsList.add(rs);
            }
        }

        dataManager.put(p, sums.values(), leaguesMap, rankedStatsList);

        return new UpdateResult(updatedIds, rankedStatsList, riotSideProblem, rateLimited);
    }

}
