package easv.app.bll;

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

public class DataManager
{
    MovieDatabase database = new MovieDatabase();
    private ListProperty<MovieModel> movies = new SimpleListProperty<>();

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
    }

    // may be initial load or reset later
    public void load() throws IOException, SQLException {
        database.getAllMovies();

        // get any and all movie info from api with id from db

        // set all items in movies list with combined data (api movie info + db movie info)

        var apiInfo1 = OpenMovieNetwork.getInstance().get(null, "john wick", false);
        var apiInfo2 = OpenMovieNetwork.getInstance().get(null, "juya", false);
        movies.setAll(MovieModel.fromMovieInfo(apiInfo1), MovieModel.fromMovieInfo(apiInfo2));
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

    public void delete(MovieModel selectedItem)
    {
        database.delete(selectedItem.getID());
    }
}
