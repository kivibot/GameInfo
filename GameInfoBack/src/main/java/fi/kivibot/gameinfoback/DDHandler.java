/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.ApiHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.Realm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Spark;
import spark.SparkBase;

/**
 *
 * @author Nicklas
 */
public class DDHandler {

    private static String ddBase = "http://ddragon.leagueoflegends.com/cdn/";
    private static String allowed = "champion|profileicon|spell";
    private static String version = "5.7.1";

    public DDHandler(ApiHandler api) {
        File root = new File("dd_data/");
        root.mkdir();
        Spark.externalStaticFileLocation(root.getAbsolutePath());
        Spark.staticFileLocation("/frontEnd/public_html");
        HashSet<String> images = new HashSet<>();
        Spark.get("/img/:type/:img", (req, res) -> {
            if (allowed.contains(req.params("type"))) {
                String imgStr = req.params("type") + "/" + req.params("img");
                if (!images.contains(imgStr)) {
                    System.out.println(imgStr);
                    HttpURLConnection conn = (HttpURLConnection) new URL(ddBase + version + "/img/" + imgStr).openConnection();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        new File(root.getAbsolutePath() + "/" + version + "//img/" + req.params("type")).mkdirs();
                        OutputStream os = new FileOutputStream(root.getAbsolutePath() + "/" + version + "/img/" + imgStr);
                        for (int i = 0; i < conn.getContentLength(); i++) {
                            os.write(is.read());
                        }
                        os.flush();
                        os.close();
                    } else {
                        return null;
                    }
                    images.add(imgStr);
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

}
