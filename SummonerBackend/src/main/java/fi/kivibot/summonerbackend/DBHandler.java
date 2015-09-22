package fi.kivibot.summonerbackend;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.structures.current_game.CurrentGameInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public interface DBHandler {

    public Map<Long, DBSummoner> getSummoners(Region region, Collection<Long> ids) throws IOException, SQLException;

    public Map<String, DBSummoner> getSummonersByName(Region region, Collection<String> names) throws IOException, SQLException;

    public void insertSummoners(Region region, Collection<DBSummoner> summoners) throws IOException, SQLException;

    public void updateSummoners(Region region, Collection<DBSummoner> summoners) throws IOException, SQLException;

    public void transaction(TransactionHandler transaction) throws IOException, SQLException;

}
