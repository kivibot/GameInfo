package fi.kivibot.riotapi.structures.summoner;

import java.util.List;
import java.util.Objects;

public class MasteryPagesDto {
    
    private List<MasteryPageDto> pages;
    private long summonerId;

    public MasteryPagesDto(List<MasteryPageDto> pages, long summonerId) {
        this.pages = pages;
        this.summonerId = summonerId;
    }

    public List<MasteryPageDto> getPages() {
        return pages;
    }

    public long getSummonerId() {
        return summonerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.pages);
        hash = 17 * hash + (int) (this.summonerId ^ (this.summonerId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MasteryPagesDto other = (MasteryPagesDto) obj;
        if (!Objects.equals(this.pages, other.pages)) {
            return false;
        }
        if (this.summonerId != other.summonerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MasteryPagesDto{" + "pages=" + pages + ", summonerId=" + summonerId + '}';
    }
    
}
