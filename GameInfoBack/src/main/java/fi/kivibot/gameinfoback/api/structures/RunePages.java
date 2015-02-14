package fi.kivibot.gameinfoback.api.structures;

import java.util.Set;

/**
 *
 * @author Nicklas
 */
public class RunePages {
    
    private final Set<RunePage> runePages;
    private final long SummonerId;

    public RunePages(Set<RunePage> runePages, long SummonerId) {
        this.runePages = runePages;
        this.SummonerId = SummonerId;
    }

    public Set<RunePage> getRunePages() {
        return runePages;
    }

    public long getSummonerId() {
        return SummonerId;
    }

    @Override
    public String toString() {
        return "RunePages{" + "runePages=" + runePages + ", SummonerId=" + SummonerId + '}';
    }
    
    
}
