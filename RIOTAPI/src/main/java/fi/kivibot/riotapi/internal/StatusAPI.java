package fi.kivibot.riotapi.internal;

import com.google.gson.reflect.TypeToken;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestRequest;
import fi.kivibot.riotapi.structures.status.Shard;
import fi.kivibot.riotapi.structures.status.ShardStatus;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Nicklas
 */
public class StatusAPI extends APIBase {

    private final RestClient client = new RestClient("http://status.leagueoflegends.com");

    public StatusAPI() {
        super("");
    }

    public List<Shard> getShards() throws IOException, RiotException {
        try {
            RestRequest request = new RestRequest("/shards");
            return (List<Shard>) get(new TypeToken<List<Shard>>() {
            }.getType(), client, request);
        } catch (RateLimitException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ShardStatus getShardStatusByRegion(Region region) throws IOException, RiotException {
        try {
            RestRequest request = new RestRequest("/shards/{region}");
            request.addUrlSegment("region", region.getName());
            return get(ShardStatus.class, client, request);
        } catch (RateLimitException ex) {
            throw new RuntimeException(ex);
        }
    }

}
