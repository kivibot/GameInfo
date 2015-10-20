package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import java.io.IOException;

/**
 *
 * @author Nicklas
 */
public class FeaturedGamesAPI extends APIBase {

    public FeaturedGamesAPI(String apiKey) {
        super(apiKey);
    }

    public FeaturedGamesAPI getFeatured(Region region, long id) throws IOException, RateLimitException, RiotException {
        return get(FeaturedGamesAPI.class,
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/observer-mode/rest/featured")
                .append("?api_key={0}", apiKey));
    }

}
