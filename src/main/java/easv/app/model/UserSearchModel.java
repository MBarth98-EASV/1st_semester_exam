package easv.app.model;

import easv.app.Utils.CustomComponent.AutoCompleteTextField;
import easv.app.be.MovieModel;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

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

        if (movie == null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any songs or playlists.");
            errorAlert.showAndWait();
        }
        else
        {
            table.getSelectionModel().select(movie);
            table.scrollTo(movie);
        }
    }




}
