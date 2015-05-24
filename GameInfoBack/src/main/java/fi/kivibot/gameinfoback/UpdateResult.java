package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import java.util.List;

/**
 *
 * @author Nicklas
 */
public class UpdateResult {
    
    private final List<Long> updated;
    private final List<RankedStats> rankedStats;
    private final boolean riotFailure;
    private final boolean rateLimited;

    public UpdateResult(List<Long> updated, List<RankedStats> rankedStats, boolean riotFailure, boolean rateLimited) {
        this.updated = updated;
        this.rankedStats = rankedStats;
        this.riotFailure = riotFailure;
        this.rateLimited = rateLimited;
    }

    public List<Long> getUpdated() {
        return updated;
    }

    public List<RankedStats> getRankedStats() {
        return rankedStats;
    }

    public boolean isRiotFailure() {
        return riotFailure;
    }

    public boolean isRateLimited() {
        return rateLimited;
    }
    
}
