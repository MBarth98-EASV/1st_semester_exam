package easv.app.controllers;

import easv.app.App;
import easv.app.be.FXMLProperties;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ListResourceBundle;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieManagerController extends FXMLProperties implements Initializable {

    DataManager data;
    MovieModel selectedMovie;

    public MovieManagerController()
    {
        data = new DataManager();

        tblViewMovies.getColumns().add(tblClmPoster);
        tblViewMovies.getColumns().add(tblClmTitle);
        tblViewMovies.getColumns().add(tblClmType);
        tblViewMovies.getColumns().add(tblClmImbdRating);
        tblViewMovies.getColumns().add(tblClmPersonalRating);
        tblViewMovies.getColumns().add(tblClmLastViewed);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            data.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        initializeMovieTable();

        tblViewMovies.getSelectionModel().selectedItemProperty().addListener(observable -> updateSelectedItemBindings());
        tblViewMovies.getSelectionModel().selectFirst();
    }

    public void updateSelectedItemBindings()
    {
        var selected = this.tblViewMovies.getSelectionModel().getSelectedItem();

        // simple text fields
        lblMovTitle.textProperty().set(selected.getTitle());
        lblMovActors.textProperty().set(selected.getActors());
        lblMovCountry.textProperty().set(selected.getCountry());
        lblMovDirector.textProperty().set(selected.getDirector());
        lblMovRating.textProperty().set(selected.getAgeRating());
        lblMovImbdRating.textProperty().set(selected.getRated());
        lblMovLanguage.textProperty().set(selected.getLanguage());
        lblMovRuntime.textProperty().set(selected.getRuntime());
        lblMovWriters.textProperty().set(selected.getWriter());
        lblMovYear.textProperty().set(selected.getYear());
        txtAreaMovPlot.setText(selected.getPlot());

        selectedMovie = selected;
        imgViewMovPoster.setImage(selected.getPoster().getImage());
        movieRating.setRating(Double.parseDouble(Optional.ofNullable(selected.getPersonalRating()).orElse("0.0")));
    }

    public void onSearch(ActionEvent event) {

    }

    public void onComboBox(ActionEvent event) {
    }

    public void onClearSearchFilter(ActionEvent event) {

    }

    public void onPlayMovie(ActionEvent event)
    {
        Parent root = null;
        try {
            Stage stage = new Stage();
            ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][]{
                            {"selectedMovie", selectedMovie}, {"playerStage", stage}};}};


            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Player.fxml")), resources);
            stage.setTitle("Player");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNewMovie(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("NewMovie.fxml")));
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

        var selected =  this.tblViewMovies.getSelectionModel().getSelectedItem();

        selected.setPersonalRating(rating + "");
        data.update(selected);
    }

    private void initializeMovieTable()
    {
        this.tblClmPoster.setStyle("-fx-alignment: CENTER;");

        this.tblClmPoster.setCellValueFactory(param -> {
            var poster = param.getValue().posterProperty();
            poster.get().setFitHeight(50);
            poster.get().setPreserveRatio(true);
            return poster; });

        this.tblClmTitle.setCellValueFactory(param -> param.getValue().titleProperty());
        this.tblClmType.setCellValueFactory(param -> param.getValue().typeProperty());
        this.tblClmImbdRating.setCellValueFactory(param -> param.getValue().ratedProperty());
        this.tblClmPersonalRating.setCellValueFactory(param -> param.getValue().personalRatingProperty());
        this.tblClmLastViewed.setCellValueFactory(param -> param.getValue().lastViewedProperty());

        this.tblViewMovies.itemsProperty().bindBidirectional(data.getMovies());

        setCellFactory(tblClmTitle, Pos.CENTER_LEFT);
        setCellFactory(tblClmType, Pos.CENTER_LEFT);
        setCellFactory(tblClmImbdRating, Pos.CENTER_LEFT);
        setCellFactory(tblClmPersonalRating, Pos.CENTER_LEFT);
        setCellFactory(tblClmLastViewed, Pos.CENTER_LEFT);

    }

    private void setCellFactory(TableColumn tblClm, Pos pos){
        tblClm.setCellFactory(param -> {

            TableCell<?, ?> tc = new TableCell<MovieModel, String>(){
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
