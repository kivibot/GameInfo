/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.RiotAPI;
import fi.kivibot.gameinfoback.api.RiotSideException;
import fi.kivibot.gameinfoback.api.old.ApiHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.old.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.old.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.old.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.old.structures.Summoner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicklas
 */
public class Main {

    public static void main(String[] args) throws IOException, RateLimitException, RequestException, RitoException, RiotSideException, fi.kivibot.gameinfoback.api.RequestException {
//        new GameInfoBackend((int) ConfigManager.getDefault().getLong("port", 8080),
//                ConfigManager.getDefault().getString("apiKey", null)).start();
        RiotAPI api = new RiotAPI(ConfigManager.getDefault().getString("apiKey", null), Platform.EUNE);
        List<String> names = new ArrayList<>();
        names.add("kivibot");
        names.add("pepsodent master");
        names.add("pownr");
        System.out.println(api.summoner.getSummonesByNames(names));
        List<Long> ids = new ArrayList<>();
        ids.add(Long.valueOf(32211843));
        ids.add(Long.valueOf(35605791));
        System.out.println(api.summoner.getSummonersByIds(ids));        
        System.out.println(api.summoner.getNamesByIds(ids));       
        System.out.println(api.summoner.getMasteriesByIds(ids));
        System.out.println(api.summoner.getRunesByIds(ids));
        System.out.println(api.currentGame.getCurrentGameInfo(23845314));

    }
}
