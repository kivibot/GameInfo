package fi.kivibot.summonerbackend;

import java.sql.Connection;

/**
 *
 * @author Nicklas
 */
public interface ConnectionPool {
    
    public Connection getConnection();
    
}
