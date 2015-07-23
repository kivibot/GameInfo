package fi.kivibot.riotapi.structures.summoner;

import java.util.Objects;

public class SummonerDto {

    private long id;
    private String name;
    private int profileIconId;
    private long revisionDate;
    private long summonerLevel;

    public SummonerDto(long id, String name, int profileIconId, long revisionDate, long summonerLevel) {
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.summonerLevel = summonerLevel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + this.profileIconId;
        hash = 89 * hash + (int) (this.revisionDate ^ (this.revisionDate >>> 32));
        hash = 89 * hash + (int) (this.summonerLevel ^ (this.summonerLevel >>> 32));
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
        final SummonerDto other = (SummonerDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.profileIconId != other.profileIconId) {
            return false;
        }
        if (this.revisionDate != other.revisionDate) {
            return false;
        }
        if (this.summonerLevel != other.summonerLevel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SummonerDto{" + "id=" + id + ", name=" + name + ", profileIconId=" + profileIconId + ", revisionDate=" + revisionDate + ", summonerLevel=" + summonerLevel + '}';
    }

}
