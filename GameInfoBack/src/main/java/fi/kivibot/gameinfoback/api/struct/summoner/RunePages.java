package fi.kivibot.gameinfoback.api.struct.summoner;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class RunePages {
    
    private List<RunePage> pages;
    private long summonerId;

    public List<RunePage> getPages() {
        return pages;
    }

    public void setPages(List<RunePage> pages) {
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
        return "RunePages{" + "pages=" + pages + ", summonerId=" + summonerId + '}';
    }
    
}
