package easv.app.bll;

import easv.app.Utils.Converters;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.be.json.MovieInfo;
import easv.app.dal.api.OpenMovieNetwork;
import easv.app.be.DBMovieData;
import easv.app.dal.db.MovieDatabase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
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

    private DataManager()
    {
        movies.set(FXCollections.observableArrayList());
        database = new MovieDatabase();
    }

    // get all movies from db
    public void load() throws IOException, SQLException 
    {
        List<DBMovieData> DBMovies = database.getAllMovies();

        database.getAllMovies();

        // get any and all movie info from api with id from db
        List<MovieInfo> ApiMovies = OpenMovieNetwork.getInstance().get(DBMovies.stream().map(DBMovieData::getImdbid).collect(Collectors.toList()));

        movies.setAll(Converters.convert(DBMovies, ApiMovies));
    }

    public ListProperty<MovieModel> getMovies()
    {
        return movies;
    }

    public static SearchModel searchMovies(String title) throws IOException
    {
        return OpenMovieNetwork.getInstance().search(title);
    }

    /**
     * Checks whether or any instantiated movie hasn't be viewed for two years.
     * When the method is called, one should make sure the list is not empty before doing anything with it.
     * Can possibly be replaced with SQL statement.
     * @return List of MovieModels whose LastViewed date is older than two years.
     */
    public List<MovieModel> getOldLastViewedMovies(){
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
        database.update(new DBMovieData(-1, selectedItem.getTitle(), Integer.parseInt(selectedItem.getPersonalRating()), selectedItem.getPath(), selectedItem.getID(), "1944/06/06"));
    }

    public void delete(MovieModel selectedItem) throws SQLException
    {
        database.delete(selectedItem.getID());
    }
}
