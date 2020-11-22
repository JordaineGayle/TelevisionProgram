package controllers;

import helpers.SceneBuilder;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Program;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerLayoutController implements Initializable {

    private Program currentProgram = ChannelListingsLayoutController.getCurrentViewingNowProgram();

    //private Media media = new Media(getClass().getResource("/assets/vid1.mp4").toExternalForm()); //Media(currentProgram.getSource());
    private Media media = new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"); //Media(currentProgram.getSource());

    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    private MediaView mediaView = new MediaView();

    private DoubleProperty progress;

    private StringProperty mediaDuration;

    private Stage stage = SceneBuilder.getStages().get("MediaPlayerLayout.fxml");

    private boolean isPlaying = false;

    @FXML
    private BorderPane playerPane = new BorderPane();

    @FXML
    private Slider trackBar = new Slider();

    @FXML
    private Button playBtn = new Button();

    @FXML
    private Button pauseBtn = new Button();

    @FXML
    private Button stopBtn = new Button();

    @FXML
    private Button exitBtn = new Button();

    @FXML
    private Slider volumeSlider = new Slider();

    @FXML
    private ProgressBar progressBar = new ProgressBar();

    @FXML
    private Label mediaTime = new Label();

    @FXML
    private Label mediaChaningTime = new Label();

    @FXML
    private Label mediaStat = new Label();

    @FXML
    private Label mediaTitle = new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        volumeSlider.valueProperty().bindBidirectional(mediaPlayer.volumeProperty());

        progressBar.setProgress(0);

        progress = new SimpleDoubleProperty() {};

        progressBar.progressProperty().bindBidirectional(progress);

        mediaTitle.setText("Now Playing: "+currentProgram.getTitle().toUpperCase()+" - Channel: "+currentProgram.getChannelName().toUpperCase());

        setUpMediaPlayerAndControls();

        setupMediaView();

        stage.setOnCloseRequest(e -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        });
    }

    private void setUpMediaPlayerAndControls(){

        mediaPlayer.setVolume(0.5);

        trackBar.setMin(0);

        playBtn.setOnMouseClicked(e->{
            mediaPlayer.play();
        });

        pauseBtn.setOnMouseClicked(e->{
            mediaPlayer.pause();
        });

        stopBtn.setOnMouseClicked(e->{
            mediaPlayer.stop();
        });

        exitBtn.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            mediaPlayer.dispose();
            stage.close();
        });

        mediaPlayer.statusProperty().addListener((e,o,n)->{

            if(n.equals(MediaPlayer.Status.READY)){

                playerPane.setCenter(mediaView);

                String totalTimeFormmated = DurationFormatUtils.formatDuration((long)mediaPlayer.getTotalDuration().toMillis(),"H:mm:ss", true);

                mediaTime.setText(totalTimeFormmated);

                progressBar.setOnMouseClicked(m->{
                    if(m.getButton().equals(MouseButton.PRIMARY)){

                        mediaPlayer.pause();

                        double xPosition = m.getX();

                        double pWidth = progressBar.getWidth();

                        double progression = xPosition / pWidth;

                        long percentMilliSec = (long)(progression * mediaPlayer.getTotalDuration().toMillis());

                        Duration duration = new Duration(percentMilliSec);

                        mediaPlayer.seek(duration);

                        mediaPlayer.play();
                    }
                });

                mediaPlayer.play();
            }

            if(n.equals(MediaPlayer.Status.PLAYING)){
                playerPane.setCenter(mediaView);
                isPlaying = true;
            }else{
                isPlaying = false;
            }

            mediaStat.setText(n.name());
        });

        mediaPlayer.currentTimeProperty().addListener((e,o,n)->{

            String currentTimeFormatted = DurationFormatUtils.formatDuration((long)n.toMillis(),"H:mm:ss", true);

            if(n.toMillis() == 0.0){
                progress.setValue(0);
            }else{
                progress.setValue(n.toMillis()/mediaPlayer.getTotalDuration().toMillis());
            }

            mediaChaningTime.setText(currentTimeFormatted);
        });

        mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
                playerPane.setCenter(setMessageOnMedia("There seems to be some issue playing this channel, please try again later."));
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playerPane.setCenter(setMessageOnMedia("Thanks for watching."));
                mediaPlayer.stop();
            }
        });

    }

    private void setupMediaView(){

        mediaView.setPreserveRatio(true);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setSmooth(true);

        mediaView.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(isPlaying){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.play();
                }
            }
        });

        playerPane.setCenter(setMessageOnMedia("Please wait processing video stream...."));
    }

    private Label setMessageOnMedia(String message){
        Label messageNode = new Label();

        messageNode.setText(message);
        messageNode.setTextFill(Paint.valueOf("white"));
        messageNode.setFont(Font.font("Lucida Handwriting",25));
        messageNode.setPadding(new Insets(10,10,10,10));
        //messageNode.setStyle("-fx-border-width: 2;-fx-border-color:white;");

        return messageNode;
    }
}
