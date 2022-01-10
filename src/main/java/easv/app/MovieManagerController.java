package easv.app;

import easv.app.CustomComponent.Player;
import easv.app.be.FXMLProperties;
import easv.app.be.Movie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class MovieManagerController extends FXMLProperties implements Initializable {

    Movie selectedMovie;
    String imageURL = "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_SX300.jpg";
    String path1 = "C:\\Users\\Sandbxk\\Desktop\\test.mp4";
    String path2 = "E:\\- Media\\Movies\\Life of Brian (1979) 1080p\\Life.Of.Brian..1979.1080p.BluRay.X264.YIFY.mp4";

    public MovieManagerController(){
        tblViewMovies= new TableView();
        tblClmPoster = new TableColumn<Movie, ImageView>();
        tblClmTitle = new TableColumn();
        tblClmType = new TableColumn();
        tblClmImbdRating = new TableColumn();
        tblClmPersonalRating = new TableColumn();
        tblClmLastViewed = new TableColumn();
        tblViewMovies.getColumns().add(this.tblClmPoster);
        tblViewMovies.getColumns().add(this.tblClmTitle);
        tblViewMovies.getColumns().add(this.tblClmType);
        tblViewMovies.getColumns().add(this.tblClmImbdRating);
        tblViewMovies.getColumns().add(this.tblClmPersonalRating);
        tblViewMovies.getColumns().add(this.tblClmLastViewed);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMovieTable();

        selectedMovie = new Movie();
        selectedMovie.setPoster(imageURL);
        selectedMovie.setTitle("John Wick");
        selectedMovie.setPath(path2);
        selectedMovie.setPersonalRating("5");
        selectedMovie.setLastViewed(LocalDate.now().toString());
        selectedMovie.setRatings("10/10");
        selectedMovie.setType("Movie");
        movieRating.setRating(Double.parseDouble(selectedMovie.getPersonalRating()));
        for (int i = 0; i < 20; i++){
            lstViewGenre.getItems().add("Romance");
        }
        imgViewMovPoster.setImage(selectedMovie.getPoster().getImage());
        tblViewMovies.getItems().add(selectedMovie);

    }

    public void onSearch(ActionEvent event) {

    }

    public void onComboBox(ActionEvent event) {
    }

    public void onClearSearchFilter(ActionEvent event) {

    }

    public void onPlayMovie(ActionEvent event) {
        /*try {
            Desktop.getDesktop().open(new File(selectedMovie.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
        Media pick = new Media(selectedMovie.getPath());
        MediaPlayer player = new MediaPlayer(pick);

        // Add a mediaView, to display the media. Its necessary !
        // This mediaView is added to a Pane
        MediaView mediaView = new MediaView(player);

        // Add to scene
        Group root = new Group(mediaView);
        Scene scene = new Scene(root, 500, 200);

        // Show the stage
        Stage stage = new Stage();
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();

        // Play the media once the stage is shown
        player.play();
        selectedMovie.setLastViewed(LocalDate.now().toString());
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
       FileChooser fileChooser = new FileChooser();
        File selectedFile =  fileChooser.showOpenDialog(new Stage());
        selectedMovie.setPath(selectedFile.getAbsolutePath());
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
        this.tblClmPoster.setStyle("-fx-alignment: CENTER;");

        this.tblClmPoster.setCellValueFactory(new PropertyValueFactory<Movie, ImageView>("poster"));
        setCellFactory(tblClmTitle, Pos.CENTER_LEFT);
        this.tblClmTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        setCellFactory(tblClmType, Pos.CENTER_LEFT);
        this.tblClmType.setCellValueFactory(new PropertyValueFactory<Movie, String>("type"));
        setCellFactory(tblClmImbdRating, Pos.CENTER_LEFT);
        this.tblClmImbdRating.setCellValueFactory(new PropertyValueFactory<Movie, String>("ratings"));
        setCellFactory(tblClmPersonalRating, Pos.CENTER_LEFT);
        this.tblClmPersonalRating.setCellValueFactory(new PropertyValueFactory<Movie, String>("personalRating"));
        setCellFactory(tblClmLastViewed, Pos.CENTER_LEFT);
        this.tblClmLastViewed.setCellValueFactory(new PropertyValueFactory<Movie, String>("lastViewed"));
    }

    private void setCellFactory(TableColumn tblClm, Pos pos){
        tblClm.setCellFactory(param -> {

            TableCell<?, ?> tc = new TableCell<Movie, String>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null){
                        setText(item);
                    }
                }
            };
            tc.setAlignment(pos);
            tc.setStyle("-fx-font-size: 14");
            return tc;
    });
    }


}
