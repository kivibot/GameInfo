package fi.kivibot.riotapi.internal;

import fi.kivibot.riotapi.exception.RateLimitException;
import fi.kivibot.riotapi.exception.RiotException;
import fi.kivibot.riotapi.rest.QueryBuilder;
import fi.kivibot.riotapi.rest.RestClient;
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
    
    protected <T> T get(Class<T> type, QueryBuilder query) throws IOException, RateLimitException, RiotException {
        RestResult<T> result = new RestClient().getJSON(query, type);
        if (result.getResponseCode().isSuccess()) {
            return result.getValue();
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
    
    protected Object get(Type type, QueryBuilder query) throws IOException, RateLimitException, RiotException {
        RestResult result = new RestClient().getJSON(query, type);
        if (result.getResponseCode().isSuccess()) {
            return result.getValue();
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
