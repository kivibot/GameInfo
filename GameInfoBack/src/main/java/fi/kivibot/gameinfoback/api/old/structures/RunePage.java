package fi.kivibot.gameinfoback.api.old.structures;

import java.util.Set;

/**
 *
 * @author Nicklas
 */
public class RunePage {
    
    private final boolean current;
    private final long id;
    private final String name;
    private final Set<RuneSlot> slots;

    public RunePage(boolean current, long id, String name, Set<RuneSlot> slots) {
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

    public Set<RuneSlot> getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return "RunePage{" + "current=" + current + ", id=" + id + ", name=" + name + ", slots=" + slots + '}';
    }
    
}
