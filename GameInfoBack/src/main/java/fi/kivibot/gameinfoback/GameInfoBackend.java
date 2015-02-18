package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.ApiCache;
import fi.kivibot.gameinfoback.api.ApiHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.BannedChampion;
import fi.kivibot.gameinfoback.api.structures.ChampionStats;
import fi.kivibot.gameinfoback.api.structures.currentgame.CurrentGame;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.structures.Mastery;
import fi.kivibot.gameinfoback.api.structures.currentgame.Participant;
import fi.kivibot.gameinfoback.api.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.structures.RankedStats;
import fi.kivibot.gameinfoback.api.structures.RunePage;
import fi.kivibot.gameinfoback.api.structures.RuneSlot;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 *
 * @author Nicklas
 */
public class GameInfoBackend {

    private final int port;
    private final String apiKey;

    private ApiHandler api;
    private ApiCache apiCache;

    private Map<Long, String> champMap = new HashMap<>();
    private Map<Long, String> spellMap = new HashMap<>();
    private Map<Long, String> mapMap = new HashMap<>();
    private Map<Long, String> gqciMap = new HashMap<>();
    private Map<Long, RuneInfo> runeMap = new HashMap<>();
    private Map<String, String> nameMap = new HashMap<>();

    public GameInfoBackend(int port, String apiKey) {
        this.port = port;
        this.apiKey = apiKey;
    }

    public void start() {
        {
            JSONObject champs = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("champion.json")));
            ((JSONObject) champs.get("data")).entrySet().forEach((e) -> {
                JSONObject champ = (JSONObject) ((Map.Entry) e).getValue();
                champMap.put(Long.valueOf((String) champ.get("key")), (String) ((JSONObject) champ.get("image")).get("full"));
            });
        }
        {
            JSONObject spells = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("summoner.json")));
            ((JSONObject) spells.get("data")).entrySet().forEach((e) -> {
                JSONObject spell = (JSONObject) ((Map.Entry) e).getValue();
                spellMap.put(Long.valueOf((String) spell.get("key")), (String) ((JSONObject) spell.get("image")).get("full"));
            });
        }
        {
            JSONObject maps = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("map.json")));
            maps.entrySet().forEach((e) -> {
                mapMap.put((Long) ((Map.Entry) e).getValue(), (String) ((Map.Entry) e).getKey());
            });
        }
        {
            JSONObject gqcis = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("gameQueueConfigId.json")));
            gqcis.entrySet().forEach((e) -> {
                gqciMap.put((Long) ((Map.Entry) e).getValue(), (String) ((Map.Entry) e).getKey());
            });
        }
        {
            JSONObject runes = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("rune.json")));
            ((JSONObject) runes.get("data")).entrySet().forEach((e) -> {
                JSONObject rune = (JSONObject) ((Map.Entry) e).getValue();
                JSONObject stats = (JSONObject) rune.get("stats");
                List<RuneStat> statsl = new ArrayList<>();
                stats.forEach((name, value) -> {
                    if (value instanceof Long) {
                        statsl.add(new RuneStat((String) name, (double) (long) (Long) value));
                    } else {
                        statsl.add(new RuneStat((String) name, (Double) value));
                    }
                });
                runeMap.put(Long.valueOf((String) ((Map.Entry) e).getKey()), new RuneInfo((String) rune.get("name"), (String) rune.get("description"), statsl));
            });
        }
        {
            JSONObject names = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("language.json")));
            ((JSONObject) names.get("data")).entrySet().forEach((e) -> {
                nameMap.put((String) ((Map.Entry) e).getKey(), (String) ((Map.Entry) e).getValue());
            });
        }

        api = new ApiHandler(apiKey);
        apiCache = new ApiCache(api);

        Spark.setPort(port);
        Spark.staticFileLocation("/frontEnd/public_html");
        Spark.before((req, res) -> {
            if (!"Basic bWF0aWtrYTpxd2VydHk=".equals(req.headers("Authorization"))) {
                res.header("WWW-Authenticate", "Basic");
                Spark.halt(401);
            }
        });
        Spark.get("/", (req, res) -> {
            String summoner = req.queryParams("summoner");
            if (summoner != null) {
                res.header("location", summoner);
                Spark.halt(301);
            }
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("autoFind", false);
            attributes.put("realRoot", "");
            return new ModelAndView(attributes, "index.html");
        }, new FreeMarkerEngine());
        Spark.get("/:summoner", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String summoner = req.queryParams("summoner");
            if (summoner != null) {
                res.header("location", summoner);
                Spark.halt(301);
            }
            summoner = req.params("summoner");
            if (summoner != null) {
                attributes.put("autoFind", true);
                attributes.put("summoner", summoner);
            } else {
                attributes.put("autoFind", false);
            }
            attributes.put("realRoot", "");
            return new ModelAndView(attributes, "index.html");
        }, new FreeMarkerEngine());
        Spark.get("/:summoner/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String summoner = req.params("summoner");
            if (summoner != null) {
                attributes.put("autoFind", true);
                attributes.put("summoner", summoner);
            } else {
                attributes.put("autoFind", false);
            }
            attributes.put("realRoot", "../");
            return new ModelAndView(attributes, "index.html");
        }, new FreeMarkerEngine());
        Spark.get(":summoner/current", this::handleCurrentGame);
        Spark.get(":gameId/ranks", this::handleGameRanks);
        Spark.get(":gameId/:summonerId/stats", this::handleSummonerStats);
        Spark.exception(RitoException.class, (e, req, res) -> {
            res.status(503);
            res.body("Failed to get data from RIOT.");
            System.err.println(e);
        });
    }

    private String handleCurrentGame(Request req, Response res) throws IOException, RateLimitException, RequestException, RitoException {
        System.out.println("New request");
        Summoner s = api.getSummonerByName(Platform.EUNE, URLDecoder.decode(req.params("summoner"), "utf-8"));
        if (s == null) {
            res.status(404);
            return "The requested summoner was not found.";
        }
        CurrentGame cg = api.getCurrentGame(Platform.EUNE, s);
        if (cg == null) {
            res.status(404);
            return "No active games were found.";
        }
        JSONObject jo = new JSONObject();
        jo.put("gameId", cg.getGameId());
        jo.put("bigIcon", s.getProfileIconId() + ".png");
        jo.put("bigName", s.getName());
        jo.put("infoLine", mapMap.get(cg.getMapId())
                + ", "
                + gqciMap.get(cg.getGameQueueConfigId())
                + ", EUNE");

        JSONArray ja = new JSONArray();
        for (Participant p : cg.getParticipants()) {
            JSONObject po = new JSONObject();
            po.put("summonerId", p.getSummonerId());
            po.put("summonerName", p.getSummonerName());
            po.put("team", p.getTeamId());
            po.put("championImage", champMap.get(p.getChampionId()));
            po.put("spell1Image", spellMap.get(p.getSpell1Id()));
            po.put("spell2Image", spellMap.get(p.getSpell2Id()));
            int oc = 0, dc = 0, uc = 0;
            for (Mastery m : p.getMasteries()) {
                if (m.getMasteryId() < 4200) {
                    oc += m.getRank();
                } else if (m.getMasteryId() < 4300) {
                    dc += m.getRank();
                } else {
                    uc += m.getRank();
                }
            }
            JSONObject mo = new JSONObject();
            mo.put("offense", oc);
            mo.put("defense", dc);
            mo.put("utility", uc);
            po.put("masteries", mo);
            po.put("hilight", p.getSummonerId() == s.getId());

            ja.add(po);
        }

        jo.put("participants", ja);

        JSONArray banja = new JSONArray();
        for (BannedChampion bc : cg.getBannedChampions()) {
            JSONObject banjo = new JSONObject();
            banjo.put("team", bc.getTeamId());
            banjo.put("champion", champMap.get(bc.getChampionId()));
            banjo.put("turn", bc.getPickTurn());
            banja.add(banjo);
        }
        jo.put("bannedChampions", banja);

        jo.put("startTime", cg.getGameStartTime());

        res.type("application/json");

        apiCache.submitGame(cg);

        return jo.toJSONString();
    }

    private String handleGameRanks(Request req, Response res) throws IOException, RateLimitException, RequestException, RitoException {
        String gameId = req.params("gameId");
        long id;
        try {
            id = Long.valueOf(gameId);
        } catch (NumberFormatException e) {
            res.status(403);
            return "Invalid id.";
        }
        CurrentGame cg = apiCache.getGame(id);
        if (cg == null) {
            res.status(404);
            return "No such game.";
        }

        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for (Participant p : cg.getParticipants()) {
            JSONObject po = new JSONObject();
            po.put("summonerId", p.getSummonerId());

            List<League> leagues = apiCache.getLeagues(p.getSummonerId());
            if (leagues != null) {
                for (League l : leagues) {
                    if (l.getQueue().equals("RANKED_SOLO_5x5")) {
                        for (LeagueEntry le : l.getEntries()) {
                            if (le.getPlayerOrTeamId().equals(p.getSummonerId() + "")) {
                                po.put("tier", l.getTier());
                                po.put("division", le.getDivision());
                                po.put("lp", le.getLeaguePoints());
                                if (le.getMiniSeries() != null) {
                                    po.put("series", le.getMiniSeries().getProgress());
                                }
                                break;
                            }
                        }
                    }
                }
            }
            po.put("level", apiCache.getSummoner(p.getSummonerId()).getSummonerLevel());
            for (RunePage rp : apiCache.getRunePages(p.getSummonerId()).getRunePages()) {
                if (rp.isCurrent()) {
                    String pageName = rp.getName();
                    po.put("runePageName", (pageName.length() > 13
                            ? pageName.substring(0, Math.min(10, pageName.length())) + "..."
                            : pageName.substring(0, Math.min(13, pageName.length()))));
                    po.put("runePageNameFull", pageName);
                    Map<String, Double> stats = new HashMap<>();
                    for (RuneSlot r : rp.getSlots()) {
                        RuneInfo ri = runeMap.get((long) r.getRuneId());
                        for (RuneStat rs : ri.getStats()) {
                            Double d = stats.get(rs.getName());
                            if (d == null) {
                                d = 0.0;
                            }
                            stats.put(rs.getName(), d + rs.getValue());
                        }
                    }
                    JSONArray sa = new JSONArray();
                    for (Map.Entry<String, Double> e : stats.entrySet()) {
                        JSONObject so = new JSONObject();
                        String name = nameMap.get(e.getKey());
                        so.put("name", name != null ? name.replace("%", "").trim() : e.getKey());
                        so.put("value", (e.getKey().contains("Percent") ? (Math.round(e.getValue() * 1000.0) / 10.0) + "%" : Math.round(e.getValue() * 10.0) / 10.0));
                        sa.add(so);
                    }
                    po.put("runeStats", sa);
                    break;
                }
            }

            ja.add(po);
        }
        jo.put("participants", ja);

        res.type("application/json");

        return jo.toJSONString();
    }

    private String handleSummonerStats(Request req, Response res) throws IOException, RateLimitException, RequestException, RitoException {
        long id;
        try {
            id = Long.valueOf(req.params("gameId"));
        } catch (NumberFormatException e) {
            res.status(403);
            return "Invalid game id.";
        }
        CurrentGame cg = apiCache.getGame(id);
        if (cg == null) {
            res.status(404);
            return "No such game.";
        }
        try {
            id = Long.valueOf(req.params("summonerId"));
        } catch (NumberFormatException e) {
            res.status(403);
            return "Invalid summoner id.";
        }
        Participant p = null;
        for (Participant pa : cg.getParticipants()) {
            if (pa.getSummonerId() == id) {
                p = pa;
                break;
            }
        }
        if (p == null) {
            res.status(403);
            return "The summoner is not in the given game.";
        }
        RankedStats rs = apiCache.getRankedStats(id);
        if (rs == null) {
            res.status(404);
            return "No such summoner.";
        }
        List<PlayerStatsSummary> pssl = apiCache.getPlayerStats(id);

        JSONObject po = new JSONObject();

        boolean yolostats = false;
        for (PlayerStatsSummary pss : pssl) {
            if (pss.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                po.put("wins", pss.getWins());
                po.put("losses", pss.getLosses());
                yolostats = true;
                break;
            }
        }
        if (!yolostats) {
            po.put("wins", 0);
            po.put("losses", 0);
        }
        boolean found = false;
        for (ChampionStats cs : rs.getChampions()) {
            if (cs.getId() == p.getChampionId()) {
                JSONObject ro = new JSONObject();
                ro.put("averageKills", Math.round((double) cs.getTotalChampionKills() / (double) cs.getTotalSessionsPlayed() * 10.0) / 10.0 + "");
                ro.put("averageDeaths", Math.round((double) cs.getTotalDeathsPerSession() / (double) cs.getTotalSessionsPlayed() * 10.0) / 10.0 + "");
                ro.put("averageAssists", Math.round((double) cs.getTotalAssists() / (double) cs.getTotalSessionsPlayed() * 10.0) / 10.0 + "");
                ro.put("kda", cs.getTotalDeathsPerSession() == 0 ? "Perfect KDA" : (Math.round((cs.getTotalAssists() + cs.getTotalChampionKills()) / (double) cs.getTotalDeathsPerSession() * 10.0) / 10.0) + " KDA");
                ro.put("championWinRatio", (Math.round((cs.getTotalSessionsWon()) / (double) cs.getTotalSessionsPlayed() * 1000.0) / 10.0) + "%");
                ro.put("championWins", cs.getTotalSessionsWon());
                ro.put("championLosses", cs.getTotalSessionsLost());
                po.put("ranked", ro);
                found = true;
                break;
            }
        }
        if (!found) {
            JSONObject ro = new JSONObject();
            ro.put("averageKills", "0.0");
            ro.put("averageDeaths", "0.0");
            ro.put("averageAssists", "0.0");
            ro.put("kda", "0.0 KDA");
            ro.put("championWinRatio", "0.0%");
            ro.put("championWins", 0);
            ro.put("championLosses", 0);
            po.put("ranked", ro);
        }

        res.type("application/json");

        return po.toJSONString();
    }

}
