package controllers;

import helpers.DatabaseHelper;
import helpers.ScenesHelper;
import interfaces.IProgram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramsLayoutController implements Initializable {

    @FXML
    private BorderPane testPane = new BorderPane();

    @FXML
    private TilePane programsTile = new TilePane();

    @FXML
    private Button addProgram = new Button();

    public static IProgram currentlyModifiedProgram;

    private List<IProgram> programs = DatabaseHelper.getPrograms();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpAddProgramButton();
    }

    private void setUpAddProgramButton(){
        addProgram.setOnMouseClicked(e -> {
            ScenesHelper.InvokeProgramModification(new Stage());
        });
    }
}
