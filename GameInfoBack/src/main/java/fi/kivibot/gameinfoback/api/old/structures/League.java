package fi.kivibot.gameinfoback.api.old.structures;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public class League {
    
    private final List<LeagueEntry> entries;
    private final String name;
    private final String participantId;
    private final String queue;
    private final String tier;

    public League(List<LeagueEntry> entries, String name, String participantId, String queue, String tier) {
        this.entries = entries;
        this.name = name;
        this.participantId = participantId;
        this.queue = queue;
        this.tier = tier;
    }

    public List<LeagueEntry> getEntries() {
        return entries;
    }

    public String getName() {
        return name;
    }

    public String getParticipantId() {
        return participantId;
    }

    public String getQueue() {
        return queue;
    }

    public String getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "League{" + "entries=" + entries + ", name=" + name + ", participantId=" + participantId + ", queue=" + queue + ", tier=" + tier + '}';
    }
    
}
