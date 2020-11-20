package controllers;

import helpers.SceneBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private Button dashboardBtn = new Button();

    @FXML
    private Button programBtn = new Button();

    @FXML
    private Button channelListBtn = new Button();

    @FXML
    private Button fullScreenBtn = new Button();

    @FXML
    private Button exitBtn = new Button();

    @FXML
    private BorderPane mainPane = new BorderPane();

    private boolean fullScreen = true;

    private Stage mainStage = SceneBuilder.getStages().get("MainLayout.fxml");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.setCenter(paneBuilder("DashboardLayout.fxml"));
        System.out.println();
    }

    @FXML
    void viewDashboard(ActionEvent event) {
        handleBtnToggle(dashboardBtn, new Button[]{channelListBtn,programBtn});
        mainPane.setCenter(paneBuilder("DashboardLayout.fxml"));
    }

    @FXML
    void viewListings(ActionEvent event) {
        handleBtnToggle(channelListBtn, new Button[]{dashboardBtn,programBtn});
        mainPane.setCenter(paneBuilder("ChannelListingsLayout.fxml"));
    }

    @FXML
    void viewPrograms(ActionEvent event) {
        handleBtnToggle(programBtn, new Button[]{dashboardBtn,channelListBtn});
        mainPane.setCenter(paneBuilder("ProgramsLayout.fxml"));
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void toggleFullscreen(ActionEvent event) {
        fullScreen = !fullScreen;
        mainStage.setMaximized(fullScreen);
        mainStage.setFullScreen(fullScreen);
    }

    private void handleBtnToggle(Button active, Button[] inactiveButtons){

        active.getStyleClass().removeAll("sidebarBtn","sidebarBtnActive");

        active.getStyleClass().add("sidebarBtnActive");

        for (Button invBtn : inactiveButtons) {
            invBtn.getStyleClass().removeAll("sidebarBtn","sidebarBtnActive");
            invBtn.getStyleClass().add("sidebarBtn");
        }

    }

    private Pane paneBuilder(String layout){

        try{
            Pane pane = new FXMLLoader().load(MainLayoutController.class.getResource("/layouts/"+layout));

            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}

