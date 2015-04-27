package fi.kivibot.gameinfoback.api.struct.summoner;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class MasteryPages {
    
    private List<MasteryPage> pages;
    private long summonerId;

    public List<MasteryPage> getPages() {
        return pages;
    }

    public void setPages(List<MasteryPage> pages) {
        this.pages = pages;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }

    @Override
    public String toString() {
        return "MasteryPages{" + "pages=" + pages + ", summonerId=" + summonerId + '}';
    }
    
}
