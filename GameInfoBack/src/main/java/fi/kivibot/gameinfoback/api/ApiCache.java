package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.RankedStats;
import fi.kivibot.gameinfoback.api.structures.RunePages;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class ApiCache {

    private final ApiHandler api;
    private final Map<String, Summoner> summonerMap = new HashMap<>();
    private final Map<Long, List<League>> leagueMap = new HashMap<>();
    private final Map<Long, RankedStats> rankeStatsMap = new HashMap<>();
    private final Map<Long, RunePages> runePagesMap = new HashMap<>();

    public ApiCache(ApiHandler api) {
        this.api = api;
    }

    public void updateSummoners(List<String> summoners)
            throws IOException, RateLimitException, RequestException, RitoException {
        Map<String, Summoner> sumMap = api.getSummonersByNames(summoners);
        List<Long> ids = new ArrayList<>();
        for (Map.Entry<String, Summoner> e : sumMap.entrySet()) {
            Summoner os = summonerMap.get(e.getKey());
            if (os == null || os.getRevisionDate() != e.getValue().getRevisionDate()) {
                System.out.println("Updating: " + e.getValue());
                ids.add(e.getValue().getId());
                RankedStats rs = api.getRankedStats(e.getValue().getId());
                summonerMap.put(e.getKey(), e.getValue());
                rankeStatsMap.put(e.getValue().getId(), rs);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ApiCache.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }
        if (ids.size() > 0) {
            Map<String, List<League>> lm = api.getLeaguesEntry(ids);
            for (Map.Entry<String, List<League>> e : lm.entrySet()) {
                leagueMap.put(Long.valueOf(e.getKey()), e.getValue());
            }
            Map<String, RunePages> runePages = api.getRunes(ids);
            for (Map.Entry<String, RunePages> e : runePages.entrySet()) {
                runePagesMap.put(e.getValue().getSummonerId(), e.getValue());
            }
        }
    }

    public RankedStats getRankedStats(long id) {
        return rankeStatsMap.get(id);
    }

    public List<League> getLeagueEntries(long id) {
        return leagueMap.get(id);
    }

    public RunePages getRunes(long id) {
        return runePagesMap.get(id);
    }

}
