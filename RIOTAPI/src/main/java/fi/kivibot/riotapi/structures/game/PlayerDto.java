package fi.kivibot.riotapi.structures.game;

public class PlayerDto {

    private int championId;
    private long summonerId;
    private int teamId;

    public PlayerDto(int championId, long summonerId, int teamId) {
        this.championId = championId;
        this.summonerId = summonerId;
        this.teamId = teamId;
    }

    public int getChampionId() {
        return championId;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.championId;
        hash = 53 * hash + (int) (this.summonerId ^ (this.summonerId >>> 32));
        hash = 53 * hash + this.teamId;
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
        final PlayerDto other = (PlayerDto) obj;
        if (this.championId != other.championId) {
            return false;
        }
        if (this.summonerId != other.summonerId) {
            return false;
        }
        if (this.teamId != other.teamId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayerDto{" + "championId=" + championId + ", summonerId=" + summonerId + ", teamId=" + teamId + '}';
    }
}
