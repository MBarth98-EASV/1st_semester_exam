package easv.app.be.json;

import com.google.gson.annotations.SerializedName;
import easv.app.be.Movie;

import java.util.List;

public class SearchDataJson
{
    @SerializedName("Search")
    public List<MovieDataJson> movies;

    @SerializedName("totalResults")
    public String results;

    @SerializedName("Response")
    public String response;
}
