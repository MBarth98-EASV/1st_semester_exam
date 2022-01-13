package easv.app.be;

import easv.app.be.json.SearchResult;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.Optional;

public class SearchModel
{
    public static SearchModel fromSearchResult(final SearchResult result)
    {
        SearchModel model = new SearchModel();

        model.setResponse(result.response);
        model.setItemCount(Integer.parseInt(Optional.ofNullable(result.results).orElse("0")));

        model.movies.set(FXCollections.observableArrayList());
        for (var info : result.movies)
        {
            model.movies.add(SearchedMovieModel.fromSearchInfo(info));
        }

        return model;
    }

    /*
     *   BELOW: bindable properties
     */

    private final ListProperty<SearchedMovieModel> movies = new SimpleListProperty<>();

    private final IntegerProperty itemCount = new SimpleIntegerProperty();

    private final StringProperty response = new SimpleStringProperty();

    /*
     *   BELOW: standard getters and setters
     */

    public List<SearchedMovieModel> getMovies()
    {
        return movies;
    }

    public ListProperty<SearchedMovieModel> moviesProperty()
    {
        return movies;
    }

    public void setMovies(List<SearchedMovieModel> movies)
    {
        this.movies.set(FXCollections.observableList(movies));
    }

    public int getItemCount()
    {
        return itemCount.get();
    }

    public IntegerProperty itemCountProperty()
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

    public StringProperty responseProperty()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response.set(response);
    }


    @Override
    public String toString()
    {
        return "SearchModel{" +
                "movies=" + movies +
                ", itemCount=" + itemCount +
                ", response=" + response +
                '}';
    }
}
