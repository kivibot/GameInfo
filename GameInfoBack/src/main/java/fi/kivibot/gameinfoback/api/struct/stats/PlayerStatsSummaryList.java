package fi.kivibot.gameinfoback.api.struct.stats;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class PlayerStatsSummaryList {
    
    private List<PlayerStatsSummary> playerStatSummaries;
    private long summonerId;

    public List<PlayerStatsSummary> getPlayerStatSummaries() {
        return playerStatSummaries;
    }

    public void setPlayerStatSummaries(List<PlayerStatsSummary> playerStatSummaries) {
        this.playerStatSummaries = playerStatSummaries;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }

    @Override
    public String toString() {
        return "PlayerStatsSummaryList{" + "playerStatSummaries=" + playerStatSummaries + ", summonerId=" + summonerId + '}';
    }
    
}
