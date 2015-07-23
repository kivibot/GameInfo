package fi.kivibot.riotapi.structures.summoner;

public class RuneSlotDto {
    
    private int runeId;
    private int runeSlotId;

    public RuneSlotDto(int runeId, int runeSlotId) {
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
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.runeId;
        hash = 53 * hash + this.runeSlotId;
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
        final RuneSlotDto other = (RuneSlotDto) obj;
        if (this.runeId != other.runeId) {
            return false;
        }
        if (this.runeSlotId != other.runeSlotId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RuneSlotDto{" + "runeId=" + runeId + ", runeSlotId=" + runeSlotId + '}';
    }
    
}
