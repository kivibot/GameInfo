package fi.kivibot.riotapi.constant;

/**
 *
 * @author Nicklas
 */
public enum Region {

    BR("BR", "BR1"),
    EUNE("EUNE", "EUN1"),
    EUW("EUW", "EUW1"),
    KR("KR", "KR"),
    LAN("LAN", "LA1"),
    LAS("LAS", "LA2"),
    NA("NA", "NA1"),
    OCE("OCE", "OC1"),
    TR("TR", "TR1"),
    RU("RU", "RU");

    private final String name;
    private final String platformId;

    private Region(String name, String platform) {
        this.name = name;
        this.platformId = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatformId() {
        return platformId;
    }
    
    public String getEndpoint(){
        return "";
    }

    public static Region getByNameOrPlatform(String str){
        for(Region rg : Region.values()){
            if(rg.getName().equals(str) 
                    || rg.getPlatformId().equals(str)){
                return rg;
            }
        }
        return null;
    }
    
}
