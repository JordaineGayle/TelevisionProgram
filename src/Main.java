import helpers.DatabaseHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //ScenesHelper.InvokeMainLayout(primaryStage);
    }


    public static void main(String[] args) {

        new DatabaseHelper().InitializeDB();

        System.out.println(DatabaseHelper.GetChannels());

        launch(args);
    }
}
