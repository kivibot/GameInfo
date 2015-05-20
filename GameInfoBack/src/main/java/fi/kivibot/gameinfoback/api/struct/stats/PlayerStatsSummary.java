package fi.kivibot.gameinfoback.api.struct.stats;

/**
 *
 * @author Nicklas
 */
public class PlayerStatsSummary {
    private AggregatedStats aggregatedStats;
    private Integer losses;
    private long modifyDate;
    private String playerStatSummaryType;
    private int wins;

    public AggregatedStats getAggregatedStats() {
        return aggregatedStats;
    }

    public void setAggregatedStats(AggregatedStats aggregatedStats) {
        this.aggregatedStats = aggregatedStats;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPlayerStatSummaryType() {
        return playerStatSummaryType;
    }

    public void setPlayerStatSummaryType(String playerStatSummaryType) {
        this.playerStatSummaryType = playerStatSummaryType;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "PlayerStatsSummary{" + "aggregatedStats=" + aggregatedStats + ", losses=" + losses + ", modifyDate=" + modifyDate + ", playerStatSummaryType=" + playerStatSummaryType + ", wins=" + wins + '}';
    }
}
