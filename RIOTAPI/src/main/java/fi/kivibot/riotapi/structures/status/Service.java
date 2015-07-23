package fi.kivibot.riotapi.structures.status;

import java.util.List;
import java.util.Objects;

public class Service {
    private List<Incident> incidents;
    private String name;
    private String slug;
    private String status;

    public Service(List<Incident> incidents, String name, String slug, String status) {
        this.incidents = incidents;
        this.name = name;
        this.slug = slug;
        this.status = status;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.incidents);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.slug);
        hash = 97 * hash + Objects.hashCode(this.status);
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
        final Service other = (Service) obj;
        if (!Objects.equals(this.incidents, other.incidents)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.slug, other.slug)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service{" + "incidents=" + incidents + ", name=" + name + ", slug=" + slug + ", status=" + status + '}';
    }
}
