import controllers.MainLayoutController;
import helpers.DatabaseHelper;
import helpers.ScenesHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScenesHelper.InvokeMainLayout(primaryStage);
        ScenesHelper.InvokeProgramModificationInstance(new Stage());
    }


    public static void main(String[] args) {
        DatabaseHelper.db.InitializeDB();

        launch(args);


    }
}
