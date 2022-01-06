package easv.app.dal.api;

import easv.app.be.api.search.SearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMovieDatabaseAPI
{
    @GET("/")
    Call<SearchModel> getSearchInfo(@Query("apikey") String key, @Query("s") String title);

}
