package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.storage.DatabaseException;
import fi.kivibot.gameinfoback.storage.DatabaseManager;
import fi.kivibot.gameinfoback.storage.DatabaseHandler;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class MariaDBManager implements DatabaseManager {

    private final ComboPooledDataSource ds;

    public MariaDBManager() throws SQLException, PropertyVetoException {
        ds = new ComboPooledDataSource();
        ds.setDriverClass("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://127.0.0.1/gameinfo");
        ds.setUser("root");
        ds.setPassword("qwerty");
    }

    @Override
    public DatabaseHandler getHandler() throws DatabaseException {
        try {
            return new MariaDBHandler(ds);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

}
