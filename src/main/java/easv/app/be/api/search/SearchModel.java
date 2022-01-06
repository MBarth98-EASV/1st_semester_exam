package easv.app.be.api.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.SerializedName;
import easv.app.Utils.Json.MovieModelList;
import easv.app.be.api.MovieModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchModel
{
    public SearchModel(MovieModelList movies, int count, String response)
    {
        this.movies.set(movies);
        this.itemCount.set(count);
        this.response.set(response);
    }

    @SerializedName("Search")
    private SimpleObjectProperty<MovieModelList> movies = new SimpleObjectProperty<MovieModelList>();

    @SerializedName("totalResults")
    private SimpleIntegerProperty itemCount = new SimpleIntegerProperty();

    @SerializedName("Response")
    private SimpleStringProperty response = new SimpleStringProperty();

    @SerializedName("Error")
    private String error;

    public MovieModelList getMovies()
    {
        return movies.get();
    }

    public SimpleObjectProperty<MovieModelList> moviesProperty()
    {
        return movies;
    }

    public void setMovies(MovieModelList movies)
    {
        this.movies.set(movies);
    }

    public int getItemCount()
    {
        return itemCount.get();
    }

    public SimpleIntegerProperty itemCountProperty()
    {
        return itemCount;
    }

    public void setItemCount(int itemCount)
    {
        this.itemCount.set(itemCount);
    }

    public String getResponse()
    {
        return response.get();
    }

    public SimpleStringProperty responseProperty()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response.set(response);
    }
}
