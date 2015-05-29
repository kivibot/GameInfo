package fi.kivibot.gameinfoback;

import com.google.gson.annotations.SerializedName;
import fi.kivibot.gameinfoback.api.struct.league.MiniSeries;

/**
 *
 * @author Nicklas
 */
public class Participant {

    @SerializedName("champImg")
    private String championImage;
    private String name;
    @SerializedName("s1img")
    private String spell1Image;
    @SerializedName("s2img")
    private String spell2Image;
    private Boolean highlight;
    private String rank;
    private int lp;
    private MiniSeries series;
    @SerializedName("rankImg")
    private String rankImage;    
    private RankeStatsOutput rankedStats;
    
    public String getChampionImage() {
        return championImage;
    }

    public void setChampionImage(String championImage) {
        this.championImage = championImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell1Image() {
        return spell1Image;
    }

    public void setSpell1Image(String spell1Image) {
        this.spell1Image = spell1Image;
    }

    public String getSpell2Image() {
        return spell2Image;
    }

    public void setSpell2Image(String spell2Image) {
        this.spell2Image = spell2Image;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLP() {
        return lp;
    }

    public void setLP(int lp) {
        this.lp = lp;
    }

    public MiniSeries getSeries() {
        return series;
    }

    public void setSeries(MiniSeries series) {
        this.series = series;
    }

    public String getRankImage() {
        return rankImage;
    }

    public void setRankImage(String rankImage) {
        this.rankImage = rankImage;
    }

    public RankeStatsOutput getRankedStats() {
        return rankedStats;
    }

    public void setRankedStats(RankeStatsOutput rankedStats) {
        this.rankedStats = rankedStats;
    }

}
