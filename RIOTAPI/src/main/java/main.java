
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.internal.ChampionAPI;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nicklas
 */
public class main {

    public static void main(String[] args) throws IOException, RateLimitException, RiotException {

        ChampionAPI api = new ChampionAPI("cd0ba87c-8a4a-4983-ac29-d1ce5cb3f302");

        System.out.println(api.getAll(Region.EUNE, true));
        System.out.println(api.getById(Region.EUNE, 32));

        for (int i = 0; i < 10; i++) {
            System.out.println(api.getById(Region.EUNE, 32));
        }

    }

}
