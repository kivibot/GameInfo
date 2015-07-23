package fi.kivibot.riotapi.structures.league;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class LeagueEntryDto {

    private String division;
    @SerializedName("isFreshBlood")
    private boolean freshBlood;
    @SerializedName("isHotStreak")
    private boolean hotStreak;
    @SerializedName("isInactive")
    private boolean inactive;
    @SerializedName("isVeteran")
    private boolean veteran;
    private int leaguePoints;
    private int losses;
    private MiniSeriesDto miniSeries;
    private String playerOrTeamId;
    private String playerOrTeamName;
    private int wins;

    public LeagueEntryDto(String division, boolean freshBlood, boolean hotStreak, boolean inactive, boolean veteran, int leaguePoints, int losses, MiniSeriesDto miniSeries, String playerOrTeamId, String playerOrTeamName, int wins) {
        this.division = division;
        this.freshBlood = freshBlood;
        this.hotStreak = hotStreak;
        this.inactive = inactive;
        this.veteran = veteran;
        this.leaguePoints = leaguePoints;
        this.losses = losses;
        this.miniSeries = miniSeries;
        this.playerOrTeamId = playerOrTeamId;
        this.playerOrTeamName = playerOrTeamName;
        this.wins = wins;
    }

    public String getDivision() {
        return division;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public boolean isInactive() {
        return inactive;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getLosses() {
        return losses;
    }

    public MiniSeriesDto getMiniSeries() {
        return miniSeries;
    }

    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    public String getPlayerOrTeamName() {
        return playerOrTeamName;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.division);
        hash = 67 * hash + (this.freshBlood ? 1 : 0);
        hash = 67 * hash + (this.hotStreak ? 1 : 0);
        hash = 67 * hash + (this.inactive ? 1 : 0);
        hash = 67 * hash + (this.veteran ? 1 : 0);
        hash = 67 * hash + this.leaguePoints;
        hash = 67 * hash + this.losses;
        hash = 67 * hash + Objects.hashCode(this.miniSeries);
        hash = 67 * hash + Objects.hashCode(this.playerOrTeamId);
        hash = 67 * hash + Objects.hashCode(this.playerOrTeamName);
        hash = 67 * hash + this.wins;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LeagueEntryDto other = (LeagueEntryDto) obj;
        if (!Objects.equals(this.division, other.division)) {
            return false;
        }
        if (this.freshBlood != other.freshBlood) {
            return false;
        }
        if (this.hotStreak != other.hotStreak) {
            return false;
        }
        if (this.inactive != other.inactive) {
            return false;
        }
        if (this.veteran != other.veteran) {
            return false;
        }
        if (this.leaguePoints != other.leaguePoints) {
            return false;
        }
        if (this.losses != other.losses) {
            return false;
        }
        if (!Objects.equals(this.miniSeries, other.miniSeries)) {
            return false;
        }
        if (!Objects.equals(this.playerOrTeamId, other.playerOrTeamId)) {
            return false;
        }
        if (!Objects.equals(this.playerOrTeamName, other.playerOrTeamName)) {
            return false;
        }
        if (this.wins != other.wins) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LeagueEntryDto{" + "division=" + division + ", freshBlood=" + freshBlood + ", hotStreak=" + hotStreak + ", inactive=" + inactive + ", veteran=" + veteran + ", leaguePoints=" + leaguePoints + ", losses=" + losses + ", miniSeries=" + miniSeries + ", playerOrTeamId=" + playerOrTeamId + ", playerOrTeamName=" + playerOrTeamName + ", wins=" + wins + '}';
    }
}
