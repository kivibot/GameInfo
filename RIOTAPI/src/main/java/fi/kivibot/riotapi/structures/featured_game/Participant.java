package fi.kivibot.riotapi.structures.featured_game;

import java.util.Objects;

public class Participant {

    private boolean bot;
    private long championId;
    private long profileIconId;
    private long spell1Id;
    private long spell2Id;
    private String summonerName;
    private long teamId;

    public Participant(boolean bot, long championId, long profileIconId, long spell1Id, long spell2Id, String summonerName, long teamId) {
        this.bot = bot;
        this.championId = championId;
        this.profileIconId = profileIconId;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.summonerName = summonerName;
        this.teamId = teamId;
    }

    public boolean isBot() {
        return bot;
    }

    public long getChampionId() {
        return championId;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public long getSpell1Id() {
        return spell1Id;
    }

    public long getSpell2Id() {
        return spell2Id;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public long getTeamId() {
        return teamId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.bot ? 1 : 0);
        hash = 37 * hash + (int) (this.championId ^ (this.championId >>> 32));
        hash = 37 * hash + (int) (this.profileIconId ^ (this.profileIconId >>> 32));
        hash = 37 * hash + (int) (this.spell1Id ^ (this.spell1Id >>> 32));
        hash = 37 * hash + (int) (this.spell2Id ^ (this.spell2Id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.summonerName);
        hash = 37 * hash + (int) (this.teamId ^ (this.teamId >>> 32));
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
        final Participant other = (Participant) obj;
        if (this.bot != other.bot) {
            return false;
        }
        if (this.championId != other.championId) {
            return false;
        }
        if (this.profileIconId != other.profileIconId) {
            return false;
        }
        if (this.spell1Id != other.spell1Id) {
            return false;
        }
        if (this.spell2Id != other.spell2Id) {
            return false;
        }
        if (!Objects.equals(this.summonerName, other.summonerName)) {
            return false;
        }
        if (this.teamId != other.teamId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Participant{" + "bot=" + bot + ", championId=" + championId + ", profileIconId=" + profileIconId + ", spell1Id=" + spell1Id + ", spell2Id=" + spell2Id + ", summonerName=" + summonerName + ", teamId=" + teamId + '}';
    }
}
