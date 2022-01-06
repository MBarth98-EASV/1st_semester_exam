package easv.app;

import easv.app.be.FXMLProperties;
import easv.app.be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieManagerController extends FXMLProperties implements Initializable {

    Movie selectedMovie;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedMovie = new Movie();
        selectedMovie.setPath("C:\\Users\\Sandbxk\\Desktop\\test.mp4");

    }

    public void onSearch(ActionEvent event) {
    }

    public void onComboBox(ActionEvent event) {
    }

    public void onClearSearchFilter(ActionEvent event) {

    }

    public void onPlayMovie(ActionEvent event) {
        try {
            Desktop.getDesktop().open(new File(selectedMovie.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNewMovie(ActionEvent event) {
    }

    public void onDeleteMovie(ActionEvent event) {
    }

    public void onEditMovie(ActionEvent event) {
    }

    public void onEditGenre(ActionEvent event) {
    }

    public void onMovieRated(MouseEvent mouseEvent) {
    }
}
