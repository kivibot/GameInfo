package fi.kivibot.riotapi.structures.status;

import java.util.List;
import java.util.Objects;

public class Incident {

    private boolean active;
    private String created_at;
    private long id;
    private List<Message> updates;

    public Incident(boolean active, String created_at, long id, List<Message> updates) {
        this.active = active;
        this.created_at = created_at;
        this.id = id;
        this.updates = updates;
    }

    public boolean isActive() {
        return active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public List<Message> getUpdates() {
        return updates;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.active ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.created_at);
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.updates);
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
        final Incident other = (Incident) obj;
        if (this.active != other.active) {
            return false;
        }
        if (!Objects.equals(this.created_at, other.created_at)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.updates, other.updates)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Incident{" + "active=" + active + ", created_at=" + created_at + ", id=" + id + ", updates=" + updates + '}';
    }

}
