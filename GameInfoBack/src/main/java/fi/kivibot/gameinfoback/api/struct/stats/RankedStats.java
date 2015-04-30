package fi.kivibot.gameinfoback.api.struct.stats;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class RankedStats {
    
    private List<ChampionStats> champions;
    private long modifyDate;
    private long summonerId;

    public List<ChampionStats> getChampions() {
        return champions;
    }

    public void setChampions(List<ChampionStats> champions) {
        this.champions = champions;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }

    @Override
    public String toString() {
        return "RankedStats{" + "champions=" + champions + ", modifyDate=" + modifyDate + ", summonerId=" + summonerId + '}';
    }
    
}
