package easv.app.bll;

import easv.app.Utils.Converters;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.be.json.MovieInfo;
import easv.app.dal.api.OpenMovieNetwork;
import easv.app.dal.db.DBMovieData;
import easv.app.dal.db.MovieDatabase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.SQLException;


public class DataManager
{
    MovieDatabase database = new MovieDatabase();
    private ListProperty<MovieModel> movies = new SimpleListProperty<>();

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
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


    public void add(String filepath) throws SQLException {
        // get movie from api
        MovieInfo apiMovie = new MovieInfo();

        database.create(new DBMovieData(-1, apiMovie.title, 0, filepath, apiMovie.ID, "1944/06/06"));

        // add to movies list to update GUI
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
