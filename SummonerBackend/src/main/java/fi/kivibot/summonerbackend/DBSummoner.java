package fi.kivibot.summonerbackend;

import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.sql.Timestamp;

/**
 *
 * @author Nicklas
 */
public class DBSummoner extends SummonerDto {

    private Timestamp lastUpdated;
    private Timestamp lastSuccess;

    public DBSummoner(Timestamp lastUpdated, Timestamp lastSuccess, long id, String name, int profileIconId, long revisionDate, long summonerLevel) {
        super(id, name, profileIconId, revisionDate, summonerLevel);
        this.lastUpdated = lastUpdated;
        this.lastSuccess = lastSuccess;
    }

    public DBSummoner(Timestamp lastUpdated, Timestamp lastSuccess, SummonerDto sum) {
        this(lastUpdated, lastSuccess, sum.getId(), sum.getName(), sum.getProfileIconId(), sum.getRevisionDate(), sum.getSummonerLevel());
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public Timestamp getLastSuccess() {
        return lastSuccess;
    }

}
