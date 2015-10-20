package fi.kivibot.riotapi.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Nicklas
 */
public class RestClient {

    private final String baseURL;

    public RestClient(String baseURL) {
        this.baseURL = baseURL;
    }

    public RestResult<String> execute(RestRequest request) throws IOException {
        URLBuilder builder = new URLBuilder();
        builder.append(baseURL);
        if (!baseURL.endsWith("/") || !request.getPath().startsWith("/")) {
            builder.append("/");
        }
        builder.append(request.getPath(), request.getSegments());
        boolean first = true;
        for (Map.Entry<String, String> e : request.getParams()) {
            builder.append(first ? "?" : "&");
            builder.appendEncoded(e.getKey()).append("=").appendEncoded(e.getValue());
        }
        HttpURLConnection conn = (HttpURLConnection) builder.build().openConnection();
        conn.setRequestMethod(request.getMethod().name());
        try {
            conn.connect();
            ResponseCode code = ResponseCode.parseInteger(conn.getResponseCode());
            String body = null;
            if (code.isSuccess()) {
                body = new Scanner(conn.getInputStream()).nextLine();
            }
            return new RestResult<>(body, code);
        } finally {
            conn.getInputStream().close();
        }
    }

    public <T> RestResult<T> execute(RestRequest request, Class<T> type) throws IOException {
        RestResult<String> result = execute(request);
        Gson gson = new GsonBuilder().create();
        RestResult<T> ret = new RestResult<>(gson.fromJson(result.getData(), type), result.getResponseCode());
        return ret;
    }

}
