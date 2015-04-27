package fi.kivibot.gameinfoback.api.struct.summoner;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class MasteryPage {
    
    private boolean current;
    private long id;
    private List<Mastery> masteries;
    private String name;

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Mastery> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<Mastery> masteries) {
        this.masteries = masteries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MasteryPage{" + "current=" + current + ", id=" + id + ", masteries=" + masteries + ", name=" + name + '}';
    }
    
}
