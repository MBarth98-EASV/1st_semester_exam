package easv.app.bll;

import easv.app.be.MovieModel;
import easv.app.be.json.MovieInfo;
import easv.app.dal.api.OpenMovieNetwork;
import easv.app.dal.db.MovieDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    public DataManager()
    {
        MovieDatabase dbaccess = new MovieDatabase();
    }

    private ObservableList<MovieModel> currentlyPlayableMovies;



}
