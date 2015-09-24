package fi.kivibot.summonerbackend;

import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.sql.Timestamp;

/**
 *
 * @author Nicklas
 */
public class DBSummoner extends SummonerDto {

    private long lastUpdated;
    private long lastSuccess;

    public DBSummoner(long lastUpdated, long lastSuccess, long id, String name, int profileIconId, long revisionDate, long summonerLevel) {
        super(id, name, profileIconId, revisionDate, summonerLevel);
        this.lastUpdated = lastUpdated;
        this.lastSuccess = lastSuccess;
    }

    public DBSummoner(long lastUpdated, long lastSuccess, SummonerDto sum) {
        this(lastUpdated, lastSuccess, sum.getId(), sum.getName(), sum.getProfileIconId(), sum.getRevisionDate(), sum.getSummonerLevel());
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public long getLastSuccess() {
        return lastSuccess;
    }

}
