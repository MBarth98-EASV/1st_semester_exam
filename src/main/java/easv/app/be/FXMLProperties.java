package easv.app.be;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;


public class FXMLProperties {

    @FXML public Button btnPlayMovie;

    @FXML public Label lblMovTitle;
    @FXML public Label lblMovYear;
    @FXML public Label lblMovRating;
    @FXML public Label lblMovRuntime;
    @FXML public TextArea txtAreaMovPlot;
    @FXML public Label lblMovDirector;
    @FXML public Label lblMovWriters;
    @FXML public Label lblMovActors;
    @FXML public Label lblMovCountry;
    @FXML public Label lblMovLanguage;
    @FXML public Label lblMovImbdRating;
    @FXML public ImageView imgViewMovPoster;

    @FXML public Button btnLblGenre1;
    @FXML public Button btnLblGenre2;
    @FXML public Button btnLblGenre3;
    @FXML public Rating movieRating;


    @FXML public ListView lstViewGenre;
    @FXML public Button btnGenreOptions;

    @FXML public TableView<Movie> tblViewMovies;
    @FXML public TableColumn tblClmPoster;
    @FXML public TableColumn tblClmTitle;
    @FXML public TableColumn tblClmYear;
    @FXML public TableColumn tblClmImbdRating;
    @FXML public TableColumn tblClmPersonalRating;
    @FXML public TableColumn tblClmLastViewed;


    //NewMovie
    @FXML public TextField txtFieldSearchNewMovie;
    @FXML public Button btnSearchNewMovie;
    @FXML public ListView lstViewNewMoviePick;
    @FXML public Button btnPickFile;
    @FXML public TextField txtFieldPickFile;
    @FXML public Label lblNewMovieTitle;
    @FXML public ImageView imgViewPosterNewMovie;
    @FXML public Button btnNewMovieFinish;

    //EditMovie
    @FXML public TextField txtFieldEditMovieTitle;
    @FXML public ComboBox cmboBoxEditGenre1;
    @FXML public ComboBox cmboBoxEditGenre2;
    @FXML public ComboBox cmboBoxEditGenre3;
    @FXML public Button btnEditMovieFinish;

    //EditGenre
    @FXML public ListView lstViewEditGenre;
    @FXML public TextField txtFieldEditGenreName;
    @FXML public Button btnNewGenre;
    @FXML public Button btnDeleteGenre;
    @FXML public Button btnEditGenreFinish;


}
