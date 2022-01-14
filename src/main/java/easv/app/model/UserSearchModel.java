package easv.app.model;

import easv.app.App;
import easv.app.utils.customComponent.ComboBoxEnum;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserSearchModel {

    ObservableList<MovieModel> movieListToFilter;

    public UserSearchModel(){
        movieListToFilter = DataManager.getInstance().getMovies().get();
    }

    private MovieModel getObjectFromText(List<MovieModel> inputList, String search)
    {
        if (search != null && !search.isEmpty())
        {
            for (MovieModel movie : inputList)
            {
                if (movie.toString().equalsIgnoreCase(search)) {
                    return movie;
                }
            }
            if (search.length() >= 3)
            {
                List<MovieModel> backupSearch = inputList.stream().filter(m -> m.toString()
                        .toLowerCase().contains(search.toLowerCase())).sorted().collect(Collectors.toList());
                return backupSearch.get(0);
            }
        }
        return null;
    }

    /**
     * Gets matching songmodel and selects it in the table upon search.
     * @param textField The searchbar.
     */
    public MovieModel search(String textField)
    {
        MovieModel movie = getObjectFromText(DataManager.getInstance().getMovies(), textField);

        if (movie != null)
        {
            return movie;
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any movies.");
            errorAlert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            errorAlert.showAndWait();
            return null;
        }
    }

    //Filter current movies show in tableview - for use in search
    public List<String> getCurrentFilterEntries(ComboBoxEnum param){
        return switch (param) {
            case TITLE -> DataManager.getInstance().getMovies().stream().map(MovieModel::getTitle).collect(Collectors.toList());
            case RATING -> DataManager.getInstance().getMovies().stream().map(MovieModel::getPersonalRating).collect(Collectors.toList());
            case IMBDRATING -> DataManager.getInstance().getMovies().stream().map(MovieModel::getImdbRating).collect(Collectors.toList());
            default -> DataManager.getInstance().getMovies().stream().map(MovieModel::toString).collect(Collectors.toList());
        };
    }

    public ObservableList<MovieModel> filterTitle(String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        ArrayList<String> filterList = new ArrayList<>();

        for (MovieModel movie : DataManager.getInstance().getMovies()){
            filterList.add(movie.getTitle());
        }

        List<String> filteredStringList = filterList.stream().filter(m ->
                m.toLowerCase().contains(filter.toLowerCase())).sorted().collect(Collectors.toList());

        for (String title : filteredStringList){
            for (MovieModel movie : DataManager.getInstance().getMovies())
            {
                if (movie.getTitle().equalsIgnoreCase(title)) {
                    returnList.add(movie);
                }
            }
        }
        return errorHandling(returnList);
    }

    public ObservableList<MovieModel> filterRating(String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        Integer filterValue = Integer.parseInt(filter);

        for (MovieModel movie : DataManager.getInstance().getMovies()){
            if (movie.getPersonalRatingAsInt() >= filterValue.intValue()){
                returnList.add(movie);
            }
        }
        return errorHandling(returnList);
    }

    public ObservableList<MovieModel> filterImbdRating(String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        Double filterValue = Double.parseDouble(filter);

        for (MovieModel movie : DataManager.getInstance().getMovies()){
            if (movie.getImbdRatingAsDouble() >= filterValue.doubleValue()){
                returnList.add(movie);
            }
        }
        return errorHandling(returnList);
    }

    private ObservableList<MovieModel> errorHandling(ObservableList<MovieModel> returnList) {
        if (returnList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input not valid");
            alert.setContentText("Your search did not match any movies.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.show();
            returnList.add(new MovieModel());
            return returnList;
        }
        return returnList;
    }

}
