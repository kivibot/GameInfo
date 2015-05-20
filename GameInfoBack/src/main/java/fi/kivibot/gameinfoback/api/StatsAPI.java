package fi.kivibot.gameinfoback.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.kivibot.gameinfoback.HTTPGetter;
import fi.kivibot.gameinfoback.api.exception.RateLimitException;
import fi.kivibot.gameinfoback.api.exception.RequestException;
import fi.kivibot.gameinfoback.api.exception.RiotSideException;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameInfo;
import fi.kivibot.gameinfoback.api.struct.stats.PlayerStatsSummaryList;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class StatsAPI {
    
    private final String apiKey;

    public StatsAPI(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public RankedStats getRankedStats(Platform platform, long id) throws RiotSideException, RateLimitException, RequestException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.3/stats/by-summoner/");
        url.append(id);
        url.append("/ranked?api_key=").append(apiKey);

        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), RankedStats.class);
            case 400:
                throw new RequestException("Bad request");
            case 401:
                throw new RequestException("Unauthorized");
            case 404:
                //throw new RequestException("No summoner data found for any specified inputs");
                return null;
            case 429:
                throw new RateLimitException("Rate limit exceeded");
            case 500:
                throw new RiotSideException("Internal server error");
            case 503:
                throw new RiotSideException("Service unavailable");
            default:
                throw new RiotSideException("Unknown response code: " + get.getResponseCode());
        }
    }
    
    public PlayerStatsSummaryList getStatsSummary(Platform platform, long id) throws RiotSideException, RateLimitException, RequestException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.3/stats/by-summoner/");
        url.append(id);
        url.append("/summary?api_key=").append(apiKey);

        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), PlayerStatsSummaryList.class);
            case 400:
                throw new RequestException("Bad request");
            case 401:
                throw new RequestException("Unauthorized");
            case 404:
                //throw new RequestException("No summoner data found for any specified inputs");
                return null;
            case 429:
                throw new RateLimitException("Rate limit exceeded");
            case 500:
                throw new RiotSideException("Internal server error");
            case 503:
                throw new RiotSideException("Service unavailable");
            default:
                throw new RiotSideException("Unknown response code: " + get.getResponseCode());
        }
    }
    
}
