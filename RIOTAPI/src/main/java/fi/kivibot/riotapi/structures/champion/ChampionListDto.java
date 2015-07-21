package fi.kivibot.riotapi.structures.champion;

import java.util.List;
import java.util.Objects;

public class ChampionListDto {

    private List<ChampionDto> champions;

    public ChampionListDto(List<ChampionDto> champions) {
        this.champions = champions;
    }

    public List<ChampionDto> getChampions() {
        return champions;
    }

    @Override
    public String toString() {
        return "ChampionListDto{" + "champions=" + champions + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.champions);
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
        final ChampionListDto other = (ChampionListDto) obj;
        if (!Objects.equals(this.champions, other.champions)) {
            return false;
        }
        return true;
    }
}
