
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.internal.ChampionAPI;
import fi.kivibot.riotapi.internal.StatusAPI;
import java.io.IOException;

/**
 *
 * @author Nicklas
 */
public class main {

    public static void main(String[] args) throws IOException, RateLimitException, RiotException {

        StatusAPI api = new StatusAPI();

        System.out.println(api.getShards());

    }

}
