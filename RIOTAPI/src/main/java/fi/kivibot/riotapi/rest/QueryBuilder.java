package fi.kivibot.riotapi.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class QueryBuilder {

    private StringBuilder sb = new StringBuilder();

    public QueryBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public QueryBuilder appendEncoded(String str) {
        try {
            return append(URLEncoder.encode(str, "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @see java.lang.Formatter
     */
    public QueryBuilder append(String format, String... params) {
        for (int i = 0; i < params.length; i++) {
            try {
                format = format.replace("{" + i + "}", URLEncoder.encode(params[i], "utf-8"));
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return append(format);
    }

    public QueryBuilder append(String format, Map<String, Object> params) {
        for (Map.Entry<String, Object> e : params.entrySet()) {
            try {
                format = format.replace("{" + e.getKey() + "}", URLEncoder.encode(e.getValue().toString(), "utf-8"));
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return append(format);
    }

    public String build() {
        return sb.toString();
    }

}
