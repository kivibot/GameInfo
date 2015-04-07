package fi.kivibot.gameinfoback.api.structures;

import java.util.Map;

/**
 *
 * @author Nicklas
 */
public class Realm {

    private final String cdn, css, dd, k, lg;
    private final Map<String, String> n;
    private final int profileiconmax;
    private final String store, v;

    public Realm(String cdn, String css, String dd, String k, String lg, Map<String, String> n, int profileiconmax, String store, String v) {
        this.cdn = cdn;
        this.css = css;
        this.dd = dd;
        this.k = k;
        this.lg = lg;
        this.n = n;
        this.profileiconmax = profileiconmax;
        this.store = store;
        this.v = v;
    }

    public String getCdn() {
        return cdn;
    }

    public String getCss() {
        return css;
    }

    public String getDd() {
        return dd;
    }

    public String getK() {
        return k;
    }

    public String getLg() {
        return lg;
    }

    public Map<String, String> getN() {
        return n;
    }

    public int getProfileiconmax() {
        return profileiconmax;
    }

    public String getStore() {
        return store;
    }

    public String getV() {
        return v;
    }

    @Override
    public String toString() {
        return "Realm{" + "cdn=" + cdn + ", css=" + css + ", dd=" + dd + ", k=" + k + ", lg=" + lg + ", n=" + n + ", profileiconmax=" + profileiconmax + ", store=" + store + ", v=" + v + '}';
    }

}
