package easv.app.bll;

import easv.app.App;
import easv.app.utils.customComponent.ComboBoxEnum;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserSearchManager {


    /**
     * Compares the searched string with the toString method of any MovieModel present in the inputList.
     * Should there not be an exact match, the "backupSearch" will be used, which stores every MovieModel's toString,
     * that contains the search query. The list is alphabetically sorted, and the first index is returned.
     * @param inputList: The list to compare with.
     * @param search: the searched string
     * @return
     */
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
     * Gets matching MovieModel and returns it.
     * @param textField The searchbar input.
     */
    public MovieModel search(ObservableList<MovieModel> tblViewItems,String textField)
    {
        MovieModel movie = getObjectFromText(tblViewItems, textField);

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

    /**
     * Returns a list of Strings, containing the chosen characteristic of every MovieModel present in the TableView.
     * Used for search entries.
     * @param param: Currently selected function of the applications search textField.
     * @return
     */
    //Filter current movies show in tableview - for use in search
    public List<String> getCurrentFilterEntries(ObservableList<MovieModel> tblViewItems, ComboBoxEnum param){
        return switch (param) {
            case TITLE -> tblViewItems.stream().map(MovieModel::getTitle).collect(Collectors.toList());
            case RATING -> tblViewItems.stream().map(MovieModel::getPersonalRating).collect(Collectors.toList());
            case IMBDRATING -> tblViewItems.stream().map(MovieModel::getImdbRating).collect(Collectors.toList());
            default -> tblViewItems.stream().map(MovieModel::toString).collect(Collectors.toList());
        };
    }

    /**
     * Gets a list of every MovieModel's titel currently present in the tableview. Said list is then filtered in a stream,
     * wherein they are compared to the String filter. The filtered list iterated through,
     * with a nested foreach loop where every filtered title is compared the title of any currently present Movie in the tableview.
     * If there is a match between a filtered title and a movie's title, the movie is added to the return list and returned.
     * @param filter the String used for the filter query.
     * @returnA filtered list of Movies, or should the query not match any movie,
     * it displays an alert and returns and empty movie.
     */
    public ObservableList<MovieModel> filterTitle(ObservableList<MovieModel> tblViewItems, String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        ArrayList<String> filterList = new ArrayList<>();

        for (MovieModel movie : tblViewItems){
            filterList.add(movie.getTitle());
        }

        List<String> filteredStringList = filterList.stream().filter(m ->
                m.toLowerCase().contains(filter.toLowerCase())).sorted().collect(Collectors.toList());

        for (String title : filteredStringList ){
            for (MovieModel movie : tblViewItems)
            {
                if (movie.getTitle().equalsIgnoreCase(title)) {
                    returnList.add(movie);
                }
            }
        }
        return errorHandling(returnList);
    }

    /**
     * Parses the given String as an int and iterates through the Movies currently present in the tableview.
     * If any movie has an personal rating greater than or equal to the inserted value, it will be added
     * to the returnList and returned.
     * @param filter the String to be parsed as a double
     * @return A filtered list of Movies, or should the query not match any movie,
     * it displays an alert and returns and empty movie.
     */
    public ObservableList<MovieModel> filterRating(ObservableList<MovieModel> tblViewItems, String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        Integer filterValue = Integer.parseInt(filter);

        for (MovieModel movie : tblViewItems){
            if (movie.getPersonalRatingAsInt() >= filterValue.intValue()){
                returnList.add(movie);
            }
        }
        return errorHandling(returnList);
    }

    /**
     * Parses the given String as a double and iterates through the Movies currently present in the tableview.
     * If any movie has an IMBD rating greater than or equal to the inserted value, it will be added
     * to the returnList and returned.
     * @param filter the String to be parsed as a double
     * @return A filtered list of Movies, or should the query not match any movie,
     * it displays an alert and returns and empty movie.
     */
    public ObservableList<MovieModel> filterImbdRating(ObservableList<MovieModel> tblViewItems, String filter){
        ObservableList<MovieModel> returnList = FXCollections.observableArrayList();
        Double filterValue = Double.parseDouble(filter);

        for (MovieModel movie : tblViewItems){
            if (movie.getImbdRatingAsDouble() >= filterValue.doubleValue()){
                returnList.add(movie);
            }
        }
        return errorHandling(returnList);
    }

    /**
     * Display an Alert and returns an ObservableList with an empty movie object.
     * Used to handle possible errors that might slip through checks when the methods
     * are called.
     */
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
