package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
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
        return get(CurrentGameInfo.class,
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/observer-mode/rest/consumer/getSpectatorGameInfo/{0}/{1}", region.getPlatformId(), "" + id)
                .append("?api_key={0}", apiKey));
    }

}
