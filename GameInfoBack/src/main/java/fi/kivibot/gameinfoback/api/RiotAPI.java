package fi.kivibot.gameinfoback.api;

/**
 *
 * @author Nicklas
 */
public class RiotAPI {

    public final CurrentGameAPI currentGame;
    public final LeagueAPI league;
    public final StatsAPI stats;
    public final SummonerAPI summoner;

    private final String apiKey;

    public RiotAPI(String apiKey) {
        this.apiKey = apiKey;
        summoner = new SummonerAPI(apiKey);
        currentGame = new CurrentGameAPI(apiKey);
        stats = new StatsAPI(apiKey);
        league = new LeagueAPI(apiKey);
    }

}
