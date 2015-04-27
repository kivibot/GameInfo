package fi.kivibot.gameinfoback.api;

/**
 *
 * @author Nicklas
 */
public class RiotAPI {

    
    public final CurrentGameAPI currentGame;
    public final SummonerAPI summoner;

    public RiotAPI(String apiKey, Platform platform) {
        summoner = new SummonerAPI(apiKey, platform);
        currentGame = new CurrentGameAPI(apiKey, platform);
    }

}