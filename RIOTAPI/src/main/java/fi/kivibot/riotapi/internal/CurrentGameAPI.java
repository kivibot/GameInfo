package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestRequest;
import fi.kivibot.riotapi.structures.current_game.CurrentGameInfo;
import java.io.IOException;

/**
 *
 * @author Nicklas
 */
public class CurrentGameAPI extends APIBase {

    public CurrentGameAPI(String apiKey) {
        super(apiKey);
    }

    public CurrentGameInfo getBySummoner(Region region, long id) throws IOException, RateLimitException, RiotException {
        RestClient client = new RestClient("");
        RestRequest request = new RestRequest("https://{region}.api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/{platform}/{id}");
        request.addUrlSegment("region", region.getName());
        request.addUrlSegment("platform", region.getPlatformId());
        request.addUrlSegment("id", id);
        request.addParameter("api_key", apiKey);
        return get(CurrentGameInfo.class, client, request);
    }

}
