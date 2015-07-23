package fi.kivibot.riotapi.structures.summoner;

import java.util.List;
import java.util.Objects;

public class RunePagesDto {

    private List<RunePageDto> pages;
    private long summonerId;

    public RunePagesDto(List<RunePageDto> pages, long summonerId) {
        this.pages = pages;
        this.summonerId = summonerId;
    }

    public List<RunePageDto> getPages() {
        return pages;
    }

    public long getSummonerId() {
        return summonerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.pages);
        hash = 19 * hash + (int) (this.summonerId ^ (this.summonerId >>> 32));
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
        final RunePagesDto other = (RunePagesDto) obj;
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
        return "RunePagesDto{" + "pages=" + pages + ", summonerId=" + summonerId + '}';
    }

}
