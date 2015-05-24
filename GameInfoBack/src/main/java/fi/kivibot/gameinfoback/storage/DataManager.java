package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public class DataManager {

    private final Cache cache;
    private final DatabaseManager dbm;

    public DataManager(Cache cache, DatabaseManager dbm) {
        this.cache = cache;
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
        try (DatabaseHandler dbh = dbm.getHandler()) {
            Map<Long, Summoner> summoners2 = dbh.getSummoners(p, notCached);
            for (Summoner s : summoners2.values()) {
                summonersMap.put(s.getId(), s);
                cache.put("s-" + p.getId() + "-" + s.getId(), s);
            }
            return summonersMap;
        }
    }

    public Map<Long, List<DBLeagueEntry>> getSoloQLeagues(Platform p, Collection<Long> ids) throws DatabaseException {
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
        try (DatabaseHandler dbh = dbm.getHandler()) {
            Map<Long, List<DBLeagueEntry>> dblem = dbh.getLeagueEntries(p, notCached);
            for (Map.Entry<Long, List<DBLeagueEntry>> e : dblem.entrySet()) {
                lem.put(e.getKey(), e.getValue());
                cache.put("dble-" + p.getId() + "-" + e.getKey(), e.getValue());
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
        try (DatabaseHandler dbh = dbm.getHandler()) {
            Map<Long, RankedStats> rsmap2 = dbh.getRankedStats(p, notCached);
            for (RankedStats rs : rsmap2.values()) {
                rsmap.put(rs.getSummonerId(), rs);
                cache.put("rs-" + p.getId() + "-" + rs.getSummonerId(), rs);
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
        System.out.println(leagues);
        System.out.println(rankedStats);
        try (DatabaseHandler dbh = dbm.getHandler()) {
            dbh.updateSummoners(p, summoners);

            dbh.updateRankedStats(p, rankedStats);

            dbh.commit();
        }
    }

}
