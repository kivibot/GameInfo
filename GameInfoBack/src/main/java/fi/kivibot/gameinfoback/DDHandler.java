/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.ApiHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
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
    private static String allowed = "champion|profileicon";
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
                System.out.println(imgStr);
                if (!images.contains(imgStr)) {
                    HttpURLConnection conn = (HttpURLConnection) new URL(ddBase + version + "/img/" + imgStr).openConnection();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        new File(root.getAbsolutePath() + "/" + version + "/5.7.1/img/" + req.params("type")).mkdirs();
                        OutputStream os = new FileOutputStream(root.getAbsolutePath() + "/" + version + "/img/" + imgStr);
                        for (int i = 0; i < conn.getContentLength(); i++) {
                            os.write(is.read());
                        }
                        os.flush();
                        os.close();
                    } else {
                        return null;
                    }
                }
                res.redirect("../../" + version + "/img/" + imgStr);
            }
            return null;
        });
    }

}
