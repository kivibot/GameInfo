package fi.kivibot.gameinfoback.api.old;

import fi.kivibot.gameinfoback.api.old.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.MiniSeries;

/**
 *
 * @author Nicklas
 */
public class SoloLeagueEntry extends LeagueEntry {
    
    private final String tier;

    public SoloLeagueEntry(String tier, String division, boolean freshBlood,
            boolean hotStreak, boolean inactive, boolean veteran, 
            int leaguePoints, int losses, MiniSeries miniSeries, 
            String playerOrTeamId, String playerOrTeamName, int wins) {
        super(division, freshBlood, hotStreak, inactive, veteran, leaguePoints, losses, miniSeries, playerOrTeamId, playerOrTeamName, wins);
        this.tier = tier;
    }

    public String getTier() {
        return tier;
    }

    
    
}
