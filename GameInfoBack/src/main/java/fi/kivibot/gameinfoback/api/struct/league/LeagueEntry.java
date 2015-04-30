package fi.kivibot.gameinfoback.api.struct.league;

/**
 *
 * @author Nicklas
 */
public class LeagueEntry {
    
    private String division;
    private boolean isFreshBlood;
    private boolean isHotStreak;
    private boolean isInactive;
    private boolean isVeteran;
    private int leaguePoints;
    private int losses;
    private MiniSeries miniSeries;
    private String playerOrTeamId;
    private String playerOrTeamName;
    private int wins;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public boolean isIsFreshBlood() {
        return isFreshBlood;
    }

    public void setIsFreshBlood(boolean isFreshBlood) {
        this.isFreshBlood = isFreshBlood;
    }

    public boolean isIsHotStreak() {
        return isHotStreak;
    }

    public void setIsHotStreak(boolean isHotStreak) {
        this.isHotStreak = isHotStreak;
    }

    public boolean isIsInactive() {
        return isInactive;
    }

    public void setIsInactive(boolean isInactive) {
        this.isInactive = isInactive;
    }

    public boolean isIsVeteran() {
        return isVeteran;
    }

    public void setIsVeteran(boolean isVeteran) {
        this.isVeteran = isVeteran;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public MiniSeries getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(MiniSeries miniSeries) {
        this.miniSeries = miniSeries;
    }

    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    public void setPlayerOrTeamId(String playerOrTeamId) {
        this.playerOrTeamId = playerOrTeamId;
    }

    public String getPlayerOrTeamName() {
        return playerOrTeamName;
    }

    public void setPlayerOrTeamName(String playerOrTeamName) {
        this.playerOrTeamName = playerOrTeamName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "LeagueEntry{" + "division=" + division + ", isFreshBlood=" + isFreshBlood + ", isHotStreak=" + isHotStreak + ", isInactive=" + isInactive + ", isVeteran=" + isVeteran + ", leaguePoints=" + leaguePoints + ", losses=" + losses + ", miniSeries=" + miniSeries + ", playerOrTeamId=" + playerOrTeamId + ", playerOrTeamName=" + playerOrTeamName + ", wins=" + wins + '}';
    }
    
}
