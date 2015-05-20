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

    private static final long update_threshold = 5 * 60 * 1000;

    private final DataManager dataManager;
    private final RiotAPI api;
    private final RateLimitPolicy rlp;

    public Updater(DataManager dataManager, RiotAPI api, RateLimitPolicy rlp) {
        this.dataManager = dataManager;
        this.api = api;
        this.rlp = rlp;
    }

//    @Deprecated
//    public void update(CurrentGameInfo cgi) throws RiotSideException, RequestException, IOException {
//        String cgiKey = "cgi-" + cgi.getGameId();
//        if (cache.get(cgiKey) != null) {
//            return;
//        }
//        ReentrantLock lock = getLock(cgiKey, true);
//        System.out.println("asdf");
//        lock.lock();
//        try {
//            System.err.println("got lock");
//            if (cache.get(cgiKey) != null) {
//                return;
//            }
//            List<Summoner> summoners = new ArrayList<>();
//            List<Long> updateList = new ArrayList<>();
//            List<Long> participants = new ArrayList<>();
//            for (CurrentGameParticipant p : cgi.getParticipants()) {
//                if (!p.isBot()) {
//                    participants.add(p.getSummonerId());
//                }
//            }
//            Map<String, Summoner> participantsMap;
//            while (true) {
//                try {
//                    participantsMap = api.summoner.getSummonersByIds(participants);
//                    break;
//                } catch (RateLimitException e) {
//                    if (rp.equals(RateLimitPolicy.THROW)) {
//                        throw e;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//            for (CurrentGameParticipant p : cgi.getParticipants()) {
//                String pidstr = "" + p.getSummonerId();
//                Summoner s = participantsMap.get(pidstr);
//                if (s != null) {
//                    Summoner cs = (Summoner) cache.get("s-" + p.getSummonerId());
//                    if (!(cs != null && cs.getRevisionDate() == s.getRevisionDate())) {
//                        updateList.add(p.getSummonerId());
//                    }
//                }
//            }
//            Map<String, List<League>> leaguesMap;
//            while (true) {
//                try {
//                    leaguesMap = api.league.getLeagueEntriesByIds(updateList);
//                    break;
//                } catch (RateLimitException e) {
//                    if (rp.equals(RateLimitPolicy.THROW)) {
//                        throw e;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//            List<RankedStats> rankedStatsList = new ArrayList<>();
//            for (Long l : updateList) {
//                while (true) {
//                    try {
//                        RankedStats rs = api.stats.getRankedStats(l);
//                        if (rs != null) {
//                            rankedStatsList.add(rs);
//                        }
//                        break;
//                    } catch (RateLimitException e) {
//                        if (rp.equals(RateLimitPolicy.THROW)) {
//                            throw e;
//                        }
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//
//            //UPDATE CACHE AND DB
//            cache.put(cgiKey, cgi);
//            for (Summoner s : participantsMap.values()) {
//                cache.put("s-" + s.getId(), s);
//            }
//            List<League> leagues = new ArrayList<>();
//            for (Map.Entry<String, List<League>> e : leaguesMap.entrySet()) {
//                leagues.addAll(e.getValue());
//                for (League l : e.getValue()) {
//                    if (l.getQueue().equals("RANKED_SOLO_5x5")) {
//                        cache.put("le-rs5-" + e.getKey(), l);
//                    }
//                }
//            }
//
//            for (RankedStats rs : rankedStatsList) {
//                cache.put("rs-" + rs.getSummonerId(), rs);
//            }
//
//            try (DatabaseHandler dbh = dbm.getHandler()) {
//                dbh.updateSummoners(api.getPlatform(), participantsMap.values());
//                dbh.updateLeagues(api.getPlatform(), leagues);
//                dbh.executeBatchAndCommit();
//            } catch (Exception ex) {
//                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        } catch (RiotSideException | RequestException | IOException ex) {
//            throw ex;
//        } finally {
//            returnLock(cgiKey);
//        }
//
//    }
    public void updateSummoners(Platform p, List<Long> ids) throws RiotSideException, RequestException, RateLimitException, IOException, DatabaseException {
        if (ids.isEmpty()) {
            return;
        }
        Map<Long, Summoner> cachedSums = dataManager.getSummoners(p, ids);
        List<Long> updateIds = new ArrayList<>();
        for (long id : ids) {
            if (cachedSums.containsKey(id)) {
                if (cachedSums.get(id).getLastUpdated() < System.currentTimeMillis() - update_threshold) {
                    System.out.println(cachedSums.get(id).getLastUpdated() +" < "+ (System.currentTimeMillis() - update_threshold));
                    updateIds.add(id);
                }
            } else {
                System.out.println("!");
                updateIds.add(id);
            }
        }
        Map<String, Summoner> sums;
        while (true) {
            try {
                sums = api.summoner.getSummonersByIds(p, updateIds);
                break;
            } catch (RateLimitException e) {
                if (rlp.equals(RateLimitPolicy.THROW)) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        updateIds.clear();
        for (Summoner s : sums.values()) {
            if (cachedSums.containsKey(s.getId())) {
                if (cachedSums.get(s.getId()).getRevisionDate() != s.getRevisionDate()) {
                    updateIds.add(s.getId());
                }
            } else {
                updateIds.add(s.getId());
            }
        }

        Map<String, List<League>> leaguesMap;

        while (true) {
            try {
                leaguesMap = api.league.getLeagueEntriesByIds(p, ids);
                break;
            } catch (RateLimitException e) {
                if (rlp.equals(RateLimitPolicy.THROW)) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        List<RankedStats> rankedStatsList = new ArrayList<>();

        for (long id : updateIds) {
            RankedStats rs;
            while (true) {
                try {
                    rs = api.stats.getRankedStats(p, id);
                    break;
                } catch (RateLimitException e) {
                    if (rlp.equals(RateLimitPolicy.THROW)) {
                        throw e;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (rs != null) {
                rankedStatsList.add(rs);
            }
        }

        dataManager.put(p, sums.values(), leaguesMap, rankedStatsList);

    }

}
