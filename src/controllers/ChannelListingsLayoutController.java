package controllers;

import com.google.gson.reflect.TypeToken;
import helpers.DatabaseHelper;
import helpers.TimeHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import models.Program;
import models.ProgramColor;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static helpers.TimeHelper.formatTo12Hour;

public class ChannelListingsLayoutController implements Initializable {

    @FXML
    private BorderPane channelBorderPane = new BorderPane();

    @FXML
    private Label currentDateLabel = new Label();

    @FXML
    private ScrollPane scrollPane = new ScrollPane();

    private GridPane grid = new GridPane();



    private HashMap<Integer,String> clockHours =  new TimeHelper().get24HrClock();

    private Map<String,String> channels = DatabaseHelper.getChannels();

    private Map<String, Integer> channelsRowMapping = new TreeMap<>();

    private Map<String, Integer> channelsColumnMapping = new HashMap<>();

    private List<Object> programs = DatabaseHelper.getPrograms();

    private ArrayList<Program> basePrograms = DatabaseHelper.toType(programs,new TypeToken<>(){});

    private int currentHour = LocalDateTime.now().getHour();

    private int currentMins = LocalDateTime.now().getMinute();

    private ArrayList<String> columnNames = new ArrayList<>();

    private ArrayList<Program> todaysPrograms = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting the current date time for the channel listing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy hh:mm a");
        String now = LocalDateTime.now().format(formatter);
        currentDateLabel.setText(now);

        //Adding time quartz between the clock hours
        clockHours.forEach((hour, val) -> {
            if(hour >= currentHour){

                int quart = 0;

                columnNames.add(val);

            }
        });

        //setting the grid up
        setupGrid();

        //setting scroll pane up
        setupScrollPane();

    }



    private void setupGrid(){
        grid.setGridLinesVisible(true);
        grid.setCursor(Cursor.HAND);
        grid.setStyle("-fx-background-color: #fff");
        grid.setPadding(new Insets(10,10,10,10));

        setGridHeadings();
    }

    private void setupScrollPane(){
        scrollPane.setPannable(true);
        scrollPane.setContent(grid);
    }

    private void setGridHeadings(){

        grid.addColumn(0,buildHeadingLabel("Channels"));

        grid.getColumnConstraints().add(setColumnConstraints(150,HPos.LEFT));

        loadColumnNames();

        loadRowNames();

        loadProgramsInCell();
    }




    private void loadColumnNames(){
        AtomicInteger colKey = new AtomicInteger();

        for(int x = 0; x < columnNames.size(); x++){

            String colName = columnNames.get(x);

            int currentColumnKey = colKey.get()+1;

            grid.addColumn(currentColumnKey,buildHeadingLabel(colName));
            grid.getColumnConstraints().add(setColumnConstraints(250,HPos.CENTER));

            channelsColumnMapping.put(colName,currentColumnKey);

            colKey.getAndIncrement();
        }
    }

    private void loadRowNames(){

        AtomicInteger rowKey = new AtomicInteger();

        channels.forEach((cnum,cname) -> {

            String combinedChannelName = cnum+" - "+cname;

            int currentRowKey = rowKey.get()+1;

            grid.add(buildChildrenLabel(combinedChannelName),0,currentRowKey);
            grid.getRowConstraints().add(setRowConstraints(0,VPos.CENTER));

            channelsRowMapping.put(combinedChannelName.toUpperCase(),currentRowKey);

            rowKey.getAndIncrement();

        });
    }

    private void loadProgramsInCell(){

        if(programs==null) return;

        programs.forEach((o) -> {

            Program program = DatabaseHelper.toType(o,new TypeToken<>(){});

            if(TimeHelper.isDateEqualToNow(program.getProgramAirDateTime())){

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:00 a");

                String colKey = program.getProgramAirDateTime().format(formatter);

                String rowKey = program.getChannelName();

                double totalHours = (program.getProgramAirDateTime().getHour() + program.getLength());

                if(program.getLength() > 0){

                    String newColKey = "";

                    for(int t = program.getProgramAirDateTime().getHour(); t < totalHours; t++){

                        if(t >= currentHour && t < 24){

                            newColKey = formatTo12Hour(t,"00");

                            Label node = buildChildrenLabel(program.getTitle());

                            setupCellNode(program,node);

                            grid.add(node,channelsColumnMapping.get(newColKey),channelsRowMapping.get(rowKey));

                        }
                    }

                }
                else{
                    if(program.getProgramAirDateTime().getHour() >= currentHour){

                        Label node = buildChildrenLabel(program.getTitle());

                        setupCellNode(program,node);

                        grid.add(node,channelsColumnMapping.get(colKey),channelsRowMapping.get(rowKey));
                    }
                }
            }
        });
    }




    private Label buildHeadingLabel(String name){
        Label label = new Label(name);

        label.setPadding(new Insets(10,10,10,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setFont(new Font("Rockwell",16));

        return label;
    }

    private Label buildChildrenLabel(String name){

        Label label = new Label(name);

        label.setPadding(new Insets(10,10,10,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setPrefWidth(250);

        label.setFont(new Font("Rockwell",12));

        return label;
    }

    private void setupCellNode(Program program, Label node){

        ContextMenu contextMenu = setepCellContextMenu(program);

        node.getStyleClass().add(program.getProgramColor().name().toLowerCase());

        node.getStylesheets().clear();

        node.getStylesheets().add(getClass().getResource("/assets/styles/labelstyle.css").toExternalForm());

        node.setId(program.getId());

        node.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                contextMenu.show(node, event.getScreenX(), event.getScreenY());
            }

            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2){
                System.out.println("You are now viewing: "+program.getTitle());
            }
        });

    }

    private ContextMenu setepCellContextMenu(Program program){

        ContextMenu contextMenu = new ContextMenu();

        MenuItem watchNow = new MenuItem("Watch Now");

        MenuItem viewLater = new MenuItem("View Later");

        MenuItem viewMore = new MenuItem("See Full Description");

        contextMenu.getItems().addAll(watchNow,viewLater,viewMore);

        contextMenu.getItems().forEach( i -> {
            i.setStyle("-fx-text-fill:#fff;-fx-font-size: 12px;-fx-padding:5 80 5 5;-fx-width:250px");
        });

        contextMenu.setOpacity(0.9);

        contextMenu.centerOnScreen();

        contextMenu.setStyle("-fx-background-color:#212121;-fx-text-fill:#fff");

        contextMenu.setAutoFix(true);

        contextMenu.setAutoHide(true);

        return contextMenu;
    }

    private ColumnConstraints setColumnConstraints(double width, HPos pos){

        ColumnConstraints colconst = new ColumnConstraints();


        colconst.setHgrow(Priority.ALWAYS);
        colconst.setHalignment(pos);
        colconst.setFillWidth(true);
        return colconst;
    }

    private RowConstraints setRowConstraints(double height, VPos pos){

        RowConstraints rowconst = new RowConstraints();

        if (height > 0){
            rowconst.setMinHeight(height);
        }

        rowconst.setVgrow(Priority.ALWAYS);
        rowconst.setFillHeight(true);


        if (pos!=null){
            rowconst.setValignment(pos);
        }

        return rowconst;
    }

}
