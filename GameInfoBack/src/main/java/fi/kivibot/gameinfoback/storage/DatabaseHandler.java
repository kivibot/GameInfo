package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public interface DatabaseHandler extends AutoCloseable {

    public void commit() throws DatabaseException;

    public void updateSummoners(Platform p, Collection<Summoner> sums) throws DatabaseException;

    public Map<Long, Summoner> getSummoners(Platform p, Collection<Long> ids) throws DatabaseException;

    public void updateLeagues(Platform p, Map<String, List<League>> leagues) throws DatabaseException;

    public void updateRankedStats(Platform p, Collection<RankedStats> rankedStats) throws DatabaseException;
    
    public Map<Long, RankedStats> getRankedStats(Platform p, Collection<Long> ids) throws DatabaseException;
    
    public Map<Long, List<DBLeagueEntry>> getLeagueEntries(Platform p, Collection<Long> ids) throws DatabaseException;

    @Override
    public void close() throws DatabaseException;

}