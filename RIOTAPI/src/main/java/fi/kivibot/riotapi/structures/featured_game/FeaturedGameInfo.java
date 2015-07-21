package fi.kivibot.riotapi.structures.featured_game;

import fi.kivibot.riotapi.structures.current_game.BannedChampion;
import fi.kivibot.riotapi.structures.current_game.Observer;
import java.util.List;
import java.util.Objects;

public class FeaturedGameInfo {
    
    private List<BannedChampion> bannedChampions;
    private long gameId;
    private long gameLength;
    private String gameMode;
    private long gameQueueConfigId;
    private long gameStartTime;
    private String gameType;
    private long mapId;
    private Observer observers;
    private List<Participant> participants;
    private String platformId;

    public FeaturedGameInfo(List<BannedChampion> bannedChampions, long gameId, long gameLength, String gameMode, long gameQueueConfigId, long gameStartTime, String gameType, long mapId, Observer observers, List<Participant> participants, String platformId) {
        this.bannedChampions = bannedChampions;
        this.gameId = gameId;
        this.gameLength = gameLength;
        this.gameMode = gameMode;
        this.gameQueueConfigId = gameQueueConfigId;
        this.gameStartTime = gameStartTime;
        this.gameType = gameType;
        this.mapId = mapId;
        this.observers = observers;
        this.participants = participants;
        this.platformId = platformId;
    }

    public List<BannedChampion> getBannedChampions() {
        return bannedChampions;
    }

    public long getGameId() {
        return gameId;
    }

    public long getGameLength() {
        return gameLength;
    }

    public String getGameMode() {
        return gameMode;
    }

    public long getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public String getGameType() {
        return gameType;
    }

    public long getMapId() {
        return mapId;
    }

    public Observer getObservers() {
        return observers;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.bannedChampions);
        hash = 67 * hash + (int) (this.gameId ^ (this.gameId >>> 32));
        hash = 67 * hash + (int) (this.gameLength ^ (this.gameLength >>> 32));
        hash = 67 * hash + Objects.hashCode(this.gameMode);
        hash = 67 * hash + (int) (this.gameQueueConfigId ^ (this.gameQueueConfigId >>> 32));
        hash = 67 * hash + (int) (this.gameStartTime ^ (this.gameStartTime >>> 32));
        hash = 67 * hash + Objects.hashCode(this.gameType);
        hash = 67 * hash + (int) (this.mapId ^ (this.mapId >>> 32));
        hash = 67 * hash + Objects.hashCode(this.observers);
        hash = 67 * hash + Objects.hashCode(this.participants);
        hash = 67 * hash + Objects.hashCode(this.platformId);
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
        final FeaturedGameInfo other = (FeaturedGameInfo) obj;
        if (!Objects.equals(this.bannedChampions, other.bannedChampions)) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.gameLength != other.gameLength) {
            return false;
        }
        if (!Objects.equals(this.gameMode, other.gameMode)) {
            return false;
        }
        if (this.gameQueueConfigId != other.gameQueueConfigId) {
            return false;
        }
        if (this.gameStartTime != other.gameStartTime) {
            return false;
        }
        if (!Objects.equals(this.gameType, other.gameType)) {
            return false;
        }
        if (this.mapId != other.mapId) {
            return false;
        }
        if (!Objects.equals(this.observers, other.observers)) {
            return false;
        }
        if (!Objects.equals(this.participants, other.participants)) {
            return false;
        }
        if (!Objects.equals(this.platformId, other.platformId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FeaturedGameInfo{" + "bannedChampions=" + bannedChampions + ", gameId=" + gameId + ", gameLength=" + gameLength + ", gameMode=" + gameMode + ", gameQueueConfigId=" + gameQueueConfigId + ", gameStartTime=" + gameStartTime + ", gameType=" + gameType + ", mapId=" + mapId + ", observers=" + observers + ", participants=" + participants + ", platformId=" + platformId + '}';
    }
    
}
