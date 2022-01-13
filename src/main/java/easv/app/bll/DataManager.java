package easv.app.bll;

import easv.app.Utils.Converters;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.dal.api.OpenMovieNetwork;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
<<<<<<< Updated upstream
=======
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

public class DataManager
{

    private ListProperty<MovieModel> movies = new SimpleListProperty<>();

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
    }

    // may be initial load or reset later
<<<<<<< Updated upstream
    public void load() throws IOException
    {
        // get all movies from db
=======
    public void load() throws IOException, SQLException {
        var DBMovies = database.getAllMovies();
>>>>>>> Stashed changes

        // get any and all movie info from api with id from db
        var ApiMovies = OpenMovieNetwork.getInstance().get(DBMovies.stream().map(DBMovieData::getImdbid).collect(Collectors.toList()));

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


    public void add( /* what parameters ? */ )
    {
        // get movie from api

        // add in db
            // rating
            // imdb id
            // filepath
            // category

        // add to movies list to update GUI
    }

    public void update(MovieModel selectedItem)
    {
        // update in db
    }
}
