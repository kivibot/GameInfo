package fi.kivibot.gameinfoback.api.structures;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class Participant {

    private final boolean bot;
    private final long championId;
    private final List<Mastery> masteries;
    private final long profileIconId;
    private final List<Rune> runes;
    private final long spell1Id, spell2Id;
    private final long summonerId;
    private final String summonerName;
    private final long teamId;

    public Participant(boolean bot, long championId, List<Mastery> masteries, long profileIconId, List<Rune> runes, long spell1Id, long spell2Id, long summonerId, String summonerName, long teamId) {
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
    public String toString() {
        return "Participant{" + "bot=" + bot + ", championId=" + championId + ", masteries=" + masteries + ", profileIconId=" + profileIconId + ", runes=" + runes + ", spell1Id=" + spell1Id + ", spell2Id=" + spell2Id + ", summonerId=" + summonerId + ", summonerName=" + summonerName + ", teamId=" + teamId + '}';
    }
}
