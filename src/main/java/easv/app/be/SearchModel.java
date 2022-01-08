package easv.app.be;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

public class SearchModel
{
    @SerializedName("Search")
    private SimpleListProperty<MovieModel> movies = new SimpleListProperty<>();

    @SerializedName("totalResults")
    private SimpleIntegerProperty itemCount = new SimpleIntegerProperty();

    @SerializedName("Response")
    private SimpleStringProperty response = new SimpleStringProperty();

    public SearchModel(List<MovieModel> movies, int count, String response)
    {
        this.setMovies(movies);
        this.setItemCount(count);
        this.setResponse(response);
    }

    public List<MovieModel> getMovies()
    {
        return movies;
    }

    public SimpleListProperty<MovieModel> moviesProperty()
    {
        return movies;
    }

    public void setMovies(List<MovieModel> movies)
    {
        this.movies.set(FXCollections.observableList(movies));
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
