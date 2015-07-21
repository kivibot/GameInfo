package fi.kivibot.riotapi.structures.game;

import java.util.List;
import java.util.Objects;

public class GameDto {

    private int championId;
    private long createDate;
    private List<PlayerDto> fellowPlayers;
    private long gameId;
    private String gameMode;
    private String gameType;
    private boolean invalid;
    private int ipEarned;
    private int level;
    private int mapId;
    private int spell1;
    private int spell2;
    private RawStatsDto stats;
    private String subType;
    private int teamId;

    public GameDto(int championId, long createDate, List<PlayerDto> fellowPlayers, long gameId, String gameMode, String gameType, boolean invalid, int ipEarned, int level, int mapId, int spell1, int spell2, RawStatsDto stats, String subType, int teamId) {
        this.championId = championId;
        this.createDate = createDate;
        this.fellowPlayers = fellowPlayers;
        this.gameId = gameId;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.invalid = invalid;
        this.ipEarned = ipEarned;
        this.level = level;
        this.mapId = mapId;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.stats = stats;
        this.subType = subType;
        this.teamId = teamId;
    }

    public int getChampionId() {
        return championId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public List<PlayerDto> getFellowPlayers() {
        return fellowPlayers;
    }

    public long getGameId() {
        return gameId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public int getIpEarned() {
        return ipEarned;
    }

    public int getLevel() {
        return level;
    }

    public int getMapId() {
        return mapId;
    }

    public int getSpell1() {
        return spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public RawStatsDto getStats() {
        return stats;
    }

    public String getSubType() {
        return subType;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.championId;
        hash = 11 * hash + (int) (this.createDate ^ (this.createDate >>> 32));
        hash = 11 * hash + Objects.hashCode(this.fellowPlayers);
        hash = 11 * hash + (int) (this.gameId ^ (this.gameId >>> 32));
        hash = 11 * hash + Objects.hashCode(this.gameMode);
        hash = 11 * hash + Objects.hashCode(this.gameType);
        hash = 11 * hash + (this.invalid ? 1 : 0);
        hash = 11 * hash + this.ipEarned;
        hash = 11 * hash + this.level;
        hash = 11 * hash + this.mapId;
        hash = 11 * hash + this.spell1;
        hash = 11 * hash + this.spell2;
        hash = 11 * hash + Objects.hashCode(this.stats);
        hash = 11 * hash + Objects.hashCode(this.subType);
        hash = 11 * hash + this.teamId;
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
        final GameDto other = (GameDto) obj;
        if (this.championId != other.championId) {
            return false;
        }
        if (this.createDate != other.createDate) {
            return false;
        }
        if (!Objects.equals(this.fellowPlayers, other.fellowPlayers)) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.gameMode, other.gameMode)) {
            return false;
        }
        if (!Objects.equals(this.gameType, other.gameType)) {
            return false;
        }
        if (this.invalid != other.invalid) {
            return false;
        }
        if (this.ipEarned != other.ipEarned) {
            return false;
        }
        if (this.level != other.level) {
            return false;
        }
        if (this.mapId != other.mapId) {
            return false;
        }
        if (this.spell1 != other.spell1) {
            return false;
        }
        if (this.spell2 != other.spell2) {
            return false;
        }
        if (!Objects.equals(this.stats, other.stats)) {
            return false;
        }
        if (!Objects.equals(this.subType, other.subType)) {
            return false;
        }
        if (this.teamId != other.teamId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameDto{" + "championId=" + championId + ", createDate=" + createDate + ", fellowPlayers=" + fellowPlayers + ", gameId=" + gameId + ", gameMode=" + gameMode + ", gameType=" + gameType + ", invalid=" + invalid + ", ipEarned=" + ipEarned + ", level=" + level + ", mapId=" + mapId + ", spell1=" + spell1 + ", spell2=" + spell2 + ", stats=" + stats + ", subType=" + subType + ", teamId=" + teamId + '}';
    }
}
