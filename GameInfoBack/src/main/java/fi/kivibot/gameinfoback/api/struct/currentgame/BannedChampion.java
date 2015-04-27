package fi.kivibot.gameinfoback.api.struct.currentgame;

/**
 *
 * @author Nicklas
 */
public class BannedChampion {
    
    private long championId;
    private int pickTurn;
    private long teamId;

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "BannedChampion{" + "championId=" + championId + ", pickTurn=" + pickTurn + ", teamId=" + teamId + '}';
    }
    
}
