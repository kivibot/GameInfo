package fi.kivibot.gameinfoback.api.structures;

/**
 *
 * @author Nicklas
 */
public class MiniSeries {
    
    private final int losses;
    private final String progress;
    private final int target;
    private final int wins;

    public MiniSeries(int losses, String progress, int target, int wins) {
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
    public String toString() {
        return "MiniSeries{" + "losses=" + losses + ", progress=" + progress + ", target=" + target + ", wins=" + wins + '}';
    }
    
}
