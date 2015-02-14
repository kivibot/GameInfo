/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.kivibot.gameinfoback;

import fi.kivibot.gameinfoback.api.ApiHandler;
import fi.kivibot.gameinfoback.api.exceptions.RateLimitException;
import fi.kivibot.gameinfoback.api.exceptions.RequestException;
import fi.kivibot.gameinfoback.api.exceptions.RitoException;
import fi.kivibot.gameinfoback.api.structures.Summoner;
import java.io.IOException;

/**
 *
 * @author Nicklas
 */
public class Main {

    public static void main(String[] args) throws IOException, RateLimitException, RequestException, RitoException {
        new GameInfoBackend((int) ConfigManager.getDefault().getLong("port", 8080),
                ConfigManager.getDefault().getString("apiKey", null)).start();
    }
}
