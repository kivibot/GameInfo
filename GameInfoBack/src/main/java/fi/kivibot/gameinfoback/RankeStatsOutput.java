package fi.kivibot.gameinfoback;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Nicklas
 */
public class RankeStatsOutput {

    @SerializedName("g")
    private int games;
    @SerializedName("w")
    private int wins;
    @SerializedName("l")
    private int losses;
    @SerializedName("k")
    private int kills;
    @SerializedName("d")
    private int deaths;
    @SerializedName("a")
    private int assists;

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    
}
