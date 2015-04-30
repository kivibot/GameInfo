package fi.kivibot.gameinfoback.api.struct.league;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class League {
    
    private List<LeagueEntry> entries;
    private String name;
    private String participantId;
    private String queue;
    private String tier;

    public List<LeagueEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LeagueEntry> entries) {
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "League{" + "entries=" + entries + ", name=" + name + ", participantId=" + participantId + ", queue=" + queue + ", tier=" + tier + '}';
    }
    
}
