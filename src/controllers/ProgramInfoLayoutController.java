/**
 * Give detailed information about the a program.
 * **/

package controllers;

import helpers.SceneBuilder;
import helpers.TimeHelper;
import interfaces.IProgram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProgramInfoLayoutController implements Initializable {

    private IProgram program = ChannelListingsLayoutController.getCurrentViewingNowProgram();

    @FXML
    ImageView image = new ImageView();

    @FXML
    AnchorPane apane = new AnchorPane();

    @FXML
    private Label title = new Label();

    @FXML
    private Label length = new Label();

    @FXML
    private Label airdate = new Label();

    @FXML
    private Label duration = new Label();

    @FXML
    private Label closeedCaption = new Label();

    @FXML
    private Label programType = new Label();

    @FXML
    private Label denomination = new Label();

    @FXML
    private Label ageRange = new Label();

    @FXML
    private Label rating = new Label();

    @FXML
    private Label severityRating = new Label();

    @FXML
    private Label releasedDate = new Label();

    @FXML
    private Label actors = new Label();

    @FXML
    private Label description = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpLabels();
    }

    public void setUpLabels(){

        new Thread(){
            @Override
            public void run() {
                try{
                    image.setImage(new Image(program.getImage()));
                }catch (Exception e){}
            }
        }.start();

        title.setText(program.getTitle());
        length.setText(program.getLength()+" hrs");
        airdate.setText(TimeHelper.convertToHumanReadableFormat(program.getProgramAirDateTime()));
        duration.setText(program.getDuration()+" days");
        closeedCaption.setText(String.valueOf(program.isClosedCaption()));
        programType.setText(program.getProgramType());
        denomination.setText("N/A");
        actors.setText("N/A");
        ageRange.setText("N/A");
        severityRating.setText("N/A");
        releasedDate.setText("N/A");

        try{
            description.setText(program.getShortDescription());
        }catch (Exception e){}

        try{
            denomination.setText(program.getDenomination().name());
        }catch (Exception e){}

        try{
            ageRange.setText(program.getAgeRange().getMin() +" - "+program.getAgeRange().getMax());
        }catch (Exception e){}

        rating.setText(String.valueOf(program.getRating())+"/8");

        try{
            releasedDate.setText(TimeHelper.convertToHumanReadableFormat(program.getDateReleased()));
        }catch (Exception e){
            releasedDate.setText("unknown");
        }

        try{
            String actorz = String.join(",",program.getActors().stream().map(e -> e.getFirstName()+" "+e.getLastName()).collect(Collectors.toList()));

            actors.setText(actorz);
        }catch (Exception e){
            actors.setText("unknown");
        }

        severityRating.setText(String.valueOf(program.getSeverityRating())+"/5");

    }


}
