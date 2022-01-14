package easv.app.be;

import easv.app.Utils.CustomComponent.AutoCompleteTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;


public class FXMLProperties {

    @FXML public AutoCompleteTextField txtFieldSearch;
    @FXML public ComboBox cmboBoxFilter;

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
    @FXML public Label lblMovImbdRating;
    @FXML public ImageView imgViewMovPoster;

    @FXML public Button btnLblGenre1;
    @FXML public Button btnLblGenre2;
    @FXML public Button btnLblGenre3;
    @FXML public Rating movieRating;


    @FXML public ListView lstViewGenre;
    @FXML public Button btnGenreOptions;

    @FXML public TableView<MovieModel> tblViewMovies = new TableView<>();
    @FXML public TableColumn<MovieModel, ImageView> tblClmPoster = new TableColumn<>();
    @FXML public TableColumn<MovieModel, String> tblClmTitle = new TableColumn<>();
    @FXML public TableColumn<MovieModel, String> tblClmType = new TableColumn<>();
    @FXML public TableColumn<MovieModel, String> tblClmImbdRating = new TableColumn<>();
    @FXML public TableColumn<MovieModel, String> tblClmPersonalRating = new TableColumn<>();
    @FXML public TableColumn<MovieModel, String> tblClmLastViewed = new TableColumn<>();





}
