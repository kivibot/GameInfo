package fi.kivibot.gameinfoback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.RiotAPI;
import fi.kivibot.gameinfoback.api.struct.currentgame.BannedChampion;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameInfo;
import fi.kivibot.gameinfoback.api.struct.currentgame.CurrentGameParticipant;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import fi.kivibot.gameinfoback.storage.DataManager;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Spark;

/**
 *
 * @author Nicklas
 */
public class Tester {

    public void run(RiotAPI api, DataManager dm, Updater u) {        
        Spark.staticFileLocation(
                "/frontEnd/public_html");
        
        Spark.get("/:platform/:summoner/info", (req, res) -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String sname = api.summoner.getStandardizedName(URLDecoder.decode(req.params("summoner"), "utf-8"));
            System.out.println(sname);
            List<String> names = new ArrayList<>();
            names.add(sname);
            Platform p = Platform.getPlatform(req.params("platform"));
            Map<String, Summoner> sm = api.summoner.getSummonersByNames(p, names);
            if (sm.isEmpty()) {
                res.status(404);
                return "Summoner not found!";
            }
            Summoner main = sm.get(sname);
            CurrentGameInfo cgi = api.currentGame.getCurrentGameInfo(p, main.getId());
            if (cgi == null) {
                res.status(404);
                return "Game not found!";
            }
            List<Long> ids = new ArrayList<>();
            for (CurrentGameParticipant cgp : cgi.getParticipants()) {
                ids.add(cgp.getSummonerId());
            }
            UpdateResult ur = u.updateSummoners(p, ids);

            ids.removeAll(ur.getUpdated());

            Map<Long, RankedStats> rsm = dm.getRankedStats(p, ids);
            for (RankedStats rs : ur.getRankedStats()) {
                rsm.put(rs.getSummonerId(), rs);
            }

            List<Long> teams = new ArrayList<>(2);

            GameInfoOutput gio = new GameInfoOutput();
            
            if(ur.isRiotFailure()){
                gio.setRiotProblem(true);
            }
            if(ur.isRateLimited()){
                gio.setHeavyLoad(true);
            }
            
            gio.setBigName(main.getName());
            gio.setInfoLine("Mui.");
            List<List<Participant>> pls = new ArrayList<>();
            for (CurrentGameParticipant cgp : cgi.getParticipants()) {
                Participant pa = new Participant();
                if (cgp.getSummonerId() == main.getId()) {
                    pa.setHighlight(true);
                }
                int teamIndex = teams.indexOf(cgp.getTeamId());
                if (teamIndex == -1) {
                    teamIndex = teams.size();
                    teams.add(cgp.getTeamId());
                    pls.add(new ArrayList<>());
                }
                pa.setName(cgp.getSummonerName());
                pa.setChampionImage("Irelia.png");
                pa.setSpell1Image("SummonerFlash.png");
                pa.setSpell2Image("SummonerTeleport.png");
                pa.setRank("Challenger I");
                pa.setLP(1337);
                pa.setRankedStats(new RankeStatsOutput());
                pls.get(teamIndex).add(pa);
            }
            gio.setParticipants(pls);
            if (!cgi.getBannedChampions().isEmpty()) {
                List<List<BannedChampionOutput>> bcl = new ArrayList<>();
                for (int i = 0; i < teams.size(); i += 1) {
                    bcl.add(new ArrayList<>());
                }
                for (BannedChampion bc : cgi.getBannedChampions()) {
                    BannedChampionOutput bco = new BannedChampionOutput();
                    bco.setImage("Irelia.png");
                    bcl.get(teams.indexOf(bc.getTeamId())).add(bco);
                }
                gio.setBans(bcl);
            }

            return "<pre>" + gson.toJson(gio) + "</pre>";
        });
    }

}
