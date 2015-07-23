package fi.kivibot.riotapi.structures.summoner;

import java.util.List;
import java.util.Objects;

public class MasteryPageDto {

    private boolean current;
    private long id;
    private List<MasteryDto> masteries;
    private String name;

    public MasteryPageDto(boolean current, long id, List<MasteryDto> masteries, String name) {
        this.current = current;
        this.id = id;
        this.masteries = masteries;
        this.name = name;
    }

    public boolean isCurrent() {
        return current;
    }

    public long getId() {
        return id;
    }

    public List<MasteryDto> getMasteries() {
        return masteries;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.current ? 1 : 0);
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.masteries);
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final MasteryPageDto other = (MasteryPageDto) obj;
        if (this.current != other.current) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.masteries, other.masteries)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MasteryPageDto{" + "current=" + current + ", id=" + id + ", masteries=" + masteries + ", name=" + name + '}';
    }
}
