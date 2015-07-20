package fi.kivibot.riotapi.structures.current_game;

public class Mastery {

    private final long masteryId;
    private final int rank;

    public Mastery(long masteryId, int rank) {
        this.masteryId = masteryId;
        this.rank = rank;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.masteryId ^ (this.masteryId >>> 32));
        hash = 97 * hash + this.rank;
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
        final Mastery other = (Mastery) obj;
        if (this.masteryId != other.masteryId) {
            return false;
        }
        if (this.rank != other.rank) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mastery{" + "masteryId=" + masteryId + ", rank=" + rank + '}';
    }

}
