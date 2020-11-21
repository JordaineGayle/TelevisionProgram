package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramsLayoutController implements Initializable {

    private MediaView mediaView;

    @FXML
    private BorderPane testPane = new BorderPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        Media media = new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
//
//        MediaPlayer player = new MediaPlayer(media);
//
//        player.setAutoPlay(true);
//
//        player.getCurrentTime().toSeconds();
//
//        mediaView = new MediaView(player);
//
//        mediaView.setPreserveRatio(true);
//
//        testPane.setCenter(mediaView);
    }
}
