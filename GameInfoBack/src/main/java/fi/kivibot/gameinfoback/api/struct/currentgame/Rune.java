package fi.kivibot.gameinfoback.api.struct.currentgame;

/**
 *
 * @author Nicklas
 */
public class Rune {
    
    private int count;
    private long runeId;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getRuneId() {
        return runeId;
    }

    public void setRuneId(long runeId) {
        this.runeId = runeId;
    }

    @Override
    public String toString() {
        return "Rune{" + "count=" + count + ", runeId=" + runeId + '}';
    }
    
}
