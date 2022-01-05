package easv.app.be;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
    

    @FXML public ListView lstViewGenre;
    @FXML public Button btnGenreOptions;

    @FXML public TableView tblViewMovies;
    @FXML public TableColumn tblClmPoster;
    @FXML public TableColumn tblClmTitle;
    @FXML public TableColumn tblClmYear;
    @FXML public TableColumn tblClmImbdRating;
    @FXML public TableColumn tblClmPersonalRating;
    @FXML public TableColumn tblClmLastViewed;


}
