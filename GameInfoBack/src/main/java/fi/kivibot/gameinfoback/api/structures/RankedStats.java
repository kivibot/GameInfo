package fi.kivibot.gameinfoback.api.structures;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class RankedStats {

    private final List<ChampionStats> champions;
    private final long modifyDate;
    private final long summonerId;

    public RankedStats(List<ChampionStats> champions, long modifyDate, long summonerId) {
        this.champions = champions;
        this.modifyDate = modifyDate;
        this.summonerId = summonerId;
    }

    public List<ChampionStats> getChampions() {
        return champions;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public long getSummonerId() {
        return summonerId;
    }

    @Override
    public String toString() {
        return "RankedStats{" + "champions=" + champions + ", modifyDate=" + modifyDate + ", summonerId=" + summonerId + '}';
    }

}
