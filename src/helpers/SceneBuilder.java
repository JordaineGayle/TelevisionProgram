/**
 * Create dynamic scenes, this class handles the state of all stages, all thier instances are stored and retrieved here.
 * **/

package helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class SceneBuilder {

    private static HashMap<String,Stage> stages = new HashMap<>();

    private Stage stage;

    private Double Width;

    private Double Height;

    private String LayoutName;

    private String Title;

    public SceneBuilder(Stage stage, String layoutName, @Nullable Optional<String> title, @Nullable Optional<Double> width, @Nullable Optional<Double> height) {
        this.stage = stage;
        stages.put(layoutName,stage);
        LayoutName = layoutName;
        Width = width != null ? width.get() : 1000;
        Height = height != null ? height.get() : 500.0;
        Title = title != null ? title.get() : "JCTCL Cable TV";
        setIcon();
        Setup();
    }

    public void Setup(){

        try{

            Scene scene = new Scene(FXMLLoader.load(SceneBuilder.class.getResource("/layouts/"+LayoutName)));

            //aids in creating translucent scenes.
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);

            stage.setTitle(Title);

            stage.setFullScreen(true);

            stage.setMaximized(true);

            stage.setMinWidth(Width);

            stage.setMinHeight(Height);

            stage.maximizedProperty().addListener((event,o,n)->{
                stage.setFullScreen(true);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setIcon(){
        String localUrl2 = "";
        File file1 = new File(Paths.get("").toAbsolutePath().toString()+"/src/assets/icon2.png");

        try {
            localUrl2 = file1.toURI().toURL().toString();
        } catch (MalformedURLException ex) {

        }

        Image img2 = new Image(localUrl2);

        stage.getIcons().add(img2);
    }

    public static HashMap<String, Stage> getStages() {
        return stages;
    }

    public double getLength() {
        return Width.doubleValue();
    }

    public double getHeight() {
        return Height.doubleValue();
    }

    public String getLayoutName() {
        return LayoutName;
    }

}
