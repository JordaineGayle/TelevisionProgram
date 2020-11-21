package controllers;

import helpers.DatabaseHelper;
import helpers.TimeHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import models.Program;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ChannelListingsLayoutController implements Initializable {

    private HashMap<Integer,String> clockHours =  new TimeHelper().get24HrClock();

    private Map<String,String> channels = DatabaseHelper.getChannels();

    private Map<String, Integer> channelsRowMapping = new TreeMap<>();

    private List<Object> programs = DatabaseHelper.getPrograms();

    private int currentHour = LocalDateTime.now().getHour();

    private ArrayList<String> columnNames = new ArrayList<>();

    @FXML
    private BorderPane channelBorderPane = new BorderPane();

    @FXML
    private Label currentDateLabel = new Label();

    @FXML
    private ScrollPane scrollPane = new ScrollPane();


    private GridPane grid = new GridPane();

    private GridPane listingGrid = new GridPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting the current date time for the channel listing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy HH:mm a");
        String now = LocalDateTime.now().format(formatter);
        currentDateLabel.setText(now);

        //Adding time quartz between the clock hours
        clockHours.forEach((hour, val) -> {
            if(hour >= currentHour){

                int quart = 0;

                columnNames.add(val);

                for (int x = 0; x < 4; x++){

                    quart = quart+15;

                    if(quart == 60){
                        quart--;
                    }

                    columnNames.add(TimeHelper.formatTo12Hour(hour,String.valueOf(quart)));
                }
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

        AtomicInteger rowKey = new AtomicInteger();

        grid.addColumn(0,BuildHeadingLabel("Channels"));

        grid.getColumnConstraints().add(setColumnConstaints(150,HPos.LEFT));

        for(int x = 0; x < columnNames.size(); x++){
            grid.addColumn(x+1,BuildHeadingLabel(columnNames.get(x)));
            grid.getColumnConstraints().add(setColumnConstaints(250,HPos.CENTER));
        }

        channels.forEach((cnum,cname) -> {

            String combinedChannelName = cnum+" - "+cname;

            int currentRowKey = rowKey.get()+1;

            grid.add(BuildChildrenLabel(combinedChannelName),0,currentRowKey);
            grid.getRowConstraints().add(setRowConstaints(0,VPos.CENTER));

            channelsRowMapping.put(combinedChannelName.toUpperCase(),currentRowKey);

            rowKey.getAndIncrement();

        });

        if(programs!=null){
            programs.forEach((o) -> {
                Program program = (Program)o;

                double programTotalAirTime = program.getAirTime() + program.getLength();


            });
        }

    }


    private Label BuildHeadingLabel(String name){
        Label label = new Label(name);

        label.setPadding(new Insets(10,10,10,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setFont(new Font("Rockwell",16));

        return label;
    }

    private Label BuildChildrenLabel(String name){
        Label label = new Label(name);

        label.setPadding(new Insets(10,10,10,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setFont(new Font("Rockwell",12));

        return label;
    }

    private ColumnConstraints setColumnConstaints(double width, HPos pos){

        ColumnConstraints colconst = new ColumnConstraints();

        colconst.setMinWidth(width);
        colconst.setHgrow(Priority.ALWAYS);
        colconst.setHalignment(pos);
        return colconst;
    }

    private RowConstraints setRowConstaints(double height, VPos pos){

        RowConstraints rowconst = new RowConstraints();

        if (height > 0){
            rowconst.setMinHeight(height);
        }

        rowconst.setVgrow(Priority.ALWAYS);


        if (pos!=null){
            rowconst.setValignment(pos);
        }

        return rowconst;
    }

}
