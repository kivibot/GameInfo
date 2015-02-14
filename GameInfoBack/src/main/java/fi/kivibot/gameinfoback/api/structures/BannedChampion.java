package fi.kivibot.gameinfoback.api.structures;

/**
 *
 * @author Nicklas
 */
public class BannedChampion {

    private final long championId;
    private final int pickTurn;
    private final long teamId;

    public BannedChampion(long championId, int pickTurn, long teamId) {
        this.championId = championId;
        this.pickTurn = pickTurn;
        this.teamId = teamId;
    }

    public long getChampionId() {
        return championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public long getTeamId() {
        return teamId;
    }

    @Override
    public String toString() {
        return "BannedChampion{" + "championId=" + championId + ", pickTurn=" + pickTurn + ", teamId=" + teamId + '}';
    }

}
