package fi.kivibot.rest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class URLBuilder {

    private StringBuilder sb = new StringBuilder();

    public URLBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public URLBuilder appendEncoded(String str) {
        try {
            return append(URLEncoder.encode(str, "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public URLBuilder append(String format, Map<String, String> params) {
        return append(format, params.entrySet());
    }

    public URLBuilder append(String format, Set<Map.Entry<String, String>> params) {
        for (Map.Entry<String, String> e : params) {
            try {
                format = format.replace("{" + e.getKey() + "}", URLEncoder.encode(e.getValue().toString(), "utf-8"));
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return append(format);
    }

    public URL build() throws MalformedURLException {
        return new URL(sb.toString());
    }

}
