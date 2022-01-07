package easv.app;

import easv.app.be.FXMLProperties;
import easv.app.be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MovieManagerController extends FXMLProperties implements Initializable {

    Movie selectedMovie;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedMovie = new Movie();
        selectedMovie.setPath("C:\\Users\\Sandbxk\\Desktop\\test.mp4");
        for (int i = 0; i < 20; i++){
            lstViewGenre.getItems().add("Romance");
        }


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
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewMovie.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Movie");
            stage.setMaxHeight(400);
            stage.setMinHeight(400);
            stage.setMaxWidth(600);
            stage.setMinWidth(600);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteMovie(ActionEvent event) {
        System.out.println(lstViewGenre.getSelectionModel().getSelectedItem());
    }

    public void onEditMovie(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditMovie.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Edit Movie");
            stage.setMaxHeight(314);
            stage.setMinHeight(314);
            stage.setMaxWidth(353);
            stage.setMinWidth(353);
            stage.setScene(new Scene(root, 353, 314));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditGenre(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditGenreBar.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Edit Genre");
            stage.setMaxHeight(332);
            stage.setMinHeight(332);
            stage.setMaxWidth(378);
            stage.setMinWidth(378);
            stage.setScene(new Scene(root, 378, 332));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMovieRated(MouseEvent mouseEvent) {
    }
}
