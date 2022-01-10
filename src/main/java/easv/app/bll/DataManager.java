package easv.app.bll;

import easv.app.Utils.Converter;
import easv.app.be.MovieModel;
import easv.app.dal.api.OpenMovieNetwork;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;

public class DataManager
{

    private ListProperty<MovieModel> movies = new SimpleListProperty<>();

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
    }

    // may be initial load or reset later
    public void load() throws IOException
    {
        // get all movies from db

        // get any and all movie info from api with id from db

        // set all items in movies list with combined data (api movie info + db movie info)

        var apiInfo1 = OpenMovieNetwork.getInstance().get(null, "john wick", false);
        var apiInfo2 = OpenMovieNetwork.getInstance().get(null, "star wars", false);
        movies.setAll(Converter.convert(apiInfo1), Converter.convert(apiInfo2));
    }

    public ListProperty<MovieModel> getMovies()
    {
        return movies;
    }


    public void add( /* what parameters ? */ )
    {
        // add in db
        // add to movies list to update GUI
    }

    public void update(MovieModel selectedItem)
    {
        // update in db
    }
}
