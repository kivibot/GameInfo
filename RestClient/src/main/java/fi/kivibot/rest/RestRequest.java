package fi.kivibot.rest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Nicklas
 */
public class RestRequest {

    private final String path;
    private final Method method;

    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> segments = new HashMap<>();

    public RestRequest(String path) {
        this(path, Method.GET);
    }

    public RestRequest(String path, Method method) {
        this.path = path;
        this.method = method;
    }

    public void addParameter(String key, Object value) {
        params.put(key, value.toString());
    }

    public void addUrlSegment(String key, Object value) {
        segments.put(key, value.toString());
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public Set<Map.Entry<String, String>> getParams() {
        return Collections.unmodifiableSet(params.entrySet());
    }

    public Set<Map.Entry<String, String>> getSegments() {
        return Collections.unmodifiableSet(segments.entrySet());
    }
}
