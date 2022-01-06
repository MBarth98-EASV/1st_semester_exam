package easv.app.Utils.Json;

import easv.app.be.api.MovieModel;

import java.util.ArrayList;

public class MovieModelList extends ArrayList<MovieModel>
{
    public MovieModelList()
    {
        this.add(new MovieModel());
        this.add(new MovieModel());
    }
}
