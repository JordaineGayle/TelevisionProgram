package controllers;

import helpers.AuthenticationHelper;
import helpers.DatabaseHelper;
import helpers.SceneBuilder;
import helpers.ScenesHelper;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    private static Button programBtnInstance  = new Button(), channelBtnInstance = new Button();

    @FXML
    private Button programBtn = new Button();

    @FXML
    private Button channelListBtn = new Button();

    @FXML
    private Button fullScreenBtn = new Button();

    @FXML
    private Button exitBtn = new Button();

    @FXML
    private Label markedItems = new Label();

    private static IntegerProperty markedItemsCount = new SimpleIntegerProperty();

    private static SimpleStringProperty str = new SimpleStringProperty();

    @FXML
    private BorderPane mainPane = new BorderPane();

    private static BorderPane globalMainPaneInstance = new BorderPane();

    private boolean fullScreen = true;

    private Stage mainStage = SceneBuilder.getStages().get("MainLayout.fxml");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.setCenter(paneBuilder("ChannelListingsLayout.fxml"));

        markedItems.textProperty().bind(markedItemsCount.asString());

        if(DatabaseHelper.getMarkedPrograms().size() > 0){
            markedItemsCount.setValue(DatabaseHelper.getMarkedPrograms().size());
        }

        globalMainPaneInstance = mainPane;
        programBtnInstance = programBtn;
        channelBtnInstance = channelListBtn;
    }


    @FXML
    void viewListings(ActionEvent event) {
        handleBtnToggle(channelListBtn, new Button[]{programBtn});
        mainPane.setCenter(paneBuilder("ChannelListingsLayout.fxml"));
    }

    @FXML
    void viewPrograms(ActionEvent event) {
        navigateToPrograms();
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

    public static void setProgramBtnActive(){

        programBtnInstance.getStyleClass().removeAll("sidebarBtn","sidebarBtnActive");

        programBtnInstance.getStyleClass().add("sidebarBtnActive");

        channelBtnInstance.getStyleClass().removeAll("sidebarBtn","sidebarBtnActive");
        channelBtnInstance.getStyleClass().add("sidebarBtn");

    }

    public static Pane paneBuilder(String layout){

        try{
            Pane pane = new FXMLLoader().load(MainLayoutController.class.getResource("/layouts/"+layout));

            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void incrementMarkedItems(int val){
        markedItemsCount.setValue(val);
    }

    public static BorderPane getMainPane(){
        return globalMainPaneInstance;
    }

    public static void navigateToPrograms(){

        if(AuthenticationHelper.isAuthenticated()){
            setProgramBtnActive();
            globalMainPaneInstance.setCenter(paneBuilder("ProgramsLayout.fxml"));
        }else{
            ScenesHelper.InvokeAuthentication(new Stage());
        }
    }

}

