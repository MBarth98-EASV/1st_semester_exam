package easv.app.dal.api;

import easv.app.be.SearchModel;
import easv.app.be.json.SearchDataJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMovieDatabaseAPI
{
    @GET("/")
    Call<SearchDataJson> getSearchInfo(@Query("apikey") String key, @Query("s") String title);

}
