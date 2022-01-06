package easv.app.be.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import easv.app.Utils.Json.Adapter;
import easv.app.Utils.Json.Adapters;
import easv.app.be.Movie;
import easv.app.be.api.MovieModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SearchModel extends Adapter<SearchModel>
{
    public SearchModel(List<MovieModel> movies, int count, String response)
    {
        super(SearchModel.class);
        System.out.println("hello");
        this.movies.setAll(movies);
        this.itemCount.set(count);
        this.response.set(response);
    }

    @SerializedName("Search")
    private SimpleListProperty<MovieModel> movies = new SimpleListProperty<>();

    @SerializedName("totalResults")
    private SimpleIntegerProperty itemCount = new SimpleIntegerProperty();

    @SerializedName("Response")
    private SimpleStringProperty response = new SimpleStringProperty();

    @SerializedName("Error")
    private String error;

    public ObservableList<MovieModel> getMovies()
    {
        return movies.get();
    }

    public SimpleListProperty<MovieModel> moviesProperty()
    {
        return movies;
    }

    public void setMovies(ObservableList<MovieModel> movies)
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


    /**
     *      defined here for dependency inversion
     *
     *      places that need a json deserializer should not need to know about this class as well.
     * */
    @Override
    protected JsonDeserializer<SearchModel> deserializer()
    {
        System.out.println("hello");

        return (jsonElement, type, jsonDeserializationContext) ->
        {
            JsonArray searchResult = jsonElement.getAsJsonObject().get("Search").getAsJsonArray();
            int elementCount = jsonElement.getAsJsonObject().get("totalResults").getAsInt();
            String response = jsonElement.getAsJsonObject().get("Response").getAsString();

            List<MovieModel> movies = new ArrayList<>();

            for (int i = 0; i < elementCount; i++)
            {
                MovieModel movieInfo = new MovieModel();

                JsonObject object = searchResult.get(i).getAsJsonObject();

                movieInfo.setID(object.get("ID").getAsString());

                movies.add(movieInfo);
            }

            return new SearchModel(movies, elementCount, response);
        };
    }
}
