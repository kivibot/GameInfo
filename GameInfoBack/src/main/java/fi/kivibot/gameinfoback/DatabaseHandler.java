package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.old.SoloLeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.ChampionStats;
import fi.kivibot.gameinfoback.api.old.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.old.structures.RankedStats;
import fi.kivibot.gameinfoback.api.old.structures.RunePage;
import fi.kivibot.gameinfoback.api.old.structures.RunePages;
import fi.kivibot.gameinfoback.api.old.structures.Summoner;
import java.util.List;

/**
 *
 * @author Nicklas
 */
public interface DatabaseHandler {
    
    public void updatePlayerStatsSummary(Platform p, PlayerStatsSummary pss, Summoner s);
    public void updateRankedStats(Platform p, RankedStats cs, Summoner s);
    public void updateRunePages(Platform p, RunePages rp, Summoner s);
    public void updateSoloLeagueEntry(Platform p, String tier, LeagueEntry solo, Summoner s);    
    public void updateSummoner(Platform p, Summoner s);
    
    
    public List<Summoner> getUpdated(Platform p, List<Long> summoners);
    
    public List<Summoner> getSummoners(Platform p, List<Long> ids); 
    
    public PlayerStatsSummary getPlayerStatsSummary(Platform p, long sid, String type); 
    public List<PlayerStatsSummary> getAllPlayerStatsSummaries(Platform p, long sid); 
    
    public ChampionStats getChampionStats(Platform p, long sid, long cid);     
    public RankedStats getRankedStats(Platform p, long id);
    
    public RunePages getRunePages(Platform p, long sid);
    public RunePage getActiveRunepage(Platform p, long sid);
    
    public SoloLeagueEntry getSoloLeagueEntry(Platform p, long id);
    
}
