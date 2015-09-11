package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
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
        return get(RecentGamesDto.class,
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.3/game/by-summoner/{1}/recent", region.name(), "" + id)
                .append("?api_key={0}", apiKey));
    }

}
