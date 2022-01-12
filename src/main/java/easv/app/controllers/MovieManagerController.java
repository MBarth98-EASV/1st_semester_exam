package easv.app.controllers;

import easv.app.App;
import easv.app.Utils.CustomComponent.ComboBoxEnum;
import easv.app.be.FXMLProperties;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        initializeComboBox();
        lstViewGenreContextMenu();
        tblViewMovieContextMenu();
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
        lblMovRuntime.textProperty().set(selected.getRuntime());
        lblMovWriters.textProperty().set(selected.getWriter());
        lblMovYear.textProperty().set(selected.getYear());
        txtAreaMovPlot.setText(selected.getPlot());

        btnLblGenre1.setText(selected.getGenre()[0]);
        btnLblGenre2.setText(selected.getGenre()[1]);
        btnLblGenre3.setText(selected.getGenre()[2]);

        selectedMovie = selected;
        if (selected.getPoster().getImage() == null) {
            selected.setPoster(App.class.getResource("images/posterError.png").toExternalForm());
        }

        imgViewMovPoster.setImage(selected.getPoster().getImage());
        movieRating.setRating(Double.parseDouble(Optional.ofNullable(selected.getPersonalRating()).orElse("0.0")));
    }


    public void onPlayMovie(ActionEvent event)
    {
        if (selectedMovie != null){
            //Parent root = null;
            try {
            Stage stage = new Stage();

                ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][]{
                            {"selectedMovie", selectedMovie}, {"playerStage", stage}};}};
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("Player.fxml")), resources);
            stage.setTitle("Player");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the selected movie. Please make sure a movie is selected.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }}
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a movie in the list below");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.show();
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

        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the movie creation panel.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
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
            ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][]{
                            {"selectedMovie", selectedMovie}};}};

            root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("EditMovie.fxml")), resources);
            Stage stage = new Stage();
            stage.setTitle("Edit Movie");
            stage.setMaxHeight(314);
            stage.setMinHeight(314);
            stage.setMaxWidth(353);
            stage.setMinWidth(353);
            stage.setScene(new Scene(root, 353, 314));
            stage.show();

        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the editing panel.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onEditGenre(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("EditGenreBar.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Edit Genre");
            stage.setMaxHeight(332);
            stage.setMinHeight(332);
            stage.setMaxWidth(378);
            stage.setMinWidth(378);
            stage.setScene(new Scene(root, 378, 332));
            stage.show();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load the editing panel.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
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

    public void onSearch(ActionEvent event) {

    }

    /**
     * Sets the searchbar's search entires for autocompletion to the input list of Strings.
     * Used for filter autocompletion - it's filled with a list of every unique value of the chosen parameter
     * in the database.
     * @param inputList
     */
    private <T> void initializeGenericSearchEntries(List<T> inputList){
        //txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
           // txtFieldSearch.getEntries().add((inputList.get(i).toString()));
        }
    }

    private void initializeStringSearchEntries(List<String> inputList){
       // txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
            //txtFieldSearch.getEntries().add((inputList.get(i)));
        }
    }
    public void onComboBox(ActionEvent event) {
        int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

        switch (ComboBoxEnum.values()[selectedItem])
        {
            case TITLE -> {
                //initializeStringSearchEntries();
                txtFieldSearch.setPromptText("Enter a title to filter");
            }
            case GENRE -> {
                //initializeStringSearchEntries();
                txtFieldSearch.setPromptText("Enter a genre to filter");
            }
            case RATING -> {
                //initializeStringSearchEntries();
                txtFieldSearch.setPromptText("Enter a numerical rating to filter");
            }
            case IMBDRATING -> {
                //initializeStringSearchEntries();
                txtFieldSearch.setPromptText("Enter a numerical, dot seperated score to filter");
            }
            default -> {
                //initializeStringSearchEntries();
                txtFieldSearch.setPromptText("Press enter to search");
            }
        }
    }

    public void onClearSearchFilter(ActionEvent event) {
        cmboBoxFilter.getSelectionModel().select(0);
        //Utility.bind(this.tblViewSongs, DataManager.selectedPlaylist().get().getSongs());
        txtFieldSearch.clear();
    }

    private void initializeComboBox(){
        cmboBoxFilter.setItems(FXCollections.observableArrayList("Search", "Title | Filter", "Genre | Filter", "Rating | Filter", "IMBD Rating | Filter"));
        cmboBoxFilter.getSelectionModel().select(0);
    }

    private void tblViewMovieContextMenu(){
        ContextMenu contextMenuMovie = new ContextMenu();
        tblViewMovies.setContextMenu(contextMenuMovie);
        //contextMenuMovie.setStyle("-fx-background-color: #404040; ");

        MenuItem play = new MenuItem("Play");
        play.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem newMovie = new MenuItem("New");
        newMovie.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem editMovie = new MenuItem("Edit");
        editMovie.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem deleteMovie = new MenuItem("Delete");
        deleteMovie.setStyle("-fx-text-fill: #d5d4d4;");

        play.setOnAction(event -> onPlayMovie(event));
        newMovie.setOnAction(event -> onNewMovie(event));
        editMovie.setOnAction(event -> onEditMovie(event));
        deleteMovie.setOnAction(event -> onDeleteMovie(event));

        contextMenuMovie.getItems().add(play);
        contextMenuMovie.getItems().add(newMovie);
        contextMenuMovie.getItems().add(editMovie);
        contextMenuMovie.getItems().add(deleteMovie);
    }

    private void lstViewGenreContextMenu(){
        ContextMenu contextMenuGenre = new ContextMenu();
        lstViewGenre.setContextMenu(contextMenuGenre);
        contextMenuGenre.setStyle("-fx-background-color: #404040; ");

        MenuItem edit = new MenuItem("Edit Genres");
        edit.setStyle("-fx-text-fill: #d5d4d4;");
        edit.setOnAction(event -> onEditGenre(event));
        contextMenuGenre.getItems().add(edit);

    }



}
