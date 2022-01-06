package easv.app.dal.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import easv.app.Utils.Json.Adapters;
import easv.app.be.api.search.SearchModel;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OpenMovieNetwork
{
    private static OpenMovieNetwork instance;
    private OpenMovieDatabaseAPI omdbAPI;

    public OpenMovieNetwork()
    {
        new SearchModel(null, 0 ,null);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(SearchModel.class, Adapters.get(SearchModel.class.getName()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .build();

        omdbAPI = retrofit.create(OpenMovieDatabaseAPI.class);
    }

    public static synchronized OpenMovieNetwork getInstance()
    {
        if (instance == null)
        {
            instance = new OpenMovieNetwork();
        }
        return instance;
    }

    public Response<SearchModel> get(String title) throws IOException
    {
        return omdbAPI.getSearchInfo("f51b4727", title).execute();
    }
}
