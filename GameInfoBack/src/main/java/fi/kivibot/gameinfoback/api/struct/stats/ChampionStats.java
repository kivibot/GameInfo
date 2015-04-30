package fi.kivibot.gameinfoback.api.struct.stats;

/**
 *
 * @author Nicklas
 */
public class ChampionStats {
    private int id;
    private AggregatedStats stats;

    public int getId() {
        return id;
    }

    public AggregatedStats getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "ChampionStats{" + "id=" + id + ", stats=" + stats + '}';
    }
}
