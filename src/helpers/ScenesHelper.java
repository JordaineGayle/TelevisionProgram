package helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Optional;

public class ScenesHelper {

    public static void InvokeMainLayout(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("MainLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }

        new SceneBuilder(stage,"MainLayout.fxml",null,null,null);
        stage.show();
    }

    public static void InvokeMediaPlayer(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("MediaPlayerLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        new SceneBuilder(stage,"MediaPlayerLayout.fxml", Optional.of("Media Player"),null,null);
        stage.show();
    }

    public static void InvokeProgramDescription(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("ProgramInfoLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        new SceneBuilder(stage,"ProgramInfoLayout.fxml",Optional.of("Program Description"),Optional.of(378.00),Optional.of(785.00));
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.showAndWait();
    }

    public static void InvokeProgramModification(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("ProgramModificationLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        new SceneBuilder(stage,"ProgramModificationLayout.fxml",Optional.of("Program Modification"),Optional.of(400.00),null);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.showAndWait();
    }

    public static void InvokeAuthentication(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("AuthenticationLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        new SceneBuilder(stage,"AuthenticationLayout.fxml",Optional.of("User Authentication"),Optional.of(600.00),Optional.of(400.00));
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.showAndWait();
    }

    public static void InvokeProgramModificationInstance(Stage stage){

        Stage hasStage = SceneBuilder.getStages().get("ProgramModificationLayout.fxml");

        if(hasStage != null){
            stage = hasStage;
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        new SceneBuilder(stage,"ProgramModificationLayout.fxml",Optional.of("Program Modification"),Optional.of(400.00),null);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
    }

    public static void createPopup(String stageName, String message, Node node){

        VBox vb = new VBox();

        vb.setMinSize(VBox.USE_COMPUTED_SIZE,VBox.USE_COMPUTED_SIZE);
        vb.setMaxSize(VBox.USE_COMPUTED_SIZE,VBox.USE_COMPUTED_SIZE);
        vb.setPrefSize(VBox.USE_COMPUTED_SIZE,VBox.USE_COMPUTED_SIZE);
        vb.setAlignment(Pos.CENTER);
        vb.setStyle("-fx-background-color: rgba(10,10,10,0.8);-fx-background-radius:10;");

        Label label = new Label(message);
        label.setFont(new Font("Rockwell",14));
        label.setTextFill(Color.WHITE);
        label.setPadding(new Insets(10));
        label.setWrapText(true);
        label.prefHeight(200);
        label.prefWidth(300);
        label.minWidth(300);
        label.minHeight(200);

        vb.getChildren().add(label);

        Popup pop = new Popup();

        pop.setAutoHide(true);

        if(node!=null){

            node.prefWidth(150);
            node.prefHeight(40);
            node.setCursor(Cursor.HAND);

            vb.getChildren().add(node);
        }

        pop.getContent().add(vb);

        pop.show(SceneBuilder.getStages().get(stageName));
    }
}
