/**
 * Has the list of marked and unmarked programs, you can literally see everything here, has a search capability and filters.
 * */

package controllers;

import helpers.DatabaseHelper;
import helpers.SceneBuilder;
import helpers.ScenesHelper;
import helpers.TimeHelper;
import interfaces.IProgram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.ProgramListing;
import models.ProgramType;
import org.controlsfx.control.Rating;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProgramsLayoutController implements Initializable {

    @FXML
    private BorderPane testPane = new BorderPane();

    @FXML
    private TilePane programsTile = new TilePane();

    @FXML
    private Button addProgram = new Button();

    @FXML
    private Tooltip tooltip = new Tooltip();

    @FXML
    private ComboBox<ProgramType> filterbox = new ComboBox();

    @FXML
    private ComboBox<ProgramListing> listingBox = new ComboBox();

    @FXML
    private TextField searchbox = new TextField();

    public static IProgram currentlyModifiedProgram;

    private List<IProgram> programs = DatabaseHelper.getPrograms();

    private List<VBox> mappedItems = new ArrayList<>();

    private Stage stage = SceneBuilder.getStages().get("ProgramModificationLayout.fxml");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip.setShowDelay(Duration.millis(0));
        tooltip.setAutoHide(true);
        tooltip.setAutoFix(true);
        setUpFilter();
        setUpAddProgramButton();
        queryProgramSetup();
        determineAndbuildFilteredPrograms();

        stage.setOnCloseRequest(e -> {
            determineAndbuildFilteredPrograms();
        });
    }

    private void setUpAddProgramButton(){
        addProgram.setOnMouseClicked(e -> {
            ScenesHelper.InvokeProgramModification(new Stage());
        });
    }

    private void setUpFilter(){
        filterbox.getItems().addAll(ProgramType.values());

        listingBox.getItems().addAll(ProgramListing.values());

        filterbox.setValue(ProgramType.All);

        listingBox.setValue(ProgramListing.Unmarked);

        filterbox.setOnAction(e -> {
            determineAndbuildFilteredPrograms();
        });

        listingBox.setOnAction(e -> {
            determineAndbuildFilteredPrograms();
        });
    }

    private void queryProgramSetup(){

        searchbox.textProperty().addListener((c,o,n)->{

            if(n == null || n.isEmpty()){
                determineAndbuildFilteredPrograms();
            }else{
                List<String> searchItems = Arrays.asList(n.split(",").clone());

                determineAndbuildFilteredPrograms();

                programs = programs.stream().filter(e -> isMatch(searchItems,e.getTitle()) == true).collect(Collectors.toList());

                searchProgramsBuilder();
            }

        });

    }

    private boolean isMatch(List<String> queryParams, String queryText)
    {
        queryText = queryText.toLowerCase();

        for (int x = 0; x < queryParams.size(); x++){
            if(queryText.contains(queryParams.get(x).toLowerCase()))return true;
        }

        return false;
    }

    private void buildTiles(List<IProgram> programs){

        if(programs == null){
            programs = DatabaseHelper.getPrograms();
        }

        programs.forEach(prog ->{

            List<HBox> hbs = new ArrayList<>();

            List<Node> image = new ArrayList<>();
            image.add(buildImageNode(prog.getImage()));
            hbs.add(buildDefaultHBox(image));

            List<Node> title = buildNodeMap("Title:",prog.getTitle());
            hbs.add(buildDefaultHBox(title));

            List<Node> category = buildNodeMap("Category:",prog.getProgramType());
            hbs.add(buildDefaultHBox(category));

            List<Node> airdate = buildNodeMap("Air Date:", TimeHelper.convertToHumanReadableFormat(prog.getProgramAirDateTime()));
            hbs.add(buildDefaultHBox(airdate));

            List<Node> length = buildNodeMap("Length:",prog.getLength()+"");
            hbs.add(buildDefaultHBox(length));

            List<Node> duration = buildNodeMap("Duration:",prog.getDuration()+"");
            hbs.add(buildDefaultHBox(duration));

            List<Node> closedCaption = buildNodeMap("Closed Caption:",prog.isClosedCaption() ? "enabled" : "disabled");
            hbs.add(buildDefaultHBox(closedCaption));

            try{

                List<Node> phase = buildNodeMap("Phase:",prog.getProgramPhase().name());
                hbs.add(buildDefaultHBox(phase));
            }catch (Exception e){}

            if(listingBox.getValue().equals(ProgramListing.Marked)){
                try{

                    List<Node> phase = buildNodeMap("Status:",prog.getProgramStatus().name());
                    hbs.add(buildDefaultHBox(phase));
                }catch (Exception e){}
            }

            try{
                String actornames = String.join(",", prog.getActors().stream().map(e -> e.getFirstName()+" "+e.getLastName()).collect(Collectors.toList()));

                List<Node> actors = buildNodeMap("Starring:",null);
                hbs.add(buildDefaultHBox(actors));


                List<Node> actorsn = buildNodeMap(null,actornames);
                hbs.add(buildDefaultHBox(actorsn));

            }catch (Exception e){}

            try
            {
                List<Node> agerange = buildNodeMap("Release Date",TimeHelper.convertToHumanReadableFormat(prog.getDateReleased()));
                hbs.add(buildDefaultHBox(agerange));
            }catch (Exception e){}

            try
            {
                List<Node> agerange = buildNodeMap("Age range",prog.getAgeRange().getMin()+" - "+prog.getAgeRange().getMax());
                hbs.add(buildDefaultHBox(agerange));
            }catch (Exception e){}


            try
            {
                List<Node> agerange = buildNodeMap("Denomination",prog.getDenomination().name());
                hbs.add(buildDefaultHBox(agerange));
            }catch (Exception e){}

            try
            {
                if(prog.getProgramType().equals(ProgramType.Movie.name())){
                    List<Node> ratingLabel = buildNodeMap("Rating",null);
                    hbs.add(buildDefaultHBox(ratingLabel));

                    Node rating = buildRatingNode(prog.getRating(),8);
                    List<Node> ratings = new ArrayList<>();
                    ratings.add(rating);
                    hbs.add(buildDefaultHBox(ratings));
                }
            }catch (Exception e){}


            try
            {
                if(prog.getProgramType().equals(ProgramType.Weather.name())){
                    List<Node> ratingLabel = buildNodeMap("Severity Rating",null);
                    hbs.add(buildDefaultHBox(ratingLabel));

                    Node rating = buildRatingNode(prog.getSeverityRating(),5);
                    List<Node> ratings = new ArrayList<>();
                    ratings.add(rating);
                    hbs.add(buildDefaultHBox(ratings));
                }
            }catch (Exception e){}

            VBox vb = buildDefaultVBox(hbs);


            if(listingBox.getValue().equals(ProgramListing.Unmarked)){
                vb.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY){
                        currentlyModifiedProgram = prog;
                        ScenesHelper.InvokeProgramModification(new Stage());
                    }
                });
            }else{
                vb.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY){
                        ChannelListingsLayoutController.setViewNowProgram(prog);
                    }

                });
            }

            vb.setId(prog.getId());

            vb.setEffect(buildDShadow());

            mappedItems.add(vb);
        });

        programsTile.getChildren().addAll(mappedItems);
    }

    private VBox buildDefaultVBox(List<HBox> nodes){

        VBox box = new VBox();

        box.setStyle("-fx-background-color: white;-fx-background-radius: 10");

        box.setPadding(new Insets(10));

        box.setMinSize(VBox.USE_COMPUTED_SIZE,VBox.USE_COMPUTED_SIZE);

        box.setMaxSize(293,VBox.USE_COMPUTED_SIZE);

        box.setPrefSize(293,VBox.USE_COMPUTED_SIZE);

        box.setFillWidth(true);

        box.getChildren().addAll(nodes);

        VBox.setMargin(box,new Insets(30));

        return box;
    }

    private HBox buildDefaultHBox(List<Node> nodes){
        HBox box = new HBox();
        box.setFillHeight(true);
        box.setPadding(new Insets(5));
        box.setMinSize(HBox.USE_COMPUTED_SIZE,HBox.USE_COMPUTED_SIZE);
        box.setMaxSize(HBox.USE_COMPUTED_SIZE,HBox.USE_COMPUTED_SIZE);
        box.setPrefSize(HBox.USE_COMPUTED_SIZE,HBox.USE_COMPUTED_SIZE);

        for (Node n: nodes) {
            Region region = new Region();
            region.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            region.setMaxSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            region.setPrefSize(10,Region.USE_COMPUTED_SIZE);

            box.getChildren().addAll(n,region);
        }
        return box;
    }

    private List<Node> buildNodeMap(String key, String value){
        List<Node> nodes = new ArrayList<>();

        if(key != null && !key.isEmpty())
        {
            Label keyLabel = new Label(key);
            keyLabel.setMinSize(Label.USE_COMPUTED_SIZE,Label.USE_COMPUTED_SIZE);
            keyLabel.setPrefSize(Label.USE_COMPUTED_SIZE,Label.USE_COMPUTED_SIZE);
            keyLabel.setFont(new Font("Rockwell",14));
            keyLabel.setStyle("-fx-font-weight: bold;-fx-text-fill:#212121");
            keyLabel.setMaxWidth(200);
            keyLabel.setWrapText(true);
            nodes.add(keyLabel);
        }

        if(value != null && !value.isEmpty())
        {
            Label valueLabel = new Label(value);
            valueLabel.setMinSize(Label.USE_COMPUTED_SIZE,Label.USE_COMPUTED_SIZE);
            valueLabel.setPrefSize(Label.USE_COMPUTED_SIZE,Label.USE_COMPUTED_SIZE);
            valueLabel.setFont(new Font("Rockwell",12));
            valueLabel.setStyle("-fx-text-fill:#212121");
            valueLabel.setMaxWidth(300);
            valueLabel.setWrapText(true);
            nodes.add(valueLabel);
        }

        return nodes;
    }

    private Node buildRatingNode(double value, int max){
        Rating rating = new Rating();

        rating.setPartialRating(true);

        rating.setMax(max);

        rating.setRating(value);

        rating.setMinSize(Rating.USE_COMPUTED_SIZE,Rating.USE_COMPUTED_SIZE);

        rating.setMaxSize(Rating.USE_COMPUTED_SIZE,Rating.USE_COMPUTED_SIZE);

        rating.setPrefSize(Rating.USE_COMPUTED_SIZE,Rating.USE_COMPUTED_SIZE);

        rating.setDisable(true);

        return rating;
    }

    private Node buildImageNode(String source){
        ImageView image = new ImageView();

        if(source == null || source.isEmpty()){
            image.setImage(new Image(Paths.get("").toAbsolutePath().toUri().toString()+"/src/assets/defaultmedia.png",true));
        }else{
            image.setImage(new Image(source,true));
        }

        image.setFitWidth(293);
        image.setFitHeight(230);
        image.setSmooth(true);
        return image;
    }

    private void determineAndbuildFilteredPrograms(){

        if(listingBox.getValue().equals(ProgramListing.Unmarked)){
            programs = DatabaseHelper.getPrograms();
        }else {
            programs = DatabaseHelper.getMarkedPrograms();
        }

        if(!filterbox.getValue().equals(ProgramType.All)){
            programs = programs.stream().filter(e -> e.getProgramType().equals(filterbox.getValue().name())).collect(Collectors.toList());
        }

        programsTile.getChildren().clear();
        mappedItems = new ArrayList<>();
        buildTiles(programs);
    }

    private void searchProgramsBuilder(){
        programsTile.getChildren().clear();
        mappedItems = new ArrayList<>();
        buildTiles(programs);
    }

    private  static DropShadow buildDShadow(){

        DropShadow ds = new DropShadow();

        ds.setBlurType(BlurType.THREE_PASS_BOX);

        ds.setWidth(32.61);

        ds.setHeight(40.89);

        ds.setRadius(17.88);

        ds.setSpread(0.19);

        ds.setColor(Color.rgb(0, 0, 0, 0.16));

        return ds;
    }
}
