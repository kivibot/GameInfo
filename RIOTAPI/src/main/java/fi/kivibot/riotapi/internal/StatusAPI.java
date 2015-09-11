package fi.kivibot.riotapi.internal;

import com.google.gson.reflect.TypeToken;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
import fi.kivibot.riotapi.structures.status.Shard;
import fi.kivibot.riotapi.structures.status.ShardStatus;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Nicklas
 */
public class StatusAPI extends APIBase {

    public StatusAPI() {
        super("");
    }

    public List<Shard> getShards() throws IOException, RiotException {
        try {
            return (List<Shard>) get(new TypeToken<List<Shard>>() {
            }.getType(), new QueryBuilder().append("http://status.leagueoflegends.com/shards"));
        } catch (RateLimitException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ShardStatus getShardStatusByRegion(Region region) throws IOException, RiotException {
        try {
            return get(ShardStatus.class, new QueryBuilder().append("http://status.leagueoflegends.com/shards/{0}", region.getName()));
        } catch (RateLimitException ex) {
            throw new RuntimeException(ex);
        }
    }

}
