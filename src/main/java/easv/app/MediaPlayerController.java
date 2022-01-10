package easv.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import easv.app.be.Movie;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

//TODO: fix buttons, add gradient to userControlPane. If possible, add keyboard shortcut for play/pause
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
     * The playback started flag. Initialized to <i>false</i> locally.
     */
    private boolean playing;
    /**
     * The playback paused flag. Initialized to <i>false</i> locally.
     */
    //private boolean paused;
    /**
     * The media mute flag. Initialized to <i>false</i> locally.
     */
    private boolean muted;
    /**
     * The UI visibility flag. Initialized to <i>true</i> locally.
     */
    private boolean showUI;

    /**
     * The Timeline for use as a delay timer.
     */
    private Timeline timeLine;

    private Movie movie;
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
        this.playing = false;
        //this.paused = false;
        this.muted = false;
        this.showUI = true;

        try {
            if (resources.getObject("selectedMovie") != null) {
                movie = (Movie) resources.getObject("selectedMovie");
                thisStage = (Stage) resources.getObject("playerStage");
            }
        } catch (Exception e) {
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
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    private void initListeners(){
        progBar.setOnMouseClicked(progBarMouseListener());
        progBar.setOnMouseDragged(progBarMouseListener());

        userControls.setOnMouseEntered(uIMouseInOutListener());
        userControls.setOnMouseExited(uIMouseInOutListener());

        mediaPlayer.currentTimeProperty().addListener(progressChangedListener());
        rootPane.setOnMouseMoved(sceneMouseMovedListener());


        thisStage.widthProperty().addListener(sceneSizeChangedListener());
        thisStage.setOnCloseRequest(stopOnClose());
        thisStage.setOnHiding(stopOnClose());


        volSlider.setValue(0.5);
        volSlider.valueProperty().addListener(volumeSliderChangedListener());
    }

    /**
     * Handles the <i>Play/Pause</i> button click. Uses the flags <i>playing</i>
     * and <i>paused</i> to initiate, pause or resume media item playback. Sets
     * the <i>paused</i> flag by flipping it.
     */
    @FXML
    public void playRequestHandler(ActionEvent event) {
        if (!playing) {
            mediaPlayer.play();
            playBtn.setStyle("-fx-background-image: url('images/play2.png'); -fx-background-size: 20 20;");

        }
        //Otherwise, check the paused flag.
        else if (playing){
            mediaPlayer.pause();
            playBtn.setStyle("-fx-background-image: url('images/pause.png'); -fx-background-size: 16 16; -fx-background-position: 8;");

           /* //If not paused, pause.
            if (!paused) {
                playBtn.setStyle("-fx-background-image: url('images/play2.png'); -fx-background-size: 16 16; -fx-background-position: 8;");
                mediaPlayer.pause();
            }
            //Otherwise, resume.
            else {
                playBtn.setStyle("-fx-background-image: url('images/pause.png'); -fx-background-size: 16 16; -fx-background-position: 8;");
                mediaPlayer.play();
            }
            this.paused = !paused;

            */
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
                volBtn.setStyle("-fx-background-image: url('images/volmute.png');");
            }
            //Otherwise, demute.
            else
            {
                mediaPlayer.muteProperty().set(!muted);
                muted = !muted;
                volBtn.setStyle("-fx-graphic: url('images/volup.png');");
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
            public void changed(ObservableValue<? extends Number> obervable,
                                Number oldValue, Number newValue) {
                if(mediaPlayer != null)
                {
                    mediaPlayer.setMute(false);
                    muted = false;
                    mediaPlayer.setVolume(newValue.doubleValue());
                }
                if(newValue.doubleValue() > 0.6)
                {
                    volBtn.setStyle("-fx-graphic: url('images/volup.png'); -fx-padding: 2 4 2 4;");
                }
                else if(newValue.doubleValue() == 0) //TODO: MUTE
                {
                    volBtn.setStyle("-fx-graphic: url('images/volup.png'); -fx-padding: 2 4 2 4;");
                }
                else
                {
                    volBtn.setStyle("-fx-graphic: url(images/voldown.png); -fx-padding: 2 4 2 4;");
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
     *
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

    protected EventHandler<? super KeyEvent> pauseOnEnter(){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.SPACE)) {
                    if (!playing) {
                        mediaPlayer.play();
                    }
                    if (playing){
                        mediaPlayer.stop();
                    }
                    }
                    /*if (!playing) {
                        mediaPlayer.play();
                    }
                    if (playing){
                        mediaPlayer.stop();
                    }
                    //Otherwise, check the paused flag.
                    else {
                        //If not paused, pause.
                        if (!paused) {
                            playBtn.setStyle("-fx-background-image: url('images/play2.png'); -fx-background-size: 20 20;");
                            mediaPlayer.pause();
                        }
                        //Otherwise, resume.
                        else {
                            playBtn.setStyle("-fx-background-image: url('images/pause.png'); -fx-background-size: 20 20;");
                            mediaPlayer.play();
                        }
                        paused = !paused;


                }*/
            }

        };
    }
}