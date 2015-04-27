/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.old.ApiHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.old.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.old.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.old.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.old.structures.Realm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import spark.Spark;
import spark.SparkBase;

/**
 *
 * @author Nicklas
 */
public class DDHandler {

    private static String ddBase = "http://ddragon.leagueoflegends.com/cdn/";
    private static String allowed = "champion|profileicon|spell";
    private static String version = "";

    public DDHandler(ApiHandler api) {
        {
            JSONObject maps = (JSONObject) JSONValue.parse(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("map.json")));
            maps.entrySet().forEach((e) -> {
                mapMap.put((Long) ((Map.Entry) e).getValue(), (String) ((Map.Entry) e).getKey());
            });
        }

        HashSet<String> images = new HashSet<>();

        File root = new File("dd_data/");
        root.mkdir();
        for (File v : root.listFiles()) {
            for (File t : v.listFiles()) {
                for (File t2 : t.listFiles()) {
                    for (File n : t2.listFiles()) {
                        System.out.println("found: " + v.getName() + "/" + t.getName() + "/" + t2.getName() + "/" + n.getName());
                        images.add(v.getName() + "/" + t.getName() + "/" + t2.getName() + "/" + n.getName());
                    }
                }
            }
        }
        Spark.externalStaticFileLocation(root.getAbsolutePath());
        Spark.staticFileLocation("/frontEnd/public_html");
        Spark.get("/:version/img/:type/:img", (req, res) -> {
            if (!req.params("version").equals(version)) {
                res.header("location", "../../../img/" + req.params("type") + "/" + req.params("img"));
                Spark.halt(301);
            }
            return null;
        });
        Spark.get("/img/:type/:img", (req, res) -> {
            System.out.println(req.params("type"));
            if (allowed.contains(req.params("type"))) {
                String imgStr = req.params("type") + "/" + req.params("img");
                if (!images.contains(version + "/img/" + imgStr)) {
                    System.out.println(version + "/img/" + imgStr);
                    HttpURLConnection conn = (HttpURLConnection) new URL(ddBase + version + "/img/" + imgStr).openConnection();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        new File(root.getAbsolutePath() + "/" + version + "/img/" + req.params("type")).mkdirs();
                        OutputStream os = new FileOutputStream(root.getAbsolutePath() + "/" + version + "/img/" + imgStr);
                        for (int i = 0; i < conn.getContentLength(); i++) {
                            os.write(is.read());
                        }
                        os.flush();
                        os.close();
                    } else {
                        return null;
                    }
                    images.add(version + "/img/" + imgStr);
                }
                res.header("location", "../../" + version + "/img/" + imgStr);
                Spark.halt(301);
            }
            return null;
        });
        new Thread(() -> {
            while (true) {
                //check version
                System.out.println("Checking dd version");
                try {
                    Realm r = api.getRealm(Platform.EUNE);
                    if (!version.equals(r.getDd())) {
                        version = r.getDd();
                        images.clear();
                        System.out.println("New dd version found!");

                        //update champ map
                        {
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://ddragon.leagueoflegends.com/cdn/" + version + "/data/en_US/champion.json").openConnection();
                            if (conn.getResponseCode() == 200) {
                                System.out.println("Updating champ map");
//                                champMap.clear();
                                JSONObject champs = (JSONObject) JSONValue.parse(
                                        new InputStreamReader(conn.getInputStream()));
                                ((JSONObject) champs.get("data")).entrySet().forEach((e) -> {
                                    JSONObject champ = (JSONObject) ((Map.Entry) e).getValue();
                                    champMap.put(Long.valueOf((String) champ.get("key")), (String) ((JSONObject) champ.get("image")).get("full"));
                                    
                                });
                            }
                        }
                        //update spell map
                        {
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://ddragon.leagueoflegends.com/cdn/" + version + "/data/en_US/summoner.json").openConnection();
                            if (conn.getResponseCode() == 200) {
                                System.out.println("Updating spell map");
//                                champMap.clear();
                                JSONObject spells = (JSONObject) JSONValue.parse(
                                        new InputStreamReader(conn.getInputStream()));
                                ((JSONObject) spells.get("data")).entrySet().forEach((e) -> {
                                    JSONObject spell = (JSONObject) ((Map.Entry) e).getValue();
                                    spellMap.put(Long.valueOf((String) spell.get("key")), (String) ((JSONObject) spell.get("image")).get("full"));
                                });
                            }
                        }
                    }
                } catch (RitoException | RateLimitException | RequestException | IOException ex) {
                    Logger.getLogger(DDHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(TimeUnit.HOURS.toMillis(1));
                } catch (InterruptedException ex) {
                    Logger.getLogger(DDHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private Map<Long, String> champMap = new HashMap<>();
    private Map<Long, String> spellMap = new HashMap<>();
    private Map<Long, String> mapMap = new HashMap<>();
    private Map<Long, String> gqciMap = new HashMap<>();

    public Map<Long, String> getChampMap() {
        return champMap;
    }

    public Map<Long, String> getSpellMap() {
        return spellMap;
    }

    public Map<Long, String> getMapMap() {
        return mapMap;
    }

    public Map<Long, String> getGqciMap() {
        return gqciMap;
    }

}
