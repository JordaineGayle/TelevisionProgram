import helpers.SceneBuilder;
import helpers.ScenesHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScenesHelper.InvokeMainLayout(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
