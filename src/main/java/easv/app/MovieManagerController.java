package easv.app;

import easv.app.be.FXMLProperties;
import easv.app.be.Movie;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class MovieManagerController extends FXMLProperties implements Initializable {

    Movie selectedMovie;
    String imageURL = "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_SX300.jpg";

    public MovieManagerController(){
        tblViewMovies= new TableView();
        tblClmPoster = new TableColumn<Movie, ImageView>();
        tblClmTitle = new TableColumn();
        tblClmYear = new TableColumn();
        tblClmImbdRating = new TableColumn();
        tblClmPersonalRating = new TableColumn();
        tblClmLastViewed = new TableColumn();
        tblViewMovies.getColumns().add(this.tblClmPoster);
        tblViewMovies.getColumns().add(this.tblClmTitle);
        tblViewMovies.getColumns().add(this.tblClmYear);
        tblViewMovies.getColumns().add(this.tblClmImbdRating);
        tblViewMovies.getColumns().add(this.tblClmPersonalRating);
        tblViewMovies.getColumns().add(this.tblClmLastViewed);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMovieTable();
        initializePosterClm();

        selectedMovie = new Movie();
        selectedMovie.setPoster(imageURL);
        selectedMovie.setTitle("Jgon");
        selectedMovie.setPath("C:\\Users\\Sandbxk\\Desktop\\test.mp4");
        selectedMovie.setPersonalRating("5");
        movieRating.setRating(Double.parseDouble(selectedMovie.getPersonalRating()));
        for (int i = 0; i < 20; i++){
            lstViewGenre.getItems().add("Romance");
        }
        imgViewMovPoster.setImage(new Image(imageURL));
        tblViewMovies.getItems().add(selectedMovie);

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
        selectedMovie.setLastViewed(LocalDate.now());
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
        int rating = (int) movieRating.getRating();
        selectedMovie.setRated(String.valueOf(rating));
        System.out.println(rating);
    }

    private void initializeMovieTable(){
        //this.tblClmPoster.setCellValueFactory();
        //this.tblClmPoster.setCellValueFactory(new PropertyValueFactory<Movie, ImageView>("poster"));
        this.tblClmTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        this.tblClmYear.setCellValueFactory(new PropertyValueFactory<Movie, String>("year"));
        this.tblClmImbdRating.setCellValueFactory(new PropertyValueFactory<Movie, String>("ratings"));
        this.tblClmPersonalRating.setCellValueFactory(new PropertyValueFactory<Movie, String>("personalRating"));
        this.tblClmLastViewed.setCellValueFactory(new PropertyValueFactory<Movie, LocalDate>("lastViewed"));
    }

    //TODO: TEST
    private void initializePosterClm() {
        this.tblClmPoster.setCellFactory(param -> new TableCell<>());
        //tblClmPoster.setCellValueFactory(cellData -> new SimpleObjectProperty<Movie>());
        /*
        tblClmPoster.setCellFactory(param -> {
            //Set up the ImageView
            param.

            final ImageView imageview = new ImageView();
            imageview.setFitHeight(10);
            imageview.setFitWidth(10);
            ///imageview.setImage(imageComputer); //uncommenting this places the image on all cells, even empty ones
            //Set up the Table
            TableCell<Movie, Movie> cell = new TableCell<>() {
                @Override
                public void updateItem(Movie item, boolean empty) {
                    if (item != null) {  // choice of image is based on values from item, but it doesn't matter now
                        imageview.setImage(item.getPoster());
                    } else {
                        imageview.setImage(null);
                    }

                }
            };

            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });

         */
    }
}
