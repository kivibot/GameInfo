package fi.kivibot.gameinfo.web;

import spark.Spark;
import spark.SparkBase;

/**
 *
 * @author Nicklas
 */
public class Main {

    public static void main(String[] args) {
        Spark.staticFileLocation("/web");
        Spark.get("/404", (req, res) -> null);
    }

}
