package fi.kivibot.gameinfoback.storage;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.league.LeagueEntry;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Nicklas
 */
public class DataManager {

    private class Pair<K, V> {

        private K first;
        private V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public Pair() {
        }

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public V getSecond() {
            return second;
        }

        public void setSecond(V second) {
            this.second = second;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.first);
            hash = 89 * hash + Objects.hashCode(this.second);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.first, other.first)) {
                return false;
            }
            if (!Objects.equals(this.second, other.second)) {
                return false;
            }
            return true;
        }
        
    }

    private final Cache<Pair<Platform,Long>, Summoner> summonerCache;
    private final DatabaseManager dbm;

    public DataManager(DatabaseManager dbm) {
        this.summonerCache = CacheBuilder.newBuilder().build(new CacheLoader<Pair<Platform,Long>, Summoner>() {
            @Override
            public Summoner load(Pair<Platform, Long> k) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.dbm = dbm;
    }

    public Map<Long, Summoner> getSummoners(Platform p, Collection<Long> ids) throws DatabaseException {
        Map<Long, Summoner> summonersMap = new HashMap<>();
        List<Long> notCached = new ArrayList<>();
        for (Long id : ids) {
            Summoner s = (Summoner) cache.get("s-" + p.getId() + "-" + id);
            if (s == null) {
                notCached.add(id);
            } else {
                summonersMap.put(id, s);
            }
        }
        if (!notCached.isEmpty()) {
            try (DatabaseHandler dbh = dbm.getHandler()) {
                Map<Long, Summoner> summoners2 = dbh.getSummoners(p, notCached);
                for (Summoner s : summoners2.values()) {
                    summonersMap.put(s.getId(), s);
                    cache.put("s-" + p.getId() + "-" + s.getId(), s);
                }
            }
        }
        return summonersMap;
    }

    public Map<Long, List<DBLeagueEntry>> getLeagueEntries(Platform p, Collection<Long> ids) throws DatabaseException {
        Map<Long, List<DBLeagueEntry>> lem = new HashMap<>();
        List<Long> notCached = new ArrayList<>();
        for (Long id : ids) {
            List<DBLeagueEntry> l = (List<DBLeagueEntry>) cache.get("dble-" + p.getId() + "-" + id);
            if (l == null) {
                notCached.add(id);
            } else {
                lem.put(id, l);
            }
        }
        if (!notCached.isEmpty()) {
            try (DatabaseHandler dbh = dbm.getHandler()) {
                Map<Long, List<DBLeagueEntry>> dblem = dbh.getLeagueEntries(p, notCached);
                for (Map.Entry<Long, List<DBLeagueEntry>> e : dblem.entrySet()) {
                    lem.put(e.getKey(), e.getValue());
                    cache.put("dble-" + p.getId() + "-" + e.getKey(), e.getValue());
                }
            }
        }
        return lem;
    }

    public Map<Long, RankedStats> getRankedStats(Platform p, Collection<Long> ids) throws DatabaseException {
        Map<Long, RankedStats> rsmap = new HashMap<>();
        List<Long> notCached = new ArrayList<>();
        for (long id : ids) {
            RankedStats rs = (RankedStats) cache.get("rs-" + p.getId() + "-" + id);
            if (rs == null) {
                notCached.add(id);
            } else {
                rsmap.put(id, rs);
            }
        }
        if (!notCached.isEmpty()) {
            try (DatabaseHandler dbh = dbm.getHandler()) {
                Map<Long, RankedStats> rsmap2 = dbh.getRankedStats(p, notCached);
                for (RankedStats rs : rsmap2.values()) {
                    rsmap.put(rs.getSummonerId(), rs);
                    cache.put("rs-" + p.getId() + "-" + rs.getSummonerId(), rs);
                }
            }
        }
        return rsmap;
    }

    public void put(Platform p, Collection<Summoner> summoners, Map<String, List<League>> leagues, Collection<RankedStats> rankedStats) throws DatabaseException {
        for (Summoner s : summoners) {
            cache.put("s-" + p.getId() + "-" + s.getId(), s);
        }
        for (RankedStats rs : rankedStats) {
            cache.put("rs-" + p.getId() + "-" + rs.getSummonerId(), rs);
        }
        leagues.forEach((k, v) -> {
            List<DBLeagueEntry> dbles = new ArrayList<>();
            for (League l : v) {
                LeagueEntry le = l.getEntries().get(0);
                DBLeagueEntry dble = new DBLeagueEntry();
                dble.setDivision(le.getDivision());
                dble.setFreshBlood(le.isFreshBlood());
                dble.setHotStreak(le.isHotStreak());
                dble.setInactive(le.isInactive());
                dble.setLeaguePoints(le.getLeaguePoints());
                dble.setLosses(le.getLosses());
                dble.setPlayerOrTeamId(le.getPlayerOrTeamId());
                dble.setPlayerOrTeamName(le.getPlayerOrTeamName());
                dble.setQueue(l.getQueue());
                dble.setTier(l.getTier());
                dble.setVeteran(le.isVeteran());
                dble.setWins(le.getWins());
                dbles.add(dble);
            }

            cache.put("dble-" + p.getId() + "-" + k, dbles);
        });
        System.out.println(leagues);
        System.out.println(rankedStats);
        try (DatabaseHandler dbh = dbm.getHandler()) {
            dbh.updateSummoners(p, summoners);

            dbh.updateRankedStats(p, rankedStats);

            dbh.updateLeagues(p, leagues);

            dbh.commit();
        }
    }

}
