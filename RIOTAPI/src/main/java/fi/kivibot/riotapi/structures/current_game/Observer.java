package fi.kivibot.riotapi.structures.current_game;

import java.util.Objects;

public class Observer {

    private final String encryptionKey;

    public Observer(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.encryptionKey);
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
        final Observer other = (Observer) obj;
        if (!Objects.equals(this.encryptionKey, other.encryptionKey)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Observer{" + "encryptionKey=" + encryptionKey + '}';
    }

}
