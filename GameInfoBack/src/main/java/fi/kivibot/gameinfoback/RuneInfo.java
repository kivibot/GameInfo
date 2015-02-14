package fi.kivibot.gameinfoback;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class RuneInfo {

    private final String name;
    private final String description;
    private final List<RuneStat> stats;

    public RuneInfo(String name, String description, List<RuneStat> stats) {
        this.name = name;
        this.description = description;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<RuneStat> getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "RuneInfo{" + "name=" + name + ", description=" + description + ", stats=" + stats + '}';
    }
}
