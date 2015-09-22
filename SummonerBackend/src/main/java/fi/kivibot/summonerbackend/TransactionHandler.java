package fi.kivibot.summonerbackend;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Nicklas
 */
public interface TransactionHandler {

    public void onTransaction(DBHandler dbh) throws IOException, SQLException;

}
