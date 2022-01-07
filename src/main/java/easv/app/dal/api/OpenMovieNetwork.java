package easv.app.dal.api;

import java.io.IOException;
import java.util.Objects;
import com.google.gson.Gson;
import easv.app.Utils.Json.*;
import easv.app.be.json.MovieInfo;
import easv.app.be.json.SearchedMovieInfo;
import easv.app.be.json.SearchResult;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  access layer for imdb data through a web API
 *
 *  using: retrofit2 that creates a new class at runtime (java reflection system) based on the OpenMovieDatabaseAPI interface, using the settings provided in this class.
 *  using: Gson (google json library) to convert responses from retrofit into application specific entities.
 *
 * @author mads-
 * */
public final class OpenMovieNetwork
{
    private static final String OMDB_BASE_URL = "http://www.omdbapi.com/";
    private static final String OMDB_API_KEY = "f51b4727";

    private static OpenMovieNetwork instance;
    private final OpenMovieDatabaseAPI omdbAPI;

    private OpenMovieNetwork()
    {
        Gson gson = JsonParserFactory.create(
                SearchResult.class,
                SearchedMovieInfo.class,
                MovieInfo.class
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        omdbAPI = retrofit.create(OpenMovieDatabaseAPI.class);
    }

    /**
     * class is a singleton and can not be instantiated outside this class - the only access point for member functions
     * */
    public static OpenMovieNetwork getInstance()
    {
        if (instance == null)
        {
            instance = new OpenMovieNetwork();
        }
        return instance;
    }

    public enum TYPE
    {
        ALL,
        MOVIE,
        SERIES,
        EPISODE
    }

    public SearchResult search(String title) throws IOException
    {
        return search(title, TYPE.ALL, 1);
    }

    public SearchResult search(String title, int page) throws IOException
    {
        return search(title, TYPE.ALL, page);
    }

    public SearchResult search(String title, TYPE type) throws IOException
    {
        return search(title, type, 1);
    }

    public SearchResult search(String title, TYPE type, int page) throws IOException
    {
        Objects.requireNonNull(title);
        Objects.requireNonNull(type, "type may not be null - specify TYPE.ALL instead");

        // omdb api specifies that the valid page range is between 1 and 99 inclusive (0 and 100 are invalid)
        if (page <= 0 || page >= 100)
        {
            throw new IllegalArgumentException("invalid page range must be 1 -> 99: was [%s]".formatted(page));
        }

        String search_type = switch(type)
        {
            case MOVIE -> "movie";
            case SERIES -> "series";
            case EPISODE -> "episode";
            default -> "";
        };

        return omdbAPI.search(OMDB_API_KEY, title, search_type, page).execute().body();
    }

    public MovieInfo get(String id, String title, boolean longPlot) throws IOException
    {
        return omdbAPI.get(OMDB_API_KEY, id, title, "short").execute().body();
    }
}
