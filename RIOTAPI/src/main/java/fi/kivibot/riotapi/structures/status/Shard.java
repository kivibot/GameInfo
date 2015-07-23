package fi.kivibot.riotapi.structures.status;

import java.util.List;
import java.util.Objects;

public class Shard {

    private String hostname;
    private List<String> locales;
    private String name;
    private String region_tag;
    private String slug;

    public Shard(String hostname, List<String> locales, String name, String region_tag, String slug) {
        this.hostname = hostname;
        this.locales = locales;
        this.name = name;
        this.region_tag = region_tag;
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

    public String getSlug() {
        return slug;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.hostname);
        hash = 53 * hash + Objects.hashCode(this.locales);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.region_tag);
        hash = 53 * hash + Objects.hashCode(this.slug);
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
        final Shard other = (Shard) obj;
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
        if (!Objects.equals(this.slug, other.slug)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Shard{" + "hostname=" + hostname + ", locales=" + locales + ", name=" + name + ", region_tag=" + region_tag + ", slug=" + slug + '}';
    }

}
