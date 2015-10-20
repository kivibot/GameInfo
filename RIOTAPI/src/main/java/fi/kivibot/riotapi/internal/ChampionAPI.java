package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestRequest;
import fi.kivibot.riotapi.structures.champion.ChampionDto;
import fi.kivibot.riotapi.structures.champion.ChampionListDto;
import java.io.IOException;

/**
 * Version: 1.2
 *
 * @author Nicklas
 */
public class ChampionAPI extends APIBase {

    public ChampionAPI(String apiKey) {
        super(apiKey);
    }

    public ChampionListDto getAll(Region region) throws IOException, RateLimitException, RiotException {
        return getAll(region, false);
    }

    public ChampionListDto getAll(Region region, boolean f2p) throws IOException, RateLimitException, RiotException {
        RestClient client = new RestClient("");
        RestRequest request = new RestRequest("https://{region}.api.pvp.net/api/lol/{region}/v1.2/champion");
        request.addUrlSegment("region", region.getName());
        if (f2p) {
            request.addParameter("freeToPlay", true);
        }
        request.addParameter("api_key", apiKey);
        return get(ChampionListDto.class, client, request);
    }

    public ChampionDto getById(Region region, int id) throws IOException, RateLimitException, RiotException {
        RestClient client = new RestClient("");
        RestRequest request = new RestRequest("https://{region}.api.pvp.net/api/lol/{region}/v1.2/champion/{id}");
        request.addUrlSegment("region", region.getName());
        request.addUrlSegment("id", id);
        request.addParameter("api_key", apiKey);
        return get(ChampionDto.class, client, request);
    }

}
