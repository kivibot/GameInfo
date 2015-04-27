package fi.kivibot.gameinfoback.api.old;

/**
 *
 * @author Nicklas
 */
public enum Platform {

    BR("br", "BR1"),
    EUNE("eune", "EUN1"),
    EUW("euw", "EUW1"),
    KR("kr", "KT"),
    LAN("lan", "LA1"),
    LAS("las", "LA2"),
    NA("na", "NA1"),
    OCE("oce", "OC1"),
    TR("tr", "TR1"),
    RU("ru", "RU"),
    PBE("pbe", "PBE1");

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
    
    public static boolean isPlatform(String str){
        switch(str.toLowerCase()){
            case "br":
            case "eune":
            case "euw":
            case "kr":
            case "lan":
            case "las":
            case "na":
            case "oce":
            case "tr":
            case "ru":
            case "pbe":
                return true;
            default:
                return false;
        }
    }
    
    public static Platform getPlatform(String str){
        switch(str.toLowerCase()){
            case "br":
                return BR;
            case "eune":
                return EUNE;
            case "euw":
                return EUW;
            case "kr":
                return KR;
            case "lan":
                return LAN;
            case "las":
                return LAS;
            case "na":
                return NA;
            case "oce":
                return OCE;
            case "tr":
                return TR;
            case "ru":
                return RU;
            case "pbe":
                return PBE;
            default:
                return null;
        }
    }

    public String getRegion() {
        return region;
    }

    public String getId() {
        return id;
    }

}
