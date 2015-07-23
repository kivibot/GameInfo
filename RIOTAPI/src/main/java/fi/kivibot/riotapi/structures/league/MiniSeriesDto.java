package fi.kivibot.riotapi.structures.league;

import java.util.Objects;

public class MiniSeriesDto {

    private int losses;
    private String progress;
    private int target;
    private int wins;

    public MiniSeriesDto(int losses, String progress, int target, int wins) {
        this.losses = losses;
        this.progress = progress;
        this.target = target;
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public String getProgress() {
        return progress;
    }

    public int getTarget() {
        return target;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.losses;
        hash = 29 * hash + Objects.hashCode(this.progress);
        hash = 29 * hash + this.target;
        hash = 29 * hash + this.wins;
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
        final MiniSeriesDto other = (MiniSeriesDto) obj;
        if (this.losses != other.losses) {
            return false;
        }
        if (!Objects.equals(this.progress, other.progress)) {
            return false;
        }
        if (this.target != other.target) {
            return false;
        }
        if (this.wins != other.wins) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MiniSeriesDto{" + "losses=" + losses + ", progress=" + progress + ", target=" + target + ", wins=" + wins + '}';
    }

}
