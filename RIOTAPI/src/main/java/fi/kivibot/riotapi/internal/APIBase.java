package fi.kivibot.riotapi.internal;

import com.google.gson.GsonBuilder;
import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.RestClient;
import fi.kivibot.riotapi.rest.RestRequest;
import fi.kivibot.riotapi.rest.RestResult;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author Nicklas
 */
public class APIBase {

    protected final String apiKey;

    public APIBase(String apiKey) {
        this.apiKey = apiKey;
    }

    protected <T> T get(Class<T> type, RestClient client, RestRequest request) throws IOException, RateLimitException, RiotException {
        RestResult<T> result = client.execute(request, type);
        if (result.getResponseCode().isSuccess()) {
            return result.getData();
        } else {
            if (result.getResponseCode().getCode() == 429) {
                throw new RateLimitException("Rate limit exceeded");
            } else if (result.getResponseCode().isServerError()) {
                throw new RiotException("Error: " + result.getResponseCode().getCode());
            } else if (result.getResponseCode().isClientError()) {
                throw new IOException("Error: " + result.getResponseCode().getCode());
            }
            throw new RuntimeException("Unknown error! " + result.getResponseCode().getCode());
        }
    }

    protected Object get(Type type, RestClient client, RestRequest request) throws IOException, RateLimitException, RiotException {
        RestResult<String> result = client.execute(request);
        if (result.getResponseCode().isSuccess()) {
            return new GsonBuilder().create().fromJson(result.getData(), type);
        } else {
            if (result.getResponseCode().getCode() == 429) {
                throw new RateLimitException("Rate limit exceeded");
            } else if (result.getResponseCode().isServerError()) {
                throw new RiotException("Error: " + result.getResponseCode().getCode());
            } else if (result.getResponseCode().isClientError()) {
                throw new IOException("Error: " + result.getResponseCode().getCode());
            }
            throw new RuntimeException("Unknown error! " + result.getResponseCode().getCode());
        }
    }
}
