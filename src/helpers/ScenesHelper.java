package helpers;

import javafx.stage.Modality;
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

        new SceneBuilder(stage,"ProgramInfoLayout.fxml",Optional.of("Program Description"),Optional.of(300.00),Optional.of(800.00));
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
}
