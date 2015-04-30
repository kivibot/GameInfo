package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.api.exception.RateLimitException;
import fi.kivibot.gameinfoback.api.exception.RiotSideException;
import fi.kivibot.gameinfoback.api.exception.RequestException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.kivibot.gameinfoback.HTTPGetter;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameInfo;
import fi.kivibot.gameinfoback.api.struct.summoner.RunePages;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public class CurrentGameAPI {
    
    private final String apiKey;
    private final Platform platform;

    public CurrentGameAPI(String apiKey, Platform platform) {
        this.apiKey = apiKey;
        this.platform = platform;
    }
    
    public CurrentGameInfo getCurrentGameInfo(long id) throws RiotSideException, RateLimitException, RequestException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/").append(platform.getId()).append("/");
        url.append(id);
        url.append("?api_key=").append(apiKey);
        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), CurrentGameInfo.class);
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
