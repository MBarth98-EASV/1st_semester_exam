package easv.app.bll;

import easv.app.utils.Converters;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.be.json.MovieInfo;
import easv.app.dal.api.OpenMovieNetwork;
import easv.app.be.DBMovieData;
import easv.app.dal.db.MovieDatabase;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataManager
{
    MovieDatabase database = null;
    private final ListProperty<MovieModel> movies = new SimpleListProperty<>();

    private static DataManager instance = null;

    public static DataManager getInstance()
    {
        if (instance == null)
        {
            instance = new DataManager();
        }

        return instance;
    }

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
        database = new MovieDatabase();

        movies.addListener(onMovieListChanged());
    }

    private ListChangeListener<MovieModel> onMovieListChanged()
    {
        return new ListChangeListener<>()
        {
            @Override
            public void onChanged(Change<? extends MovieModel> c)
            {
                while (c.next())
                {
                    for (var added : c.getAddedSubList())
                    {
                        System.out.println("movie added");

                        added.addListener(onMovieDataInvalidated());
                    }

                    for (var removed : c.getRemoved())
                    {
                        System.out.println("movie removed");
                        database.delete(removed.getID());
                        removed.removeListener(onMovieDataInvalidated());
                    }
                }
            }
        };
    }

    private InvalidationListener onMovieDataInvalidated()
    {
        return new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                DataManager.getInstance().update((MovieModel)observable);
            }
        };
    }

    // get all movies from db
    public void load() throws IOException, SQLException 
    {
        List<DBMovieData> DBMovies = database.getAllMovies();

        database.getAllMovies();

        List<MovieInfo> ApiMovies = OpenMovieNetwork.getInstance().get(DBMovies.stream().map(DBMovieData::getImdbid).collect(Collectors.toList()));

        movies.setAll(Converters.convert(DBMovies, ApiMovies));
    }

    public ListProperty<MovieModel> getMovies()
    {
        return movies;
    }

    //TODO: Make DB method
    public ListProperty<MovieModel> getMoviesOfGenre(String genre) throws IOException {
        ListProperty<MovieModel> returnlist = new SimpleListProperty<MovieModel>();
        List<DBMovieData> DBMovies = database.getMoviesByGenre(genre);

        // get any and all movie info from api with id from db
        List<MovieInfo> ApiMovies = OpenMovieNetwork.getInstance().get(DBMovies.stream().map(DBMovieData::getImdbid).collect(Collectors.toList()));
        returnlist.setAll(Converters.convert(DBMovies, ApiMovies));
        return returnlist;
    }

    public static SearchModel searchMovies(String title) throws IOException
    {
        return OpenMovieNetwork.getInstance().search(title);
    }

    //Search all movies - for use in search entries.
    public List<String> getAllMovieTitles() throws SQLException {
        List<DBMovieData> DBMovies = database.getAllMovies();
        // get any and all movie info from api with id from db
        List<String> returnList = DBMovies.stream().map(DBMovieData::getTitle).collect(Collectors.toList());
        return returnList;
    }

    /**
     * Checks whether or any instantiated movie hasn't be viewed for two years.
     * When the method is called, one should make sure the list is not empty before doing anything with it.
     * Can possibly be replaced with SQL statement.
     * @return List of MovieModels whose LastViewed date is older than two years.
     */
    public List<MovieModel> sortOldLastViewedMovies(){
        ArrayList<MovieModel> returnList = new ArrayList<>();
        for (MovieModel m : movies.get()){
            if (!m.getLastViewed().equals(null) && !m.getLastViewed().isEmpty()){
                LocalDate lastViewedDate = LocalDate.parse(m.getLastViewed());
                if (LocalDate.now().minusYears(2).isAfter(lastViewedDate.minusDays(1))) {
                    returnList.add(m);
                }
            }
        }
        return returnList;
    }

    public List<MovieModel> sortBadMovies() {
        ArrayList<MovieModel> returnList = new ArrayList<>();
        for (MovieModel m : movies.get()){
            if (m.getPersonalRating() != null && !m.getPersonalRating().isEmpty()){
                Integer rating = Integer.parseInt(m.getPersonalRating());
                if (rating.intValue() <= 2) {
                    returnList.add(m);
                }
            }
        }
        return returnList;
    }

    public void add(String filepath, String ImdbID) throws SQLException, IOException
    {
        // get movie from api
        MovieInfo apiMovie = OpenMovieNetwork.getInstance().get(ImdbID, null, false);

        DBMovieData dbMovie = new DBMovieData();

        dbMovie.setGenre(apiMovie.genre);
        dbMovie.setFilepath(filepath);
        dbMovie.setTitle(apiMovie.title);
        dbMovie.setRating(0);
        dbMovie.setLastViewed(Date.valueOf(LocalDate.now()).toString());
        dbMovie.setImdbid(apiMovie.ID);

        dbMovie = database.create(dbMovie);

        this.movies.add(Converters.convert(dbMovie, apiMovie));
    }

    public void update(MovieModel selectedItem)
    {
        database.update(new DBMovieData(selectedItem.getID(), selectedItem.getTitle(), selectedItem.getGenre(), Integer.parseInt(selectedItem.getPersonalRating()), selectedItem.getPath(), selectedItem.getLastViewed()));
    }


    public List<String> getAllGenres() throws SQLException
    {
        var list = database.getCategories();
        if (!list.contains("N/A"))
            list.add("N/A");

        return list;
    }

    public void updateGenre(String selected, String text)
    {
    }

    public void updateMovieGenre(String imdbid, String[] oldgenre, String[] newgenre)
    {
        database.setMovieCatIds(imdbid, oldgenre, newgenre);
    }
}
