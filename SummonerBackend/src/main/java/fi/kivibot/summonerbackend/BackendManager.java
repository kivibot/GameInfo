package fi.kivibot.summonerbackend;

import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.internal.SummonerAPI;
import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Nicklas
 */
public class BackendManager {
    
    private final Cache<Long, DBSummoner> summonerCache;
    private final ExecutorService executorService;
    private final DBHandler dbHandler;
    private final String apiKey;

    
    public Map<Long, ? extends SummonerDto> getSummoners(Region region, Collection<Long> ids) throws ExecutionException, InterruptedException{
        UpdateSummoners us = new UpdateSummoners(new SummonerAPI(apiKey), summonerCache, dbHandler, region, ids);
        return executorService.submit(us).get();
    }
    
}
