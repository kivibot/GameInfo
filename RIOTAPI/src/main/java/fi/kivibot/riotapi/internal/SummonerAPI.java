package fi.kivibot.riotapi.internal;

import com.google.gson.reflect.TypeToken;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
import fi.kivibot.riotapi.structures.summoner.MasteryPagesDto;
import fi.kivibot.riotapi.structures.summoner.RunePagesDto;
import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Nicklas
 */
public class SummonerAPI extends APIBase {

    public SummonerAPI(String apiKey) {
        super(apiKey);
    }

    public Map<String, SummonerDto> getSummonerByName(Region region, Collection<String> names) throws IOException, RiotException, RateLimitException {
        return (Map<String, SummonerDto>) get(new TypeToken<Map<String, SummonerDto>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.4/summoner/by-name/{1}", region.getName(), String.join(",", names))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, SummonerDto> getSummonerById(Region region, Collection<Long> ids) throws IOException, RiotException, RateLimitException {
        return (Map<String, SummonerDto>) get(new TypeToken<Map<String, SummonerDto>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.4/summoner/{1}", region.getName(), String.join(",", ids.stream().map(i -> i.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, MasteryPagesDto> getMasteryPagesById(Region region, Collection<Long> ids) throws IOException, RiotException, RateLimitException {
        return (Map<String, MasteryPagesDto>) get(new TypeToken<Map<String, MasteryPagesDto>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.4/summoner/{1}/masteries", region.getName(), String.join(",", ids.stream().map(i -> i.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, RunePagesDto> getRunePagesById(Region region, Collection<Long> ids) throws IOException, RiotException, RateLimitException {
        return (Map<String, RunePagesDto>) get(new TypeToken<Map<String, RunePagesDto>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.4/summoner/{1}/runes", region.getName(), String.join(",", ids.stream().map(i -> i.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, String> getNamesById(Region region, Collection<Long> ids) throws IOException, RiotException, RateLimitException {
        return (Map<String, String>) get(new TypeToken<Map<String, String>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v1.4/summoner/{1}/name", region.getName(), String.join(",", ids.stream().map(i -> i.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

}
