package easv.app.be;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResult {

    @JsonProperty("Search")
    public List<Movie> movies;

    @JsonProperty("totalResults")
    public String results;

    @JsonProperty("Response")
    public String response;

}
