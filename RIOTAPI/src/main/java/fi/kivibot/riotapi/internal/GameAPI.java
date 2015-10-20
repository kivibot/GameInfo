package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestRequest;
import fi.kivibot.riotapi.structures.game.RecentGamesDto;
import java.io.IOException;

/**
 *
 * @author Nicklas
 */
public class GameAPI extends APIBase {

    public GameAPI(String apiKey) {
        super(apiKey);
    }

    public RecentGamesDto getBySummoner(Region region, long id) throws IOException, RateLimitException, RiotException {
        RestClient client = new RestClient("");
        RestRequest request = new RestRequest("https://{region}.api.pvp.net/api/lol/{region}/v1.3/game/by-summoner/{id}/recent");
        request.addUrlSegment("region", region.getName());
        request.addUrlSegment("id", id);
        request.addParameter("api_key", apiKey);
        return get(RecentGamesDto.class, client, request);
    }

}
