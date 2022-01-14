package easv.app.controllers;

import easv.app.App;
import easv.app.utils.customComponent.ComboBoxEnum;
import easv.app.be.FXMLProperties;
import easv.app.be.MovieModel;
import easv.app.bll.DataManager;
import easv.app.model.UserSearchModel;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

public class MovieManagerController extends FXMLProperties implements Initializable
{

    MovieModel selectedMovie;
    UserSearchModel searchModel;

    public MovieManagerController()
    {
        searchModel = new UserSearchModel();

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
            DataManager.getInstance().load();
        }
        catch (Exception e)
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

        if (selected == null)
        {
            return;
        }


        // simple text fields
        lblMovTitle.textProperty().set(selected.getTitle());
        lblMovActors.textProperty().set(selected.getActors());
        lblMovCountry.textProperty().set(selected.getCountry());
        lblMovDirector.textProperty().set(selected.getDirector());
        lblMovRating.textProperty().set(selected.getAgeRating());
        lblMovImbdRating.textProperty().set(selected.getImdbRating());
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

            selectedMovie.setLastViewed(LocalDate.now().toString());
            //dataManager.update(selectedMovie);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the selected movie. Please make sure a movie is selected.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }}
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a movie in the list below.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
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
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onDeleteMovie(ActionEvent event) {
        try
        {
            DataManager.getInstance().delete(selectedMovie);
            this.tblViewMovies.refresh();
            this.tblViewMovies.getSelectionModel().selectNext();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
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
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onMovieRated(MouseEvent mouseEvent) {
        int rating = (int) movieRating.getRating();

        var selected =  this.tblViewMovies.getSelectionModel().getSelectedItem();

        selected.setPersonalRating(rating + "");
        DataManager.getInstance().update(selected);
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
        this.tblClmImbdRating.setCellValueFactory(param -> param.getValue().imdbRatingProperty());
        this.tblClmPersonalRating.setCellValueFactory(param -> param.getValue().personalRatingProperty());
        this.tblClmLastViewed.setCellValueFactory(param -> param.getValue().lastViewedProperty());

        this.tblViewMovies.itemsProperty().bindBidirectional(DataManager.getInstance().getMovies());

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
        txtFieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent key)
            {
                if (key.getCode().equals(KeyCode.ENTER))
                {
                    int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

                    switch (ComboBoxEnum.values()[selectedItem])
                    {
                        case SEARCH_ALL -> {
                            searchModel.searchAll(tblViewMovies, txtFieldSearch.getText());
                        }
                        case TITLE ->{
                            searchModel.filterTitle(tblViewMovies, txtFieldSearch.getText());
                        }
                        case RATING -> {
                            searchModel.filterRating(tblViewMovies, txtFieldSearch.getText());
                        }
                        case IMBDRATING -> {
                            searchModel.filterImbdRating(tblViewMovies, txtFieldSearch.getText());
                        }
                        default -> {
                            searchModel.searchCurrent(tblViewMovies, txtFieldSearch.getText());
                        }
                    }
                }
            }
        });
    }

    private void initializeGenreBar(){
        try {
            lstViewGenre.setItems(FXCollections.observableArrayList(DataManager.getInstance().getAllGenres()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTableOnGenre(){
        try {
            tblViewMovies.setItems(DataManager.getInstance().getMoviesOfGenre(lstViewGenre.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Pull any use of TableView out of model.


    Add listView Functionality


    Add additional SQL statements for

	Retrieving All movies where "filtertype" like/greater than "input"
	Binding it to table
	Handle listview either locally, Select and compare, or have it reset filter.


            **??** Try to handle the data locally
     */
    private void updateOnFilter(ListProperty<MovieModel> activeMovies, ComboBoxEnum useCase){
        this.tblViewMovies.itemsProperty().bindBidirectional(activeMovies);

    }

    public void onComboBox(ActionEvent event) {
        int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

        switch (ComboBoxEnum.values()[selectedItem])
        {
            case SEARCH_ALL -> {
                try {
                    initializeGenericSearchEntries(DataManager.getInstance().getAllMovieTitles());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                txtFieldSearch.setPromptText("Press enter to search");
            }
            case TITLE -> {
                initializeStringSearchEntries(searchModel.getCurrentFilterEntries(tblViewMovies.getItems(), ComboBoxEnum.TITLE));
                txtFieldSearch.setPromptText("Enter a title to filter");
            }
            case RATING -> {
                initializeStringSearchEntries(searchModel.getCurrentFilterEntries(tblViewMovies.getItems(), ComboBoxEnum.RATING));
                txtFieldSearch.setPromptText("Enter a numerical rating to filter");
            }
            case IMBDRATING -> {
                initializeStringSearchEntries(searchModel.getCurrentFilterEntries(tblViewMovies.getItems(), ComboBoxEnum.IMBDRATING));
                txtFieldSearch.setPromptText("Enter a numerical, dot seperated score to filter");
            }
            default -> {
                initializeGenericSearchEntries(searchModel.getCurrentFilterEntries(tblViewMovies.getItems(), ComboBoxEnum.SEARCH));
                txtFieldSearch.setPromptText("Press enter to search");
            }
        }
    }



    /**
     * Sets the searchbar's search entires for autocompletion to the input list of Strings.
     * Used for filter autocompletion - it's filled with a list of every unique value of the chosen parameter
     * in the database.
     * @param inputList
     */
    private <T> void initializeGenericSearchEntries(List<T> inputList){
        txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
           txtFieldSearch.getEntries().add((inputList.get(i).toString()));
        }
    }
    private void initializeStringSearchEntries(List<String> inputList){
        txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
            txtFieldSearch.getEntries().add((inputList.get(i)));
        }
    }

    public void onClearSearchFilter(ActionEvent event) {
        cmboBoxFilter.getSelectionModel().select(0);
        //Utility.bind(this.tblViewSongs, DataManager.selectedPlaylist().get().getSongs());
        txtFieldSearch.clear();
    }

    private void initializeComboBox(){
        cmboBoxFilter.setItems(FXCollections.observableArrayList("Search", "Search All", "Title | Filter", "Rating | Filter", "IMBD Rating | Filter"));
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


    private void openImbdPage() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                URI selectedImbdPage = new URI("https://www.imdb.com/title/" + selectedMovie.getID() + "/");
                Desktop.getDesktop().browse(selectedImbdPage);
            } catch (IOException | URISyntaxException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the selected movie's IMBD page");
                alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }
    /**
     * Checks whether any MovieModel object has either not been viewed for two years or more, or has been rated 2/5 or lower.
     * Should any applicable movies exist, it displays an alert with ListViews and Labels packaged in VBoxes and an Hbox,
     * that contains said applicable movies.
     */
    private void alertUser(){
        ObservableList<MovieModel> badMovies = FXCollections.observableArrayList(DataManager.getInstance().sortBadMovies());
        ObservableList<MovieModel> oldMovies = FXCollections.observableArrayList(DataManager.getInstance().sortOldLastViewedMovies());
        if (!badMovies.isEmpty() || !oldMovies.isEmpty()){
            ObservableList<Node> oldListAndLabel = FXCollections.observableArrayList();
            ObservableList<Node> badListAndLabel = FXCollections.observableArrayList();
            HBox hbox = new HBox();
            if (!oldMovies.isEmpty()) {
                VBox vBox = new VBox();
                ListView<MovieModel> lstViewOld = new ListView<>(oldMovies);
                lstViewOld.setMaxHeight(200);
                oldListAndLabel.addAll(new Label("Movies not opened for two years"), lstViewOld);
                vBox.getChildren().addAll(oldListAndLabel);
                hbox.getChildren().add(vBox);
            }
            if (!badMovies.isEmpty()){
                VBox vBox = new VBox();
                ListView<MovieModel> lstViewBad = new ListView<>(badMovies);
                lstViewBad.setMaxHeight(200);
                badListAndLabel.addAll(new Label("Movies rated poorly by you"), lstViewBad);
                vBox.getChildren().addAll(badListAndLabel);
                hbox.getChildren().add(vBox);
            }
        }
    }

    public void onImbdScoreClicked(MouseEvent mouseEvent) {
        openImbdPage();
    }

    public void onImbdClicked(MouseEvent mouseEvent) {
        openImbdPage();
    }
}
