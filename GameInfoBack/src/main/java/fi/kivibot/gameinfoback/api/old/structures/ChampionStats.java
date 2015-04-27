package fi.kivibot.gameinfoback.api.old.structures;

/**
 *
 * @author Nicklas
 */
public class ChampionStats {

    private final int id;
    private final int totalSessionsPlayed;
    private final int totalSessionsWon;
    private final int totalSessionsLost;
    private final int totalChampionKills;
    private final int totalDeathsPerSession;
    private final int totalAssists;

    public ChampionStats(int id, int totalSessionsPlayed, int totalSessionsWon, int totalSessionsLost, int totalChampionKills, int totalDeathsPerSession, int totalAssists) {
        this.id = id;
        this.totalSessionsPlayed = totalSessionsPlayed;
        this.totalSessionsWon = totalSessionsWon;
        this.totalSessionsLost = totalSessionsLost;
        this.totalChampionKills = totalChampionKills;
        this.totalDeathsPerSession = totalDeathsPerSession;
        this.totalAssists = totalAssists;
    }

    public int getId() {
        return id;
    }

    public int getTotalSessionsPlayed() {
        return totalSessionsPlayed;
    }

    public int getTotalSessionsWon() {
        return totalSessionsWon;
    }

    public int getTotalSessionsLost() {
        return totalSessionsLost;
    }

    public int getTotalChampionKills() {
        return totalChampionKills;
    }

    public int getTotalDeathsPerSession() {
        return totalDeathsPerSession;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    @Override
    public String toString() {
        return "ChampionStats{" + "id=" + id + ", totalSessionsPlayed=" + totalSessionsPlayed + ", totalSessionsWon=" + totalSessionsWon + ", totalSessionsLost=" + totalSessionsLost + ", totalChampionKills=" + totalChampionKills + ", totalDeathsPerSession=" + totalDeathsPerSession + ", totalAssists=" + totalAssists + '}';
    }

}
