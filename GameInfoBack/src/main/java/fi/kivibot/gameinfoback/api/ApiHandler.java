package fi.kivibot.gameinfoback.api;

import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.structures.MiniSeries;
import fi.kivibot.gameinfoback.api.structures.currentgame.Participant;
import fi.kivibot.gameinfoback.api.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.structures.Rune;
import fi.kivibot.gameinfoback.api.structures.Mastery;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.BannedChampion;
import fi.kivibot.gameinfoback.api.structures.ChampionStats;
import fi.kivibot.gameinfoback.api.structures.currentgame.CurrentGame;
import fi.kivibot.gameinfoback.api.structures.League;
import fi.kivibot.gameinfoback.api.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.structures.RankedStats;
import fi.kivibot.gameinfoback.api.structures.Realm;
import fi.kivibot.gameinfoback.api.structures.RunePage;
import fi.kivibot.gameinfoback.api.structures.RunePages;
import fi.kivibot.gameinfoback.api.structures.RuneSlot;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Nicklas
 */
public class ApiHandler {

    private class ApiResponse {

        private final int code;
        private final String body;

        public ApiResponse(int code, String body) {
            this.code = code;
            this.body = body;
        }

        public int getCode() {
            return code;
        }

        public String getBody() {
            return body;
        }
    }

    private String apiKey;

    public ApiHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    public Summoner getSummonerByName(Platform p, String name)
            throws IOException, RateLimitException, RequestException, RitoException {
        List<String> l = new ArrayList<>();
        l.add(name);
        return getSummonersByNames(p, l).get(name.replace(" ", "").toLowerCase());
    }

    public Map<String, Summoner> getSummonersByNames(Platform p, List<String> names)
            throws IOException, RateLimitException, RequestException, RitoException {

        String path = "v1.4/summoner/by-name/";
        for (Iterator<String> i = names.iterator(); i.hasNext();) {
            path += URLEncoder.encode(i.next().replace(" ", ""), "utf-8");
            if (i.hasNext()) {
                path += ",";
            }
        }
        ApiResponse resp = get(formApiUrl(p, path));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return new HashMap<>();
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);

        Map<String, Summoner> m = new HashMap<>();

        jo.forEach((name, d) -> {
            JSONObject data = (JSONObject) d;
            m.put((String) name, new Summoner(
                    (long) (Long) data.get("id"),
                    (String) data.get("name"),
                    (int) (long) (Long) data.get("profileIconId"),
                    (int) (long) (Long) data.get("revisionDate"),
                    (int) (long) (Long) data.get("summonerLevel")));
        });
        return m;
    }

    public CurrentGame getCurrentGame(Platform p, Summoner s)
            throws IOException, RateLimitException, RequestException, RitoException {

        ApiResponse resp = get(formObserverUrl(p, "getSpectatorGameInfo/{platformId}/" + s.getId()));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 403:
                throw new RequestException(resp.code);
            case 404:
                return null;
            case 429:
                throw new RateLimitException();
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);

        List<BannedChampion> bannedChampions = new ArrayList<>();
        ((JSONArray) jo.get("bannedChampions")).forEach((d) -> {
            JSONObject data = (JSONObject) d;
            bannedChampions.add(new BannedChampion(
                    (long) (Long) data.get("championId"),
                    (int) (long) (Long) data.get("pickTurn"),
                    (long) (Long) data.get("teamId")));
        });

        List<Participant> participants = new ArrayList<>();
        ((JSONArray) jo.get("participants")).forEach((d) -> {
            JSONObject data = (JSONObject) d;
            List<Mastery> masteries = new ArrayList<>();
            ((JSONArray) data.get("masteries")).forEach((md) -> {
                JSONObject mdata = (JSONObject) md;
                masteries.add(new Mastery(
                        (long) (Long) mdata.get("masteryId"),
                        (int) (long) (Long) mdata.get("rank")));
            });
            List<Rune> runes = new ArrayList<>();
            ((JSONArray) data.get("runes")).forEach((rd) -> {
                JSONObject rdata = (JSONObject) rd;
                runes.add(new Rune(
                        (int) (long) (Long) rdata.get("count"),
                        (long) (Long) rdata.get("runeId")));
            });
            participants.add(new Participant(
                    (boolean) (Boolean) data.get("bot"),
                    (long) (Long) data.get("championId"),
                    masteries,
                    (long) (Long) data.get("profileIconId"),
                    runes,
                    (long) (Long) data.get("spell1Id"),
                    (long) (Long) data.get("spell2Id"),
                    (long) (Long) data.get("summonerId"),
                    (String) data.get("summonerName"),
                    (long) (Long) data.get("teamId")));
        });

        return new CurrentGame(
                bannedChampions,
                (long) (Long) jo.get("gameId"),
                (long) (Long) jo.get("gameLength"),
                (String) jo.get("gameMode"),
                (long) (Long) jo.get("gameQueueConfigId"),
                (long) (Long) jo.get("gameStartTime"),
                (String) jo.get("gameType"),
                (long) (Long) jo.get("mapId"),
                (String) ((JSONObject) jo.get("observers")).get("encryptionKey"),
                participants,
                (String) jo.get("platformId"));
    }

    public Map<String, List<League>> getLeaguesEntry(Platform p, List<Long> ids)
            throws IOException, RateLimitException, RequestException, RitoException {

        String path = "v2.5/league/by-summoner/";
        for (Iterator<Long> i = ids.iterator(); i.hasNext();) {
            path += i.next();
            if (i.hasNext()) {
                path += ",";
            }
        }

        ApiResponse resp = get(formApiUrl(p, path + "/entry"));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return new HashMap<>();
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        Map<String, List<League>> lm = new HashMap();

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);

        jo.entrySet().forEach((e) -> {
            List<League> leagues = new ArrayList<>();
            JSONArray la = (JSONArray) ((Map.Entry) e).getValue();
            lm.put((String) ((Map.Entry) e).getKey(), leagues);
            la.forEach(lea -> {
                List<LeagueEntry> entries = new ArrayList<>();
                ((JSONArray) ((JSONObject) lea).get("entries")).forEach(lae -> {
                    JSONObject data = (JSONObject) lae;
                    LeagueEntry le = new LeagueEntry(
                            (String) data.get("division"),
                            (Boolean) data.get("isFreshBlood"),
                            (Boolean) data.get("isHotStreak"),
                            (Boolean) data.get("isInactive"),
                            (Boolean) data.get("isVeteran"),
                            (int) (long) (Long) data.get("leaguePoints"),
                            (int) (long) (Long) data.get("losses"),
                            data.containsKey("miniSeries") ? new MiniSeries(
                                            (int) (long) (Long) ((JSONObject) data.get("miniSeries")).get("losses"),
                                            (String) ((JSONObject) data.get("miniSeries")).get("progress"),
                                            (int) (long) (Long) ((JSONObject) data.get("miniSeries")).get("target"),
                                            (int) (long) (Long) ((JSONObject) data.get("miniSeries")).get("wins")) : null,
                            (String) data.get("playerOrTeamId"),
                            (String) data.get("playerOrTeamName"),
                            (int) (long) (Long) data.get("wins")
                    );
                    entries.add(le);
                });
                leagues.add(new League(
                        entries,
                        (String) ((JSONObject) lea).get("name"),
                        (String) ((JSONObject) lea).get("participantId"),
                        (String) ((JSONObject) lea).get("queue"),
                        (String) ((JSONObject) lea).get("tier")));
            });
        });

        return lm;
    }

    public RankedStats getRankedStats(Platform p, long id)
            throws IOException, RateLimitException, RequestException, RitoException {

        ApiResponse resp = get(formApiUrl(p, "v1.3/stats/by-summoner/" + id + "/ranked"));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return null;
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);

        List<ChampionStats> champions = new ArrayList<>();

        ((JSONArray) jo.get("champions")).forEach((e) -> {
            JSONObject ejo = (JSONObject) e;
            JSONObject sjo = ((JSONObject) ejo.get("stats"));
            champions.add(new ChampionStats(
                    (int) (long) (Long) ejo.get("id"),
                    (int) (long) (Long) sjo.get("totalSessionsPlayed"),
                    (int) (long) (Long) sjo.get("totalSessionsWon"),
                    (int) (long) (Long) sjo.get("totalSessionsLost"),
                    (int) (long) (Long) sjo.get("totalChampionKills"),
                    (int) (long) (Long) sjo.get("totalDeathsPerSession"),
                    (int) (long) (Long) sjo.get("totalAssists")));
        });

        return new RankedStats(champions, (Long) jo.get("modifyDate"), (Long) jo.get("summonerId"));
    }

    public Map<String, RunePages> getRunes(Platform p, List<Long> ids)
            throws IOException, RateLimitException, RequestException, RitoException {

        String path = "v1.4/summoner/";
        for (Iterator<Long> i = ids.iterator(); i.hasNext();) {
            path += i.next();
            if (i.hasNext()) {
                path += ",";
            }
        }

        ApiResponse resp = get(formApiUrl(p, path + "/runes"));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return new HashMap<>();
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);
        Map<String, RunePages> lm = new HashMap<>();
        for (Map.Entry<String, JSONObject> e : ((Map<String, JSONObject>) jo).entrySet()) {
            Set<RunePage> pages = new HashSet<>();
            for (JSONObject rpo : (List<JSONObject>) e.getValue().get("pages")) {
                Set<RuneSlot> slots = new HashSet<>();
                if (rpo.containsKey("slots")) {
                    for (JSONObject rso : (List<JSONObject>) rpo.get("slots")) {
                        slots.add(new RuneSlot((int) (long) (Long) rso.get("runeId"),
                                (int) (long) (Long) rso.get("runeSlotId")));
                    }
                }
                pages.add(new RunePage((Boolean) rpo.get("current"),
                        (Long) rpo.get("id"),
                        (String) rpo.get("name"),
                        slots));
            }
            lm.put(e.getKey(), new RunePages(pages, (Long) e.getValue().get("summonerId")));
        }

        return lm;
    }

    public List<PlayerStatsSummary> getStatsSummary(Platform p, long id) throws IOException, RequestException, RateLimitException, RitoException {

        ApiResponse resp = get(formApiUrl(p, "v1.3/stats/by-summoner/" + id + "/summary"));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return new ArrayList<>();
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);
        JSONArray ja = (JSONArray) jo.get("playerStatSummaries");
        List<PlayerStatsSummary> sl = new ArrayList<>();
        ja.forEach((so) -> {
            JSONObject data = (JSONObject) so;
            sl.add(new PlayerStatsSummary(data.containsKey("losses") ? (int) (long) (Long) data.get("losses") : 0,
                    (Long) data.get("modifyDate"),
                    (String) data.get("playerStatSummaryType"),
                    (int) (long) (Long) data.get("wins")));
        });
        return sl;
    }

    public Realm getRealm(Platform p) throws RitoException, RateLimitException, RequestException, IOException {
        ApiResponse resp = get(formStaticDataUrl(p, "v1.2/realm"));

        //Error handling
        switch (resp.code) {
            case 200:
                break;
            case 400:
            case 401:
                throw new RequestException(resp.code);
            case 404:
                return null;
            case 429:
                throw new RateLimitException();
            case 500:
            case 503:
            default:
                throw new RitoException(resp.code);
        }

        JSONObject jo = (JSONObject) JSONValue.parse(resp.body);
        Map<String, String> n = (Map<String, String>) jo.get("n");

        return new Realm((String) jo.get("cdn"),
                (String) jo.get("css"),
                (String) jo.get("dd"),
                (String) jo.get("l"),
                (String) jo.get("lg"),
                n,
                (int) (long) (Long) jo.get("profileiconmax"),
                (String) jo.get("store"),
                (String) jo.get("v"));
    }

    private String formApiUrl(Platform p, String path) {
        return "https://" + p.getRegion() + ".api.pvp.net/api/lol/" + p.getRegion() + "/" + path + "?api_key=" + apiKey;
    }

    private String formStaticDataUrl(Platform p, String path) {
        return "https://" + p.getRegion() + ".api.pvp.net/api/lol/static-data/" + p.getRegion() + "/" + path + "?api_key=" + apiKey;
    }

    private String formObserverUrl(Platform p, String path) {
        return "https://" + p.getRegion() + ".api.pvp.net/observer-mode/rest/consumer/" + path.replace("{platformId}", p.getId()) + "?api_key=" + apiKey;
    }

    private boolean apiOK = true;

    private ApiResponse get(String url) throws MalformedURLException, IOException {
        boolean apiChecker = false;
        System.out.print(".");
        for (int i = 0; i < 100; i++) {
//            while (!apiChecker && !apiOK) {
//                try {
//                    System.out.println("a");
//                    Thread.sleep(333);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ApiHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            if (!apiChecker && !apiOK) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ApiHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.connect();
            String body = "";
            if (conn.getResponseCode() == 429) {
                synchronized (this) {
                    if (!apiOK && !apiChecker) {
                        continue;
                    } else if (apiOK) {
                        apiOK = false;
                        apiChecker = true;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApiHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            } else {
                synchronized (this) {
                    if (!apiOK) {
                        apiOK = true;
                        this.notifyAll();
                    }
                }
            }
            //Ugly hack
            if (conn.getContentLength() != 0 && (conn.getResponseCode() + "").startsWith("2")) {
                body = IOUtils.toString(conn.getInputStream(), "UTF-8");
            }
            return new ApiResponse(conn.getResponseCode(), body);
        }
        return new ApiResponse(429, "");
    }

}
