package fi.kivibot.gameinfoback.api.structures;

/**
 *
 * @author Nicklas
 */
public class Rune {
    private final int count;
    private final long runeId;

    public Rune(int count, long runeId) {
        this.count = count;
        this.runeId = runeId;
    }

    public int getCount() {
        return count;
    }

    public long getRuneId() {
        return runeId;
    }

    @Override
    public String toString() {
        return "Rune{" + "count=" + count + ", runeId=" + runeId + '}';
    }
}
