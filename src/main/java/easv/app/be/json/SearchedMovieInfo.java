package easv.app.be.json;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author mads-
 * */
public class SearchedMovieInfo
{
    @SerializedName("imdbID")
    public String ID;

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public String year;

    @SerializedName("Type")
    public String type;

    @SerializedName("Poster")
    public String imageURL;

    @Override
    public String toString()
    {
        return "SearchMovieData{" +
                "ID=" + ID +
                ", title=" + title +
                ", year=" + year +
                ", type=" + type +
                ", imageURL=" + imageURL +
                '}';
    }
}
