package fi.kivibot.gameinfoback.storage;

/**
 *
 * @author Nicklas
 */
public interface DatabaseManager {
    
    public DatabaseHandler getHandler() throws DatabaseException;
    
}
