package easv.app.dal.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import easv.app.utils.Json.*;
import easv.app.be.SearchModel;
import easv.app.be.json.MovieInfo;
import easv.app.be.json.SearchedMovieInfo;
import easv.app.be.json.SearchResult;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  <b>access layer for imdb data through a web API</b>
 *
 *  <p><br/><b>using</b>: retrofit2 <i> to create a new class at runtime based on the OpenMovieDatabaseAPI interface, using the settings provided in this class.</i></p>
 *
 *  <p><br/><b>using</b>: Gson (google json library) <i> to convert responses from retrofit into application specific entities.</i></p>
 *
 * @author mads-
 * */
public final class OpenMovieNetwork
{
    private static final String OMDB_BASE_URL = "http://www.omdbapi.com/";
    private static final String OMDB_API_KEY = "f51b4727";

    private static OpenMovieNetwork instance;
    private final OpenMovieDatabaseAPI WEB_API;

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

        WEB_API = retrofit.create(OpenMovieDatabaseAPI.class);
    }

    /**
     * class is a singleton and can not be instantiated outside this class - <b>the only access point for member functions</b>
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

    public SearchModel search(String title) throws IOException, NullPointerException
    {
        return SearchModel.fromSearchResult(search(title, TYPE.ALL, 1));
    }

    public SearchResult search(String title, int page) throws IOException, NullPointerException
    {
        return search(title, TYPE.ALL, page);
    }

    public SearchResult search(String title, TYPE type) throws IOException, NullPointerException
    {
        return search(title, type, 1);
    }

    public SearchResult search(String title, TYPE type, int page) throws IOException, NullPointerException
    {
        Objects.requireNonNull(title, "title MUST not be null and SHOULD not be empty");
        Objects.requireNonNull(type, "type MUST not be null - specify OpenMovieNetwork.TYPE.ALL instead");

        // omdb api specifies that the valid page range is between 0 and 100 exclusive (0 and 100 are invalid)
        if (page <= 0 || page >= 100)
        {
            throw new IllegalArgumentException("invalid page range MUST be 1 ... 99: was [%s]".formatted(page));
        }

        String search_type = switch(type)
        {
            case MOVIE -> "movie";
            case SERIES -> "series";
            case EPISODE -> "episode";
            default -> "";
        };

        return WEB_API.search(OMDB_API_KEY, title, search_type, page).execute().body();
    }


    /**
     * get full movie info on a single entity
     *
     * @param id the id created by IMDB, which is also what we store in our database (used for internal/external referencing)
     * @param title the partial or full title of the target entity
     * @param longPlot specify the length of the plot description returned from the omdb api call
     *
     * @throws IllegalArgumentException if both id and title is null, empty or only white spaces.
     * @throws IOException caused if the call to the web api failed.
     *
     * */
    public MovieInfo get(String id, String title, boolean longPlot) throws IOException, IllegalArgumentException
    {
        if (id != null && !id.isBlank())
        {
            title = null;
        }
        else
        {
            if (title == null || title.isBlank())
            {
                throw new IllegalArgumentException("either id or title MUST be set");
            }
        }

        return WEB_API.movie(OMDB_API_KEY, id, title, longPlot ? "short" : "full").execute().body();
    }

    public List<MovieInfo> get(List<String> ids) throws IOException
    {
        List<MovieInfo> movieInfoList = new ArrayList<>();

        for (String id : ids)
        {
            movieInfoList.add(get(id, null, false));
        }

        return movieInfoList;
    }
}
