
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.internal.ChampionAPI;
import fi.kivibot.riotapi.internal.StatusAPI;
import fi.kivibot.riotapi.internal.SummonerAPI;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Nicklas
 */
public class main {

    public static void main(String[] args) throws IOException, RateLimitException, RiotException {

        SummonerAPI api = new SummonerAPI("cd0ba87c-8a4a-4983-ac29-d1ce5cb3f302");

        ArrayList<String> names = new ArrayList<>();
        names.add("kivibot");
        
        System.out.println(api.getSummonerByName(Region.EUNE, names));

    }

}
