package helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Optional;

public class SceneBuilder {

    private Stage stage;

    private Scene scene;

    private Parent parent;

    private Double Length;

    private Double Height;

    private String LayoutName;

    private String Title;

    public SceneBuilder(Stage stage, String layoutName, @Nullable Optional<String> title, @Nullable Optional<Double> length, @Nullable Optional<Double> height) {
        this.stage = stage;
        LayoutName = layoutName;
        Length = length != null ? length.get() : 900.0;
        Height = height != null ? height.get() : 1200.0;
        Title = title != null ? title.get() : "LITE TV";
        setIcon();
        Setup();
    }

    private void setIcon(){
        String localUrl2 = "";
        File file1 = new File(Paths.get("").toAbsolutePath().toString()+"/Assets/icon1.png");

        try {
            localUrl2 = file1.toURI().toURL().toString();
        } catch (MalformedURLException ex) {

        }

        Image img2 = new Image(localUrl2);

        stage.getIcons().add(img2);
    }

    public void Setup(){

        try{

            parent = FXMLLoader.load(SceneBuilder.class.getResource("/layouts/"+LayoutName));

            scene = new Scene(parent);

            stage.setScene(scene);

            stage.setTitle(Title);

            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getParent() {
        return parent;
    }

    public double getLength() {
        return Length.doubleValue();
    }

    public double getHeight() {
        return Height.doubleValue();
    }

    public String getLayoutName() {
        return LayoutName;
    }

}
