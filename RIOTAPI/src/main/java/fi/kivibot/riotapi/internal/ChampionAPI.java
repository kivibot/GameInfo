package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestResult;
import fi.kivibot.riotapi.structures.champion.ChampionDto;
import fi.kivibot.riotapi.structures.champion.ChampionListDto;
import java.io.IOException;

/**
 * Version: 1.2
 *
 * @author Nicklas
 */
public class ChampionAPI {

    private final String apiKey;

    public ChampionAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public ChampionListDto getAll(Region region) throws IOException, RateLimitException, RiotException {
        return getAll(region, false);
    }

    public ChampionListDto getAll(Region region, boolean f2p) throws IOException, RateLimitException, RiotException {
        return get(ChampionListDto.class,
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.2/champion?freeToPlay={1}", region.name(), "" + f2p)
                .append("&api_key={0}", apiKey));
    }

    public ChampionDto getById(Region region, int id) throws IOException, RateLimitException, RiotException {
        return get(ChampionDto.class,
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.2/champion/{1}", region.name(), "" + id)
                .append("?api_key={0}", apiKey));
    }

    private <T> T get(Class<T> type, QueryBuilder query) throws IOException, RateLimitException, RiotException {
        RestResult<T> result = new RestClient().getJSON(query, type);
        if (result.getResponseCode().isSuccess()) {
            return result.getValue();
        } else {
            if (result.getResponseCode().getCode() == 429) {
                throw new RateLimitException("Rate limit exceeded");
            } else if (result.getResponseCode().isServerError()) {
                throw new RiotException("Error: " + result.getResponseCode().getCode());
            } else if (result.getResponseCode().isClientError()) {
                throw new IOException("Error: " + result.getResponseCode().getCode());
            }
            throw new RuntimeException("Unknown error! " + result.getResponseCode().getCode());
        }
    }

}
