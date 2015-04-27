package fi.kivibot.gameinfoback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Nicklas
 */
public class HTTPGetter {

    private final HttpURLConnection conn;

    public HTTPGetter(String url) throws IOException {
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.connect();
    }

    public int getResponseCode() throws IOException {
        return conn.getResponseCode();
    }

    public String getContent() throws IOException {
        if (conn.getContentLength() != 0) {
            return IOUtils.toString(conn.getInputStream(), "UTF-8");
        }
        return "";
    }

}
