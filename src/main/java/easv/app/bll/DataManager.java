package easv.app.bll;

import easv.app.be.MovieModel;
import easv.app.be.SearchModel;
import easv.app.dal.api.OpenMovieNetwork;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Checks whether or any instantiated movie hasn't be viewed for two years.
     * When the method is called, one should make sure the list is not empty before doing anything with it.
     * Can possibly be replaced with SQL statement.
     * @return List of MovieModels whose LastViewed date is older than two years.
     */
    public List<MovieModel> sortOldLastViewedMovies(){
        ArrayList<MovieModel> returnList = new ArrayList<>();
        for (MovieModel m : movies.get()){
            if (m.getLastViewed() != null && !m.getLastViewed().isEmpty()){
                LocalDate lastViewedDate = LocalDate.parse(m.getLastViewed());
                if (LocalDate.now().minusYears(2).isAfter(lastViewedDate.minusDays(1))) {
                    returnList.add(m);
                }
            }
        }
        return returnList;
    }

    public List<MovieModel> sortBadMovies() {
        ArrayList<MovieModel> returnList = new ArrayList<>();
        for (MovieModel m : movies.get()){
            if (m.getPersonalRating() != null && !m.getPersonalRating().isEmpty()){
                Integer rating = Integer.parseInt(m.getPersonalRating());
                if (rating.intValue() <= 2) {
                    returnList.add(m);
                }
            }
        }
        return returnList;
    }


    public void add( /* what parameters ? */ )
    {
        // get movie from api

        // add in db
            // rating
            // imdb id
            // filepath
            // category

        // add to movies list to update GUI
    }

    public void update(MovieModel selectedItem)
    {
        // update in db
    }
}
