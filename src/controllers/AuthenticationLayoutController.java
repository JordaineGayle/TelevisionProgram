package controllers;

import helpers.AuthenticationHelper;
import helpers.SceneBuilder;
import helpers.ScenesHelper;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationLayoutController implements Initializable {

    @FXML
    private TextField seq1  = new TextField();

    @FXML
    private TextField seq2 = new TextField();

    @FXML
    private TextField seq3 = new TextField();

    @FXML
    private TextField seq4 = new TextField();

    @FXML
    private Button authenticateBtn = new Button();

    private Stage stage = SceneBuilder.getStages().get("AuthenticationLayout.fxml");

    private String pass[] = new String[] {"0","0","0","0"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        seq1.textProperty().addListener((c,o,n) -> {
            try{
                pass[0] = n;
            }catch (Exception e){}
        });

        seq2.textProperty().addListener((c,o,n) -> {
            try{
                pass[1] = n;
            }catch (Exception e){}
        });

        seq3.textProperty().addListener((c,o,n) -> {
            try{
                pass[2] = n;
            }catch (Exception e){}
        });

        seq4.textProperty().addListener((c,o,n) -> {
            try{
                pass[3] = n;
            }catch (Exception e){}
        });

        authenticateUser();
    }

    public void authenticateUser(){
        authenticateBtn.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                String message = "Failed to authenticate user.";


                try{
                    String password = String.join("",pass);
                    boolean authenticated =  AuthenticationHelper.authenticateUser(Integer.parseInt(password));

                    if(authenticated){
                        message = "Login was successful, please wait...\nyou can proceed to the programs listing.";
                        ScenesHelper.createPopup("AuthenticationLayout.fxml",message,null);
                        PauseTransition delay = new PauseTransition(Duration.seconds(5));
                        delay.setOnFinished(t -> stage.close());
                        delay.play();
                        return;
                    }
                }catch (Exception ex){}
                ScenesHelper.createPopup("AuthenticationLayout.fxml",message,null);
            }
        });
    }
}
