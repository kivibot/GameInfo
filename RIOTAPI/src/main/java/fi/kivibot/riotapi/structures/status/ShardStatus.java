package fi.kivibot.riotapi.structures.status;

import java.util.List;
import java.util.Objects;

public class ShardStatus {

    private String hostname;
    private List<String> locales;
    private String name;
    private String region_tag;
    private List<Service> services;
    private String slug;

    public ShardStatus(String hostname, List<String> locales, String name, String region_tag, List<Service> services, String slug) {
        this.hostname = hostname;
        this.locales = locales;
        this.name = name;
        this.region_tag = region_tag;
        this.services = services;
        this.slug = slug;
    }

    public String getHostname() {
        return hostname;
    }

    public List<String> getLocales() {
        return locales;
    }

    public String getName() {
        return name;
    }

    public String getRegion_tag() {
        return region_tag;
    }

    public List<Service> getServices() {
        return services;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.hostname);
        hash = 11 * hash + Objects.hashCode(this.locales);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.region_tag);
        hash = 11 * hash + Objects.hashCode(this.services);
        hash = 11 * hash + Objects.hashCode(this.slug);
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
        final ShardStatus other = (ShardStatus) obj;
        if (!Objects.equals(this.hostname, other.hostname)) {
            return false;
        }
        if (!Objects.equals(this.locales, other.locales)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.region_tag, other.region_tag)) {
            return false;
        }
        if (!Objects.equals(this.services, other.services)) {
            return false;
        }
        if (!Objects.equals(this.slug, other.slug)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShardStatus{" + "hostname=" + hostname + ", locales=" + locales + ", name=" + name + ", region_tag=" + region_tag + ", services=" + services + ", slug=" + slug + '}';
    }

}
