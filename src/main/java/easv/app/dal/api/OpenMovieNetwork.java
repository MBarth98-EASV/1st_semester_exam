package easv.app.dal.api;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import easv.app.Utils.Json.*;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.be.json.SearchDataJson;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OpenMovieNetwork
{
    private static OpenMovieNetwork instance;
    private OpenMovieDatabaseAPI omdbAPI;

    private OpenMovieNetwork()
    {
        Gson gson = JsonParserFactory.create(
                SearchDataJson.class,
                AnonymousGenericClass.create(ArrayList.class, MovieModel.class).getClass()
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        omdbAPI = retrofit.create(OpenMovieDatabaseAPI.class);
    }

    public static OpenMovieNetwork getInstance()
    {
        if (instance == null)
        {
            instance = new OpenMovieNetwork();
        }
        return instance;
    }

    public Response<SearchDataJson> get(String title) throws IOException
    {
        return omdbAPI.getSearchInfo("f51b4727", title).execute();
    }
}
