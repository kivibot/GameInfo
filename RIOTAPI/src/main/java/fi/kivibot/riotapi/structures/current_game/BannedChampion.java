package fi.kivibot.riotapi.structures.current_game;

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
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.championId ^ (this.championId >>> 32));
        hash = 37 * hash + this.pickTurn;
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
        final BannedChampion other = (BannedChampion) obj;
        if (this.championId != other.championId) {
            return false;
        }
        if (this.pickTurn != other.pickTurn) {
            return false;
        }
        if (this.teamId != other.teamId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BannedChampion{" + "championId=" + championId + ", pickTurn=" + pickTurn + ", teamId=" + teamId + '}';
    }
}
