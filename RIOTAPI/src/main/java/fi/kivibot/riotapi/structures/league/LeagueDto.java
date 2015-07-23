package fi.kivibot.riotapi.structures.league;

import java.util.List;
import java.util.Objects;

public class LeagueDto {

    private List<LeagueEntryDto> entries;
    private String name;
    private String participantId;
    private String queue;
    private String tier;

    public LeagueDto(List<LeagueEntryDto> entries, String name, String participantId, String queue, String tier) {
        this.entries = entries;
        this.name = name;
        this.participantId = participantId;
        this.queue = queue;
        this.tier = tier;
    }

    public List<LeagueEntryDto> getEntries() {
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
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.entries);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.participantId);
        hash = 19 * hash + Objects.hashCode(this.queue);
        hash = 19 * hash + Objects.hashCode(this.tier);
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
        final LeagueDto other = (LeagueDto) obj;
        if (!Objects.equals(this.entries, other.entries)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.participantId, other.participantId)) {
            return false;
        }
        if (!Objects.equals(this.queue, other.queue)) {
            return false;
        }
        if (!Objects.equals(this.tier, other.tier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LeagueDto{" + "entries=" + entries + ", name=" + name + ", participantId=" + participantId + ", queue=" + queue + ", tier=" + tier + '}';
    }

}
