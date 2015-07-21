package fi.kivibot.riotapi.structures.game;

import java.util.List;
import java.util.Objects;

public class RecentGamesDto {

    private List<GameDto> games;
    private long summonerId;

    public RecentGamesDto(List<GameDto> games, long summonerId) {
        this.games = games;
        this.summonerId = summonerId;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public long getSummonerId() {
        return summonerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.games);
        hash = 37 * hash + (int) (this.summonerId ^ (this.summonerId >>> 32));
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
        final RecentGamesDto other = (RecentGamesDto) obj;
        if (!Objects.equals(this.games, other.games)) {
            return false;
        }
        if (this.summonerId != other.summonerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RecentGamesDto{" + "games=" + games + ", summonerId=" + summonerId + '}';
    }

}
