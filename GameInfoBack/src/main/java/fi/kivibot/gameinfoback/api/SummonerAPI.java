package fi.kivibot.gameinfoback.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.kivibot.gameinfoback.HTTPGetter;
import fi.kivibot.gameinfoback.api.struct.summoner.MasteryPages;
import fi.kivibot.gameinfoback.api.struct.summoner.RunePages;
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
public class SummonerAPI {

    private final String apiKey;
    private final Platform platform;

    public SummonerAPI(String apiKey, Platform platform) {
        this.apiKey = apiKey;
        this.platform = platform;
    }

    /**
     *
     * @param names
     * @return
     * @throws RiotSideException
     * @throws RateLimitException
     * @throws RequestException
     * @throws IOException
     */
    public Map<String, Summoner> getSummonesByNames(List<String> names) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (names.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.4/summoner/by-name/");
        for (int i = 0; i < names.size(); i++) {
            try {
                url.append(URLEncoder.encode(getStandardizedName(names.get(i)), "utf-8"));
            } catch (UnsupportedEncodingException ex) {
                //Should never be thrown
                Logger.getLogger(SummonerAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (i != names.size() - 1) {
                url.append(",");
            }
        }
        url.append("?api_key=").append(apiKey);

        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, Summoner>>() {
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

    public Map<String, Summoner> getSummonersByIds(List<Long> ids) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.4/summoner/");
        for (int i = 0; i < ids.size(); i++) {
            url.append(ids.get(i));
            if (i != ids.size() - 1) {
                url.append(",");
            }
        }
        url.append("?api_key=").append(apiKey);
        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, Summoner>>() {
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

    //masteries
    public Map<String, MasteryPages> getMasteriesByIds(List<Long> ids) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.4/summoner/");
        for (int i = 0; i < ids.size(); i++) {
            url.append(ids.get(i));
            if (i != ids.size() - 1) {
                url.append(",");
            }
        }
        url.append("/masteries?api_key=").append(apiKey);
        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, MasteryPages>>() {
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
    
    
    /**
     * 
     * @param ids
     * @return
     * @throws RiotSideException
     * @throws RateLimitException
     * @throws RequestException
     * @throws IOException 
     */
    public Map<String, String> getNamesByIds(List<Long> ids) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.4/summoner/");
        for (int i = 0; i < ids.size(); i++) {
            url.append(ids.get(i));
            if (i != ids.size() - 1) {
                url.append(",");
            }
        }
        url.append("/name?api_key=").append(apiKey);
        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, String>>() {
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
    
    //runes
    public Map<String, RunePages> getRunesByIds(List<Long> ids) throws RiotSideException, RateLimitException, RequestException, IOException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        StringBuilder url = new StringBuilder();
        url.append("https://").append(platform.getRegion()).append(".api.pvp.net/api/lol/").append(platform.getRegion()).append("/v1.4/summoner/");
        for (int i = 0; i < ids.size(); i++) {
            url.append(ids.get(i));
            if (i != ids.size() - 1) {
                url.append(",");
            }
        }
        url.append("/runes?api_key=").append(apiKey);
        HTTPGetter get = new HTTPGetter(url.toString());
        switch (get.getResponseCode()) {
            case 200: //OK
                Gson gson = new Gson();
                return gson.fromJson(get.getContent(), new TypeToken<Map<String, RunePages>>() {
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
    
    public String getStandardizedName(String name) {
        return name.toLowerCase().replace(" ", "");
    }

}
