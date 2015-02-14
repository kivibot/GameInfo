package fi.kivibot.gameinfoback.api.structures;

/**
 *
 * @author Nicklas
 */
public class RuneSlot {
    
    private final int runeId;
    private final int runeSlotId;

    public RuneSlot(int runeId, int runeSlotId) {
        this.runeId = runeId;
        this.runeSlotId = runeSlotId;
    }

    public int getRuneId() {
        return runeId;
    }

    public int getRuneSlotId() {
        return runeSlotId;
    }

    @Override
    public String toString() {
        return "RuneSlot{" + "runeId=" + runeId + ", runeSlotId=" + runeSlotId + '}';
    }
    
}
