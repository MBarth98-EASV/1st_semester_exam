package easv.app.dal.api;

import easv.app.be.json.MovieInfo;
import easv.app.be.json.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Open Movie Database API endpoint definitions
 *
 * @author mads-
 * */
public interface OpenMovieDatabaseAPI
{
    /**
     *      Open Movie Database: <b>movie entity api</b>
     *
     *      <p> http GET request at the root directory with query parameters ( omdbapi.com/?apikey=key&i=imdbID ) </p>
     *
     * @param key omdb api key obtained from <url>http://www.omdbapi.com/apikey.aspx</url>.
     * @param imdbID entity identifier in the format ('tt1285016'). if the key is valid it will return the unique entity.
     * @param title if this is specified, and imdbID is null then it will return the closest matching entity.
     * @param resumeType valid input is short/full, specify the size of the plot text returned.
     * */
    @GET("/")
    Call<MovieInfo> movie(@Query("apikey") String key, @Query("i") String imdbID, @Query("t") String title, @Query("plot") String resumeType);

    /**
     *      Open Movie Database: search api
     *
     *      <p> http GET request at the root directory with query parameters ( omdbapi.com/?apikey=key&s=title ) </p>
     *
     * @param key omdb api key obtained from <url>http://www.omdbapi.com/apikey.aspx</url>.
     * @param title full or partial title will return all entities that match.
     * @param type if not empty and is one of the following ('movie/series/episode') it will only return entities of that type.
     * @param page every page only contains 10 entities - use this to iterate over multiple sets. (MUST equal 1 ... 99)
     * */
    @GET("/")
    Call<SearchResult> search(@Query("apikey") String key, @Query("s") String title, @Query("type") String type, @Query("page") int page);
}
