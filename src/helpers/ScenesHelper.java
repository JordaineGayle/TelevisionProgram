package helpers;

import javafx.stage.Stage;

public class ScenesHelper {

    public static void InvokeMainLayout(Stage stage){
        new SceneBuilder(stage,"MainLayout.fxml",null,null,null);
    }
}
