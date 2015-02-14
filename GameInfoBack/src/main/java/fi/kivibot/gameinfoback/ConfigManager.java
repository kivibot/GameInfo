package fi.kivibot.gameinfoback;

import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Laiska ratkaisu! :D
 *
 * @author Nicklas
 */
public class ConfigManager {

    private static final ConfigManager conf = new ConfigManager("config.json");

    public static ConfigManager getDefault() {
        return conf;
    }

    JSONObject jo;

    private ConfigManager(String path) {
        jo = (JSONObject) JSONValue.parse(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path)));
    }

    public long getLong(String name, long def) {
        Object o = jo.get(name);
        if (o != null && o instanceof Long) {
            return (Long) o;
        } else if (o != null && o instanceof String) {
            try {
                return Long.valueOf((String) o);
            } catch (NumberFormatException nfe) {
            }
        }
        return def;
    }

    public String getString(String name, String def) {
        Object o = jo.get(name);
        if (o != null && o instanceof String) {
            return (String) o;
        }
        return def;
    }

}
