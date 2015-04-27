package fi.kivibot.gameinfoback.api.old.structures;

/**
 *
 * @author Nicklas
 */
public class Mastery {

    private final long masteryId;
    private final int rank;

    public Mastery(long masteryId, int rank) {
        this.masteryId = masteryId;
        this.rank = rank;
    }

    public long getMasteryId() {
        return masteryId;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Mastery{" + "masteryId=" + masteryId + ", rank=" + rank + '}';
    }
}
