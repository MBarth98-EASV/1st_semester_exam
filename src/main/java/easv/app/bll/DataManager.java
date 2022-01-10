package easv.app.bll;

import easv.app.Utils.Converter;
import easv.app.be.MovieModel;
import easv.app.be.json.MovieInfo;
import easv.app.dal.api.OpenMovieNetwork;
import easv.app.dal.db.IDatabaseCRUD;
import easv.app.dal.db.MovieDatabase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager
{

    private ListProperty<MovieModel> movies = new SimpleListProperty<>();

    public DataManager()
    {
        movies.set(FXCollections.observableArrayList());
    }

    public void load() throws IOException
    {
        var model = OpenMovieNetwork.getInstance().get(null, "john wick", false);
        var model2 = OpenMovieNetwork.getInstance().get(null, "star wars", false);
        movies.addAll(Converter.convert(model), Converter.convert(model2));
    }

    public ListProperty<MovieModel> getMovies()
    {
        return movies;
    }


    public void update(MovieModel selectedItem)
    {
        // update in db
    }
}
