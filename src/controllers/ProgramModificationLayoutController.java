package controllers;

import helpers.DatabaseHelper;
import interfaces.IProgram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import models.Denomination;
import models.ProgramPhase;
import models.ProgramType;
import org.controlsfx.control.Rating;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramModificationLayoutController  implements Initializable {

    @FXML
    private TextField title = new TextField();

    @FXML
    private ComboBox<ProgramType> programType = new ComboBox<>();

    @FXML
    private ComboBox<String> channel = new ComboBox<>();

    @FXML
    private DateTimePicker airDate = new DateTimePicker();

    @FXML
    private TextField previewImage = new TextField();

    @FXML
    private TextField programSource = new TextField();

    @FXML
    private TextField length = new TextField();

    @FXML
    private TextField duration = new TextField();

    @FXML
    private ComboBox<ProgramPhase> programPhase = new ComboBox<>();

    @FXML
    private CheckBox closedCaption = new CheckBox();

    @FXML
    private TextField actorFname = new TextField();

    @FXML
    private TextField actorLname = new TextField();

    @FXML
    private TextField actorAge = new TextField();

    @FXML
    private DateTimePicker grammyDate = new DateTimePicker();

    @FXML
    private Button addActor = new Button();

    @FXML
    private Rating movieRating = new Rating();

    @FXML
    private DateTimePicker releaseDate = new DateTimePicker();

    @FXML
    private ComboBox<Denomination> denomination = new ComboBox<>();

    @FXML
    private TextField rangeMin = new TextField();

    @FXML
    private TextField rangeMax = new TextField();

    @FXML
    private Rating weatherRating = new Rating();

    @FXML
    private Button saveProgram = new Button();

    @FXML
    private Button deleteProgram = new Button();

    @FXML
    private Text errorText = new Text();

    private IProgram program = ProgramsLayoutController.currentlyModifiedProgram;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupComboBoxes();

        configureSaveProgram();

        errorText.setText("Hello Worlds");

        channel.onActionProperty().set(e -> {

        });


    }

    public void setupComboBoxes(){

        programType.getItems().addAll(ProgramType.values());

        channel.getItems().addAll(DatabaseHelper.getChannels());

        programPhase.getItems().addAll(ProgramPhase.values());

        denomination.getItems().addAll(Denomination.values());
    }

    public void setupModifiedProgramValues(){

        if(program != null){

        }
    }

    public void setProgramValues(){

    }

    public void configureSaveProgram(){
        saveProgram.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                try{
                    if(program != null){
                        DatabaseHelper.addOrUpdateProgram(program);
                    }else {
                        errorText.setText("Please set up the necessary values for this program.");
                    }
                }catch (Exception ex){
                    errorText.setText(ex.getMessage());
                }
            }
        });
    }

}
