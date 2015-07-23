package fi.kivibot.riotapi.structures.summoner;

public class MasteryDto {
    
    private int id;
    private int rank;

    public MasteryDto(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.rank;
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
        final MasteryDto other = (MasteryDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.rank != other.rank) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MasteryDto{" + "id=" + id + ", rank=" + rank + '}';
    }
    
}
