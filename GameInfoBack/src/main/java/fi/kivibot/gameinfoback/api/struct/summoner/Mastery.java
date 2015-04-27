package fi.kivibot.gameinfoback.api.struct.summoner;

/**
 *
 * @author Nicklas
 */
public class Mastery {
    
    private int id;
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Mastery{" + "id=" + id + ", rank=" + rank + '}';
    }
    
}
