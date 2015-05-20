package fi.kivibot.gameinfoback.api.struct.summoner;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Nicklas
 */
public class Summoner {

    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private int profileIconId;
    @Expose
    private long revisionDate;
    @Expose
    private long summonerLevel;

    private long lastUpdated;

    public Summoner() {
        lastUpdated = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Summoner{" + "id=" + id + ", name=" + name + ", profileIconId=" + profileIconId + ", revisionDate=" + revisionDate + ", summonerLevel=" + summonerLevel + ", lastUpdated=" + lastUpdated + '}';
    }
}
