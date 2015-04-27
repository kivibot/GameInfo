package fi.kivibot.gameinfoback.api.old.structures;

/**
 *
 * @author Nicklas
 */
public class LeagueEntry {
    
    private final String division;
    private final boolean freshBlood;
    private final boolean hotStreak;
    private final boolean inactive;
    private final boolean veteran;
    private final int leaguePoints;
    private final int losses;
    private final MiniSeries miniSeries;
    private final String playerOrTeamId;
    private final String playerOrTeamName;
    private final int wins;

    public LeagueEntry(String division, boolean freshBlood, boolean hotStreak, boolean inactive, boolean veteran, int leaguePoints, int losses, MiniSeries miniSeries, String playerOrTeamId, String playerOrTeamName, int wins) {
        this.division = division;
        this.freshBlood = freshBlood;
        this.hotStreak = hotStreak;
        this.inactive = inactive;
        this.veteran = veteran;
        this.leaguePoints = leaguePoints;
        this.losses = losses;
        this.miniSeries = miniSeries;
        this.playerOrTeamId = playerOrTeamId;
        this.playerOrTeamName = playerOrTeamName;
        this.wins = wins;
    }

    public String getDivision() {
        return division;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public boolean isInactive() {
        return inactive;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getLosses() {
        return losses;
    }

    public MiniSeries getMiniSeries() {
        return miniSeries;
    }

    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    public String getPlayerOrTeamName() {
        return playerOrTeamName;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return "LeagueEntry{" + "division=" + division + ", freshBlood=" + freshBlood + ", hotStreak=" + hotStreak + ", inactive=" + inactive + ", veteran=" + veteran + ", leaguePoints=" + leaguePoints + ", losses=" + losses + ", miniSeries=" + miniSeries + ", playerOrTeamId=" + playerOrTeamId + ", playerOrTeamName=" + playerOrTeamName + ", wins=" + wins + '}';
    }
}
