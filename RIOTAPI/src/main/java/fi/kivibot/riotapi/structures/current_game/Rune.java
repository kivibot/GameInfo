package fi.kivibot.riotapi.structures.current_game;

public class Rune {

    private int count;
    private long runeId;

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
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.count;
        hash = 29 * hash + (int) (this.runeId ^ (this.runeId >>> 32));
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
        final Rune other = (Rune) obj;
        if (this.count != other.count) {
            return false;
        }
        if (this.runeId != other.runeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rune{" + "count=" + count + ", runeId=" + runeId + '}';
    }

}
