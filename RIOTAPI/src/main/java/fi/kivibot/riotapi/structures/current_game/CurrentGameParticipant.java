package fi.kivibot.riotapi.structures.current_game;

import java.util.List;
import java.util.Objects;

public class CurrentGameParticipant {

    private final boolean bot;
    private final long championId;
    private final List<Mastery> masteries;
    private final long profileIconId;
    private final List<Rune> runes;
    private final long spell1Id;
    private final long spell2Id;
    private final long summonerId;
    private final String summonerName;
    private final long teamId;

    public CurrentGameParticipant(boolean bot, long championId, List<Mastery> masteries, long profileIconId, List<Rune> runes, long spell1Id, long spell2Id, long summonerId, String summonerName, long teamId) {
        this.bot = bot;
        this.championId = championId;
        this.masteries = masteries;
        this.profileIconId = profileIconId;
        this.runes = runes;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.summonerId = summonerId;
        this.summonerName = summonerName;
        this.teamId = teamId;
    }

    public boolean isBot() {
        return bot;
    }

    public long getChampionId() {
        return championId;
    }

    public List<Mastery> getMasteries() {
        return masteries;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public List<Rune> getRunes() {
        return runes;
    }

    public long getSpell1Id() {
        return spell1Id;
    }

    public long getSpell2Id() {
        return spell2Id;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public long getTeamId() {
        return teamId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.bot ? 1 : 0);
        hash = 73 * hash + (int) (this.championId ^ (this.championId >>> 32));
        hash = 73 * hash + Objects.hashCode(this.masteries);
        hash = 73 * hash + (int) (this.profileIconId ^ (this.profileIconId >>> 32));
        hash = 73 * hash + Objects.hashCode(this.runes);
        hash = 73 * hash + (int) (this.spell1Id ^ (this.spell1Id >>> 32));
        hash = 73 * hash + (int) (this.spell2Id ^ (this.spell2Id >>> 32));
        hash = 73 * hash + (int) (this.summonerId ^ (this.summonerId >>> 32));
        hash = 73 * hash + Objects.hashCode(this.summonerName);
        hash = 73 * hash + (int) (this.teamId ^ (this.teamId >>> 32));
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
        final CurrentGameParticipant other = (CurrentGameParticipant) obj;
        if (this.bot != other.bot) {
            return false;
        }
        if (this.championId != other.championId) {
            return false;
        }
        if (!Objects.equals(this.masteries, other.masteries)) {
            return false;
        }
        if (this.profileIconId != other.profileIconId) {
            return false;
        }
        if (!Objects.equals(this.runes, other.runes)) {
            return false;
        }
        if (this.spell1Id != other.spell1Id) {
            return false;
        }
        if (this.spell2Id != other.spell2Id) {
            return false;
        }
        if (this.summonerId != other.summonerId) {
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
        return "CurrentGameParticipant{" + "bot=" + bot + ", championId=" + championId + ", masteries=" + masteries + ", profileIconId=" + profileIconId + ", runes=" + runes + ", spell1Id=" + spell1Id + ", spell2Id=" + spell2Id + ", summonerId=" + summonerId + ", summonerName=" + summonerName + ", teamId=" + teamId + '}';
    }

}
