package fi.kivibot.gameinfoback.api.structures;

/**
 *
 * @author Nicklas
 */
public class PlayerStatsSummary {
    
    private final int losses;
    private final long modifyDate;
    private final String playerStatSummaryType;
    private final int wins;

    public PlayerStatsSummary(int losses, long modifyDate, String playerStatSummaryType, int wins) {
        this.losses = losses;
        this.modifyDate = modifyDate;
        this.playerStatSummaryType = playerStatSummaryType;
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public String getPlayerStatSummaryType() {
        return playerStatSummaryType;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return "PlayerStatsSummary{" + "losses=" + losses + ", modifyDate=" + modifyDate + ", playerStatSummaryType=" + playerStatSummaryType + ", wins=" + wins + '}';
    }
    
}
