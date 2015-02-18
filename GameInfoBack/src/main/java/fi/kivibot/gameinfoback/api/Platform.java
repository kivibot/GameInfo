package fi.kivibot.gameinfoback.api;

/**
 *
 * @author Nicklas
 */
public enum Platform {

    EUNE("eune", "EUN1"),
    EUW("euw", "EUW1");

    private final String region;
    private final String id;

    private Platform(String region, String id) {
        this.region = region;
        this.id = id;
    }

    public static Platform getEUNE() {
        return EUNE;
    }

    public static Platform getEUW() {
        return EUW;
    }

    public String getRegion() {
        return region;
    }

    public String getId() {
        return id;
    }

}
