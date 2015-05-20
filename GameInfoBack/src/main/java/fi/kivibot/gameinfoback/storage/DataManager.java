package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.Cache;
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

    public Map<Long, League> getSoloQLeagues(Platform p, Collection<Long> ids) {
        Map<Long, League> soloqMap = new HashMap<>();
        List<Long> notCached = new ArrayList<>();
        for (Long id : ids) {
            League l = (League) cache.get("s-" + p.getId() + "-" + id);
            if (l == null) {
                notCached.add(id);
            } else {
                soloqMap.put(id, l);
            }
        }
        //TODO: DB
        //--------

        return soloqMap;
    }

    public void put(Platform p, Collection<Summoner> summoners, Map<String, List<League>> leagues, Collection<RankedStats> rankedStats) throws DatabaseException {
        try (DatabaseHandler dbh = dbm.getHandler()) {
            dbh.updateSummoners(p, summoners);

            dbh.commit();
        }
    }

}
