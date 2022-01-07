package easv.app.be.json;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 *
 * @author mads-
 * */
public class SearchResult
{
    @SerializedName("Search")
    public SearchedMovieInfo[] movies;

    @SerializedName("totalResults")
    public String results;

    @SerializedName("Response")
    public String response;
}
