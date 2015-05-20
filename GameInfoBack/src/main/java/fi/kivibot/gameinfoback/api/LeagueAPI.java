package fi.kivibot.gameinfoback.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.kivibot.gameinfoback.HTTPGetter;
import fi.kivibot.gameinfoback.api.exception.RateLimitException;
import fi.kivibot.gameinfoback.api.exception.RequestException;
import fi.kivibot.gameinfoback.api.exception.RiotSideException;
import fi.kivibot.gameinfoback.api.struct.league.League;
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
public class LeagueAPI {

    private final String apiKey;

    public LeagueAPI(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public Map<String, List<League>> getLeagueEntriesByIds(Platform platform, List<Long> ids) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v2.5/league/by-summoner/");
        for (int i = 0; i < ids.size(); i++) {
            url.append(ids.get(i));
            if (i != ids.size() - 1) {
                url.append(",");
            }
        }
        url.append("/entry?api_key=").append(apiKey);
        System.out.println(url);

        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, List<League>>>() {
                }.getType());
            case 400:
                throw new RequestException("Bad request");
            case 401:
                throw new RequestException("Unauthorized");
            case 404:
                //throw new RequestException("No summoner data found for any specified inputs");
                return new HashMap<>();
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
