package easv.app.controllers;

import easv.app.App;
import easv.app.be.MovieModel;
import easv.app.be.SearchedMovieModel;
import easv.app.bll.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CrudController implements Initializable {

    //NewMovie
    @FXML public TextField txtFieldSearchNewMovie;
    @FXML public Button btnSearchNewMovie;
    @FXML public ListView<SearchedMovieModel> lstViewNewMoviePick;
    @FXML public Button btnPickFile;
    @FXML public TextField txtFieldPickFile;
    @FXML public Label lblNewMovieTitle;
    @FXML public ImageView imgViewPosterNewMovie;
    @FXML public Button btnNewMovieFinish;

    //EditMovie
    @FXML public TextField txtFieldEditMovieTitle;
    @FXML public SearchableComboBox cmboBoxEditGenre1;
    @FXML public SearchableComboBox cmboBoxEditGenre2;
    @FXML public SearchableComboBox cmboBoxEditGenre3;
    @FXML public Button btnEditMovieFinish;

    //EditGenre
    @FXML public ListView<String> lstViewEditGenre;
    @FXML public TextField txtFieldEditGenreName;
    @FXML public Button btnNewGenre;
    @FXML public Button btnDeleteGenre;
    @FXML public Button btnEditGenreFinish;

    MovieModel movie;
    DataManager dataManager = new DataManager();

    public CrudController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.txtFieldSearchNewMovie.setText("avengers");
        //onSearchNewMovieTitles(new ActionEvent());
        initNewMovie();
        initEditGenre();
        initEditMovie(resources);
    }

    //NewMovie
    private void initNewMovie(){
        if (btnNewMovieFinish != null){ //If the button exists, NewMovie.fxml is open and the appropriate values are initialized.
            imgViewPosterNewMovie.setPreserveRatio(true);
            lstViewNewMoviePick.getSelectionModel().selectedItemProperty().addListener(observable -> updateNewMovieBindings());
        }
    }

    private void updateNewMovieBindings(){
        var selected = this.lstViewNewMoviePick.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblNewMovieTitle.textProperty().set(selected.getTitle());
            if (selected.getImageURL() == null || selected.getImageURL().equals("N/A")) {
                imgViewPosterNewMovie.setImage(new Image(App.class.getResource("images/posterError.png").toExternalForm()));
            } else {
                imgViewPosterNewMovie.setImage(new Image(selected.getImageURL()));
            }
        }
    }

    public void onSearchNewMovieTitles(ActionEvent event)
    {
        try {
            var search = DataManager.searchMovies(this.txtFieldSearchNewMovie.getText());
            lstViewNewMoviePick.setItems(FXCollections.observableArrayList(search.getMovies()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPickFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile =  fc.showOpenDialog(new Stage());
        if (!checkForMp4(selectedFile.getName())){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsupported file format. Please choose a different movie.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            return;
        }
        txtFieldPickFile.setText(selectedFile.getAbsolutePath());

    }

    public void onNewMovieDone(ActionEvent event)
    {
        SearchedMovieModel newMovie = lstViewNewMoviePick.getSelectionModel().getSelectedItem();

        if (newMovie != null)
        {
            try
            {
                DataManager.getInstance().add(txtFieldPickFile.getText(), newMovie.getImdbID());
            }
            catch (SQLException | IOException e)
            {
                e.printStackTrace();
            }
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Checks the characters of a filename after the "." indicating it's filetype.
     * @param fileName String value of a Path object.
     * @return true if the file has an .mp4 file extension, the only supported filetype by JavaFX MediaPlayer.
     */
    private Boolean checkForMp4(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension.equals("mp4");
    }

    //EditGenreBar
    private void initEditGenre(){
        if (btnEditGenreFinish != null){ //If the button exists, EditGenreBar.fxml is open and the appropriate values are initialized.

            lstViewEditGenre.setItems(dataManager.getAllGenres);
            lstViewEditGenre.getSelectionModel().selectedItemProperty().addListener(observable -> updateGenreBindings());
        }
    }

    private void updateGenreBindings(){
        var selected = lstViewEditGenre.getSelectionModel().getSelectedItem();
        if (selected != null){
            txtFieldEditGenreName.setText(selected);
        }
    }

    public void onDeleteGenre(ActionEvent event) {
        //dataManager.deleteGenre();
    }

    /**
     * Adds a blank "New Genre" for the user to edit.
     * @param event
     */
    public void onNewGenre(ActionEvent event) {
        //dataManager.addGenre();
    }

    public void onSaveEditGenre(ActionEvent event) {
        var selected = lstViewEditGenre.getSelectionModel().getSelectedItem();
        if (!selected.equals(txtFieldEditGenreName.getText())){
            //dataManager.updateGenre(selected, txtFieldEditGenreName.getText());
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    private void initEditMovie(ResourceBundle resources) {
        if (btnEditMovieFinish != null) { //If the button exists, EditMovie.fxml is open and the appropriate values are initialized.
            try {
                resources.getObject("selectedMovie");
                movie = (MovieModel) resources.getObject("selectedMovie");

                ObservableList<String> genres = FXCollections.observableArrayList(dataManager.getAllGenres());
                cmboBoxEditGenre1.setItems(genres);
                cmboBoxEditGenre2.setItems(genres);
                cmboBoxEditGenre3.setItems(genres);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the selected movie.");
                alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
                alert.showAndWait();
                e.printStackTrace();
            }


            txtFieldEditMovieTitle.setText(movie.getTitle());

        }
    }

    //EditMovie
    public void onEditMovieSave(ActionEvent event) {
       /* if (!cmboBoxEditGenre1.getSelectionModel().getSelectedItem().equals(movie.getGenre()[1]) ||
                !cmboBoxEditGenre2.getSelectionModel().getSelectedItem().equals(movie.getGenre()[2]) ||
                !cmboBoxEditGenre3.getSelectionModel().getSelectedItem().equals(movie.getGenre()[3]) )
        {
            if (!txtFieldEditMovieTitle.getText().equals(movie.getTitle())){
                movie.setTitle(txtFieldEditMovieTitle.getText());
            }
            dataManager.update(movie);
        }
        */

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
