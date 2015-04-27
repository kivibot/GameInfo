package fi.kivibot.gameinfoback.api.struct.currentgame;

/**
 *
 * @author Nicklas
 */
public class Observer {
    
    private String encryptionKey;

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    public String toString() {
        return "Observer{" + "encryptionKey=" + encryptionKey + '}';
    }
    
}
