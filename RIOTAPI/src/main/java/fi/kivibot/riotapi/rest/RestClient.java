package fi.kivibot.riotapi.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Nicklas
 */
public class RestClient {

    public <T> RestResult<T> getJSON(String url, Class<T> type) throws MalformedURLException, IOException {
        return getJSON(new URL(url), type);
    }

    public <T> RestResult<T> getJSON(URL url, Class<T> type) throws IOException {
        RestResult<String> result = get(url);
        Gson gson = new GsonBuilder().create();
        RestResult<T> ret = new RestResult<>(gson.fromJson(result.getValue(), type), result.getResponseCode());
        return ret;
    }

    public <T> RestResult<T> getJSON(QueryBuilder url, Class<T> type) throws MalformedURLException, IOException {
        return getJSON(url.build(), type);
    }

    public RestResult<String> get(String url) throws MalformedURLException, IOException {
        return get(new URL(url));
    }

    public RestResult<String> get(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        ResponseCode code = ResponseCode.parseInteger(conn.getResponseCode());
        String body = null;
        if (code.isSuccess()) {
            body = new Scanner(conn.getInputStream()).next();
        }
        return new RestResult<>(body, code);
    }

    public RestResult<String> get(QueryBuilder url) throws MalformedURLException, IOException {
        return get(url.build());
    }

}
