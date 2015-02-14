package fi.kivibot.gameinfoback.api.structures;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class CurrentGame {

    public static String configIdToName(long gameQueueConfigId) {
        switch((int)gameQueueConfigId){
            case 0:
                return "CUSTOM";
            case 2:
                return "NORMAL 5x5 BLIND";
            default:
                return "error";
        }
    }

    private final List<BannedChampion> bannedChampions;
    private final long gameId, gameLength;
    private final String gameMode;
    private final long gameQueueConfigId, gameStartTime;
    private final String gameType;
    private final long mapId;
    private final String observersEncryptionKey;
    private final List<Participant> participants;
    private final String platformId;

    public CurrentGame(List<BannedChampion> bannedChampions, long gameId, long gameLength, String gameMode, long gameQueueConfigId, long gameStartTime, String gameType, long mapId, String observersEncryptionKey, List<Participant> participants, String platformId) {
        this.bannedChampions = bannedChampions;
        this.gameId = gameId;
        this.gameLength = gameLength;
        this.gameMode = gameMode;
        this.gameQueueConfigId = gameQueueConfigId;
        this.gameStartTime = gameStartTime;
        this.gameType = gameType;
        this.mapId = mapId;
        this.observersEncryptionKey = observersEncryptionKey;
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

    public String getObserversEncryptionKey() {
        return observersEncryptionKey;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    @Override
    public String toString() {
        return "CurrentGame{" + "bannedChampions=" + bannedChampions + ", gameId=" + gameId + ", gameLength=" + gameLength + ", gameMode=" + gameMode + ", gameQueueConfigId=" + gameQueueConfigId + ", gameStartTime=" + gameStartTime + ", gameType=" + gameType + ", mapId=" + mapId + ", observersEncryptionKey=" + observersEncryptionKey + ", participants=" + participants + ", platformId=" + platformId + '}';
    }
    
    

}
