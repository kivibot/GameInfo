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

    private final LoadingCache<String, DBSummoner> summonerByNameCache;
    private final LoadingCache<Long, DBSummoner> summonerCache;

    public SummonerBackend(String apiKey) {
        SummonerAPI summonerAPI = new SummonerAPI(apiKey);
        summonerByNameCache = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<String, DBSummoner>() {

            @Override
            public DBSummoner load(String k) throws Exception {
                Collection<String> names = new ArrayList<>();
                names.add(k);
                summonerAPI.getSummonerByName(Region.EUNE, null);
            }

            @Override
            public Map<String, DBSummoner> loadAll(Iterable<? extends String> keys) throws Exception {
                return super.loadAll(keys); //To change body of generated methods, choose Tools | Templates.
            }

        });
        summonerCache = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<Long, DBSummoner>() {

            @Override
            public DBSummoner load(Long k) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Map<Long, DBSummoner> loadAll(Iterable<? extends Long> keys) throws Exception {
                return super.loadAll(keys); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public SummonerDto getSummonerByName(Region region, String name) {
        return null;
    }

    public CurrentGameInfo getCurrentGameInfoBySummoner(Region region, SummonerDto player) {
        return null;
    }

}
