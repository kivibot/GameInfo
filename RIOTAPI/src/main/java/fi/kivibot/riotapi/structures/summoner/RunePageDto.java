package fi.kivibot.riotapi.structures.summoner;

import java.util.List;
import java.util.Objects;

public class RunePageDto {
    private boolean current;
    private long id;
    private String name;
    private List<RuneSlotDto> slots;

    public RunePageDto(boolean current, long id, String name, List<RuneSlotDto> slots) {
        this.current = current;
        this.id = id;
        this.name = name;
        this.slots = slots;
    }

    public boolean isCurrent() {
        return current;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RuneSlotDto> getSlots() {
        return slots;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.current ? 1 : 0);
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.slots);
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
        final RunePageDto other = (RunePageDto) obj;
        if (this.current != other.current) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.slots, other.slots)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RunePageDto{" + "current=" + current + ", id=" + id + ", name=" + name + ", slots=" + slots + '}';
    }
    
}
