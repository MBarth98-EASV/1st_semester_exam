package easv.app.dal.api;

import easv.app.be.json.MovieInfo;
import easv.app.be.json.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMovieDatabaseAPI
{
    @GET("/")
    Call<SearchResult> getSearchInfo(@Query("apikey") String key, @Query("s") String title);

    @GET("/")
    Call<MovieInfo> get(@Query("apikey") String key, @Query("i") String imdbID, @Query("t") String title, @Query("plot") String resumeType);

    @GET("/")
    Call<SearchResult> search(@Query("apikey") String key, @Query("s") String title, @Query("type") String type, @Query("page") int page);

}
