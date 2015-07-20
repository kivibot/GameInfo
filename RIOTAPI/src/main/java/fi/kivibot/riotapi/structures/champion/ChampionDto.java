package fi.kivibot.riotapi.structures.champion;

public class ChampionDto {

    private final boolean active;
    private final boolean botEnabled;
    private final boolean botMmEnabled;
    private final boolean freeToPlay;
    private final boolean id;
    private final boolean rankedPlayEnabled;

    public ChampionDto(boolean active, boolean botEnabled, boolean botMmEnabled, boolean freeToPlay, boolean id, boolean rankedPlayEnabled) {
        this.active = active;
        this.botEnabled = botEnabled;
        this.botMmEnabled = botMmEnabled;
        this.freeToPlay = freeToPlay;
        this.id = id;
        this.rankedPlayEnabled = rankedPlayEnabled;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isBotEnabled() {
        return botEnabled;
    }

    public boolean isBotMmEnabled() {
        return botMmEnabled;
    }

    public boolean isFreeToPlay() {
        return freeToPlay;
    }

    public boolean isId() {
        return id;
    }

    public boolean isRankedPlayEnabled() {
        return rankedPlayEnabled;
    }

    @Override
    public String toString() {
        return "ChampionDto{" + "active=" + active + ", botEnabled=" + botEnabled + ", botMmEnabled=" + botMmEnabled + ", freeToPlay=" + freeToPlay + ", id=" + id + ", rankedPlayEnabled=" + rankedPlayEnabled + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.active ? 1 : 0);
        hash = 37 * hash + (this.botEnabled ? 1 : 0);
        hash = 37 * hash + (this.botMmEnabled ? 1 : 0);
        hash = 37 * hash + (this.freeToPlay ? 1 : 0);
        hash = 37 * hash + (this.id ? 1 : 0);
        hash = 37 * hash + (this.rankedPlayEnabled ? 1 : 0);
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
        final ChampionDto other = (ChampionDto) obj;
        if (this.active != other.active) {
            return false;
        }
        if (this.botEnabled != other.botEnabled) {
            return false;
        }
        if (this.botMmEnabled != other.botMmEnabled) {
            return false;
        }
        if (this.freeToPlay != other.freeToPlay) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.rankedPlayEnabled != other.rankedPlayEnabled) {
            return false;
        }
        return true;
    }
}
