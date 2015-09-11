package fi.kivibot.riotapi.internal;

import com.google.gson.reflect.TypeToken;
import fi.kivibot.riotapi.constant.LeagueQueueType;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
import fi.kivibot.riotapi.structures.current_game.CurrentGameInfo;
import fi.kivibot.riotapi.structures.league.LeagueDto;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Nicklas
 */
public class LeagueAPI extends APIBase {

    public LeagueAPI(String apiKey) {
        super(apiKey);
    }

    public Map<String, List<LeagueDto>> getBySummoner(Region region, Collection<Long> ids) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/by-summoner/{1}", region.getPlatformId(), String.join(",", ids.stream().map(l -> l.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, List<LeagueDto>> getEntryBySummoner(Region region, Collection<Long> ids) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/by-summoner/{1}/entry", region.getPlatformId(), String.join(",", ids.stream().map(l -> l.toString()).collect(Collectors.toList())))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, List<LeagueDto>> getByTeam(Region region, Collection<String> ids) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/by-team/{1}", region.getPlatformId(), String.join(",", ids))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, List<LeagueDto>> getEntryByTeam(Region region, Collection<String> ids) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/by-team/{1}/entry", region.getPlatformId(), String.join(",", ids))
                .append("?api_key={0}", apiKey));
    }

    public Map<String, List<LeagueDto>> getChallenger(Region region, LeagueQueueType type) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/challenger?type={1}", region.getPlatformId(), type.name())
                .append("&api_key={0}", apiKey));
    }

    public Map<String, List<LeagueDto>> getMaster(Region region, LeagueQueueType type) throws IOException, RateLimitException, RiotException {
        return (Map<String, List<LeagueDto>>) get(new TypeToken<Map<String, List<LeagueDto>>>() {
        }.getType(),
                new QueryBuilder().append("https://{0}.api.pvp.net", region.getName())
                .append("/api/lol/{0}/v2.5/league/master?type={1}", region.getPlatformId(), type.name())
                .append("&api_key={0}", apiKey));
    }

}
