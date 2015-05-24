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

    public void setId(int id) {
        this.id = id;
    }

    public AggregatedStats getStats() {
        return stats;
    }

    public void setStats(AggregatedStats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "ChampionStats{" + "id=" + id + ", stats=" + stats + '}';
    }
}
