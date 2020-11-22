package helpers;

import javafx.stage.Modality;
import javafx.stage.Stage;

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

        new SceneBuilder(stage,"MediaPlayerLayout.fxml",null,null,null);
        stage.show();
    }
}
