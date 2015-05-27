/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.storage.Cache;
import fi.kivibot.gameinfoback.storage.MariaDBManager;
import fi.kivibot.gameinfoback.storage.DatabaseException;
import fi.kivibot.gameinfoback.storage.DatabaseManager;
import fi.kivibot.gameinfoback.storage.DataManager;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.RiotAPI;
import fi.kivibot.gameinfoback.api.exception.RateLimitException;
import fi.kivibot.gameinfoback.api.exception.RequestException;
import fi.kivibot.gameinfoback.api.exception.RiotSideException;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameInfo;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameParticipant;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class Main {

    public static void main(String[] args) throws IOException, RiotSideException, fi.kivibot.gameinfoback.api.exception.RequestException, SQLException, PropertyVetoException, RateLimitException, DatabaseException {
//        new GameInfoBackend((int) ConfigManager.getDefault().getLong("port", 8080),
//                ConfigManager.getDefault().getString("apiKey", null)).start();
        RiotAPI api = new RiotAPI(ConfigManager.getDefault().getString("apiKey", null));
//        List<String> names = new ArrayList<>();
//        names.add("kivibot");
//        names.add("pepsodent master");
//        names.add("pownr");
//        System.out.println(api.summoner.getSummonesByNames(names));
//        List<Long> ids = new ArrayList<>();
//        ids.add(Long.valueOf(32211843));
//        ids.add(Long.valueOf(35605791));
//        System.out.println(api.summoner.getSummonersByIds(ids));        
//        System.out.println(api.summoner.getNamesByIds(ids));       
//        System.out.println(api.summoner.getMasteriesByIds(ids));
//        System.out.println(api.summoner.getRunesByIds(ids));
//        System.out.println(api.currentGame.getCurrentGameInfo(23845314));
//        System.out.println(api.stats.getRankedStats(23845314));
//        System.out.println(api.stats.getStatsSummary(23845314));
        
        String testSummoner = api.summoner.getStandardizedName("Stagor");
        Platform plat = Platform.EUW;
        
        List<String> testNames = new ArrayList<>();
        testNames.add(testSummoner);
//        Map<String, Summoner> sm = api.summoner.getSummonesByNames(plat, testNames);
//        Summoner s = sm.get(testSummoner);
        
        DatabaseManager dbm = new MariaDBManager();
        Cache c = new Cache();
        DataManager dm = new DataManager(c, dbm);
//        CurrentGameInfo cgi = api.currentGame.getCurrentGameInfo(plat, s.getId());
//        System.out.println(cgi.getGameId());
//
//        
        Updater u = new Updater(dm, api, RateLimitPolicy.LOOP);
//        
//        List<Long> ids = new ArrayList<>();
//        for(CurrentGameParticipant p : cgi.getParticipants()){
//            ids.add(p.getSummonerId());
//        }
//        
//        u.updateSummoners(plat, ids);
        
        new Tester().run(api, dm, u);

    }
}
