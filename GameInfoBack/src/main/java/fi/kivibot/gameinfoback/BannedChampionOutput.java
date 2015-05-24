package fi.kivibot.gameinfoback;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Nicklas
 */
public class BannedChampionOutput {

    @SerializedName("img")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
