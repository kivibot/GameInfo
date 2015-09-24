package fi.kivibot.summonerbackend;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.internal.SummonerAPI;
import fi.kivibot.riotapi.structures.current_game.CurrentGameInfo;
import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nicklas
 */
public class SummonerBackend {


    public SummonerBackend(String apiKey) {
    }

    public SummonerDto getSummonerByName(Region region, String name) {
        return null;
    }

    public CurrentGameInfo getCurrentGameInfoBySummoner(Region region, SummonerDto player) {
        return null;
    }

}
