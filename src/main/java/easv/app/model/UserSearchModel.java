package easv.app.model;

import easv.app.App;
import easv.app.Utils.CustomComponent.ComboBoxEnum;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserSearchModel {

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
    public void search(TableView<MovieModel> table, String textField)
    {
        MovieModel movie = getObjectFromText(table.getItems().stream().toList(), textField);

        if (movie != null)
        {
            table.getSelectionModel().select(movie);
            table.scrollTo(movie);
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any movies.");
            errorAlert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            errorAlert.showAndWait();
        }
    }

    //Filter current movies show in tableview - for use in search
    public List<String> getCurrentFilterEntries(ObservableList<MovieModel> inputList, ComboBoxEnum param){
        ArrayList<String> error = new ArrayList<>();
        error.add("Error");
        return switch (param) {
            case TITLE -> inputList.stream().map(MovieModel::getTitle).collect(Collectors.toList());
            case RATING -> inputList.stream().map(MovieModel::getPersonalRating).collect(Collectors.toList());
            case IMBDRATING -> inputList.stream().map(MovieModel::getImdbRating).collect(Collectors.toList());
            default -> error;
        };
    }

    //For use in search entries
    public List<String> getAllMovieTitles() throws SQLException {
        return DataManager.getInstance().getAllMovieTitles();
    }



    public void searchCurrent(TableView<MovieModel> tblView, String filter){

    }

    public void searchAll(TableView<MovieModel> tblView, String filter){

    }

    public void filterTitle(TableView<MovieModel> tblView, String filter){

    }

    public void filterRating(TableView<MovieModel> tblView, String filter){

    }

    public void filterGenre(TableView<MovieModel> tblView, String filter){

    }

    public void filterImbdRating(TableView<MovieModel> tblView, String filter){

    }

}
