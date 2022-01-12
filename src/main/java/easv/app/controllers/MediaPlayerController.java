package easv.app.controllers;

import easv.app.App;
import easv.app.be.MovieModel;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO: fix buttons, add gradient to userControlPane
/**
 * The Controller for the MediaPlayerView. Contains the UI functionality and
 * media playback logic.
 *
 * @author Alex Hage, Rasmus Sandbæk
 *
 */
public class MediaPlayerController implements Initializable {

    private static final int HIDE_UI_TIMEOUT = 2500;
    private static final boolean SHOW_UI = true;
    private static final boolean HIDE_UI = false;

    @FXML public MediaView mediaView;

    @FXML public ProgressBar progBar;

    @FXML public Button playBtn;

    @FXML public Button fScreenBtn;

    @FXML public Button volBtn;

    @FXML public Label timeLabel;

    @FXML public Slider volSlider;

    @FXML public AnchorPane userControls;

    @FXML public AnchorPane rootPane;


    private Stage thisStage;

    /**
     * The reusable MediaPlayer.
     */
    private MediaPlayer mediaPlayer;
    /**
     * The reusable Media.
     */
    private Media media;
    /**
     * The UI visibility flag. Initialized to <i>true</i> locally.
     */
    private boolean showUI;
    /**
     * The Timeline for use as a delay timer.
     */
    private Timeline timeLine;
    /**
     * The media mute flag. Initialized to <i>false</i> locally.
     */
    private boolean muted;

    private MovieModel movie;
    /**
     * The default constructor.
     * Called before the <i>initialize()</i> method.
     */
    public MediaPlayerController() {
    }

    /**
     * Initializes the controller class. Sets the controller-wise flags, UI
     * tooltips and default values. Is automatically called after the fxml file
     * has been loaded.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        this.showUI = true;
        this.muted = false;
        try {
            resources.getObject("selectedMovie");
            movie = (MovieModel) resources.getObject("selectedMovie");
            thisStage = (Stage) resources.getObject("playerStage");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the selected movie.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        }
        initMedia();
        initListeners();
        mediaPlayer.play();
    }

    private void initMedia(){
        File file = new File(movie.getPath());
        try {
            media = new Media(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program could not load the selected movie's file.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            e.printStackTrace();
        } catch (MediaException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unsupported file format. Please choose a different movie.");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();

        }
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.muteProperty().set(false);
        userControls.setStyle("-fx-background-color: linear-gradient(to top, #212833, transparent);");
    }

    private void initListeners(){
        progBar.setOnMouseClicked(progBarMouseListener());
        progBar.setOnMouseDragged(progBarMouseListener());

        userControls.setOnMouseEntered(uIMouseInOutListener());
        userControls.setOnMouseExited(uIMouseInOutListener());

        mediaPlayer.currentTimeProperty().addListener(progressChangedListener());
        rootPane.setOnMouseMoved(sceneMouseMovedListener());

        mediaView.setOnMouseClicked(pauseOnClick());

        thisStage.widthProperty().addListener(sceneSizeChangedListener());
        thisStage.setOnCloseRequest(stopOnClose());
        thisStage.setOnHiding(stopOnClose());

        volSlider.setValue(1);
        volSlider.valueProperty().addListener(volumeSliderChangedListener());
    }

    /**
     * Handles the <i>Play/Pause</i> button click.
     */
    @FXML
    public void playRequestHandler(ActionEvent event) {
        if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.play();
            String pauseImg = App.class.getResource("images/pause.png").toExternalForm();
            playBtn.setStyle("-fx-background-image: url('"+ pauseImg +"'); -fx-background-size: 16 16; -fx-background-position: 7.5");

        }
        else {
            mediaPlayer.pause();
            String playImg = App.class.getResource("images/play2.png").toExternalForm();
            playBtn.setStyle("-fx-background-image: url('"+ playImg +"'); -fx-background-size: 16 16; -fx-background-position: 8;");
        }
    }

    /**
     * Handles the <i>Fullscreen</i> button click. Each click reverses the
     * current fullscreen status of the primary stage.
     */
    @FXML
    public void fullScreenRequestHandler(ActionEvent event)
    {
        thisStage.setFullScreen(!thisStage.isFullScreen());
    }

    /**
     * Handles the <i>Mute</i> button click. Each click reverses the current
     * status of MediaPlayer's muteProperty.
     */
    @FXML
    public void muteRequestHandler(ActionEvent event)
    {
        //If not initialized, nothing happens. Otherwise,
        if(mediaPlayer != null)
        {
            //If not currently muted, then mute.
            if(!muted)
            {
                mediaPlayer.muteProperty().set(!muted);
                muted = !muted;
                String muteImg = App.class.getResource("images/volmute.png").toExternalForm();
                volBtn.setStyle("-fx-background-image: url('"+ muteImg +"');");
            }
            //Otherwise, demute.
            else
            {
                mediaPlayer.muteProperty().set(!muted);
                muted = !muted;
                String volImg = App.class.getResource("images/volup.png").toExternalForm();
                volBtn.setStyle("-fx-background-image: url('"+ volImg +"');");
                //Set the appropriate volume button icon on return, based on current volume.
            }
        }
    }

    /**
     * Shows/hides the user interface based on a boolean value. Uses
     * FadeTransition to fade in/out and TimeLine to delay fade out.
     *
     * @param show
     *            the boolean. True fades in controls, false fades out.
     */
    private void toggleUI(boolean show)
    {
            if (show)
            {
                showUI = SHOW_UI;
                FadeTransition fadeTransition = new FadeTransition(
                        Duration.millis(200), userControls);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
            else
            {
                timeLine = new Timeline(new KeyFrame(
                        Duration.millis(HIDE_UI_TIMEOUT), event ->
                {
                    FadeTransition fadeTransition = new FadeTransition(
                            Duration.millis(500), userControls);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();
                    thisStage.getScene().setCursor(Cursor.NONE);
                    showUI = HIDE_UI;
                }));
                timeLine.play();
            }

    }


    /**
     * Listens to changes in Scene size. On change, assigns new values to
     * MediaView's FitWidth property, thus resizing the viewport.
     *
     * @return {@code ChangeListener<Number>}
     */
    private ChangeListener<Number> sceneSizeChangedListener() {
        return new ChangeListener<Number>()
        {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldSceneWidth, Number newSceneWidth)
            {
                if(mediaView != null)
                {
                    mediaView.setFitWidth(newSceneWidth.doubleValue());
                }
            }
        };
    }

    /**
     * Listens to changes in volume Slider position. On change, assigns new
     * values to MediaPlayer's volume property. Cancels mute status and sets the
     * mute flag to <i>false</i>.
     *
     * @return {@code ChangeListener<Number>}
     */
    private ChangeListener<Number> volumeSliderChangedListener() {
        return new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if(mediaPlayer != null)
                {
                    mediaPlayer.setMute(false);
                    mediaPlayer.setVolume(newValue.doubleValue());
                }

                if(newValue.doubleValue() == 0)
                {
                    String muteImg = App.class.getResource("images/volmute.png").toExternalForm();
                    volBtn.setStyle("-fx-background-image: url('"+ muteImg +"');");
                }
                else
                {
                    String volImg = App.class.getResource("images/volup.png").toExternalForm();
                    volBtn.setStyle("-fx-background-image: url('"+ volImg +"');");
                }
            }
        };
    }

    /**
     * Listens to changes in media playback progress. On change, sets the
     * progress bar and the progress clock accordingly.
     *
     * @return {@code ChangeListener<Duration>}
     */
    private ChangeListener<Duration> progressChangedListener()
    {
        ChangeListener<Duration> progressChangeListener = new ChangeListener<Duration>()
        {
            @Override
            public void changed(
                    ObservableValue<? extends Duration> observableValue,
                    Duration oldValue, Duration newValue)
            {
                progBar.setProgress(1.0
                        * mediaPlayer.getCurrentTime().toMillis()
                        / mediaPlayer.getTotalDuration().toMillis());

                timeLabel.setText(convertTimeInSeconds((int)newValue.toSeconds()));
            }
        };
        return progressChangeListener;
    }

    public static String convertTimeInSeconds(int inTime)
    {
        String outTime = "";

        int hours = (inTime / 3600) % 24;
        int minutes = (inTime / 60) % 60;
        int seconds = inTime % 60;

        outTime += (hours < 10 ? "0" + hours : hours) + ":"
                + (minutes < 10 ? "0" + minutes : minutes) + ":"
                + (seconds < 10 ? "0" + seconds : seconds);

        return outTime;
    }

    /**
     * Listens for left mouse button click or drag action on the progress bar. Reacts
     * by updating the media position index.
     *
     * @return {@code EventHandler<MouseEvent>}
     */
    private EventHandler<MouseEvent> progBarMouseListener()
    {
        return new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(mediaPlayer == null)
                {
                    event.consume();
                }
                else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED
                        || event.getEventType() == MouseEvent.MOUSE_CLICKED)
                {
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(
                            event.getX() / progBar.getWidth()));
                }
            }
        };
    }

    /**
     * Listens for mouse entering and exiting the UI container. Toggles UI
     * visibility accordingly using <i>toggleUI()</i>
     *
     * @return {@code EventHandler<MouseEvent>}
     */
    private EventHandler<MouseEvent> uIMouseInOutListener() {
        return new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(mediaPlayer == null)
                {
                    event.consume();
                }
                else if(event.getEventType() == MouseEvent.MOUSE_ENTERED)
                {
                    if(timeLine != null)
                    {
                        timeLine.stop();
                    }
                    if(!showUI)
                    {
                        toggleUI(SHOW_UI);
                    }
                }
                else if(event.getEventType() == MouseEvent.MOUSE_EXITED)
                {
                    if(showUI)
                    {
                        toggleUI(HIDE_UI);
                    }
                }
            }

        };
    }


    /**
     * Listens for mouse movement within the scene. Sets default Cursor on movement.
     * @return {@code EventHandler<MouseEvent>}
     */
    private EventHandler<? super MouseEvent> sceneMouseMovedListener() {
        return new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                Stage stage = thisStage;
                if(stage.getScene().getCursor() != Cursor.DEFAULT)
                {
                    stage.getScene().setCursor(Cursor.DEFAULT);
                }
            }
        };
    }

    private EventHandler<WindowEvent> stopOnClose(){
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mediaPlayer.stop();
            }
        };
    }

    /**
     *
     * @return EventHandler for pausing when the mediaview is clicked.
     */
    protected EventHandler<? super MouseEvent> pauseOnClick(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    mediaPlayer.play();
                    String pauseImg = App.class.getResource("images/pause.png").toExternalForm();
                    playBtn.setStyle("-fx-background-image: url('"+ pauseImg +"'); -fx-background-size: 16 16; -fx-background-position: 7.5");

                }
                else {
                    mediaPlayer.pause();
                    String playImg = App.class.getResource("images/play2.png").toExternalForm();
                    playBtn.setStyle("-fx-background-image: url('"+ playImg +"'); -fx-background-size: 16 16; -fx-background-position: 8;");
            }   }
        };
    }

}