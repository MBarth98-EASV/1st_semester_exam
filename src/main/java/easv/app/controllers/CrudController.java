package easv.app.controllers;

import easv.app.be.FXMLProperties;
import easv.app.bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CrudController extends FXMLProperties implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.txtFieldSearchNewMovie.setText("avengers");
        onSearchNewMovieTitles(new ActionEvent());
    }

    //EditGenreBar
    public void onDeleteGenre(ActionEvent event) {
    }

    public void onNewGenre(ActionEvent event) {
    }

    public void onSaveEditGenre(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    //NewMovie
    public void onSearchNewMovieTitles(ActionEvent event)
    {
        try
        {
            var search = DataManager.searchMovies(this.txtFieldSearchNewMovie.getText());

            for (var s : search.getMovies())
            {
                System.out.println(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onNewMovieDone(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onPickFile(ActionEvent event) {
        FileChooser fc = new FileChooser();

        File selectedFile =  fc.showOpenDialog(new Stage());
    }

    //EditMovie
    public void onEditMovieSave(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
