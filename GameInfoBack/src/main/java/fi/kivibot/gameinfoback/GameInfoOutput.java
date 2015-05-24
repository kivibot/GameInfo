package fi.kivibot.gameinfoback;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public class GameInfoOutput {

    private String bigIcon;
    private String bigName;
    private String infoLine;
    private Long startTime;
    private int teams;
    private List<List<Participant>> participants;
    private List<List<BannedChampionOutput>> bans;
    /**
     * some data might be missing or be old
     */
    private boolean riotProblem;
    private boolean heavyLoad;

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getBigName() {
        return bigName;
    }

    public void setBigName(String bigName) {
        this.bigName = bigName;
    }

    public String getInfoLine() {
        return infoLine;
    }

    public void setInfoLine(String infoLine) {
        this.infoLine = infoLine;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }

    public List<List<Participant>> getParticipants() {
        return participants;
    }

    public void setParticipants(List<List<Participant>> participants) {
        this.participants = participants;
    }

    public List<List<BannedChampionOutput>> getBans() {
        return bans;
    }

    public void setBans(List<List<BannedChampionOutput>> bans) {
        this.bans = bans;
    }

    public boolean isRiotProblem() {
        return riotProblem;
    }

    public void setRiotProblem(boolean riotProblem) {
        this.riotProblem = riotProblem;
    }

    public boolean isHeavyLoad() {
        return heavyLoad;
    }

    public void setHeavyLoad(boolean heavyLoad) {
        this.heavyLoad = heavyLoad;
    }
    

}
