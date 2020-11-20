package controllers;

import helpers.TimeHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class ChannelListingsLayoutController implements Initializable {

    private HashMap<Integer,String> clockHours =  new TimeHelper().get24HrClock();

    private int currentHour = LocalDateTime.now().getHour();

    private ArrayList<String> columnNames = new ArrayList<>();

    @FXML
    private BorderPane channelBorderPane = new BorderPane();

    private GridPane grid = new GridPane();

    private GridPane listingGrid = new GridPane();

    private ScrollPane scrollPane = new ScrollPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        System.out.println(columnNames);

        setupGrid();

        scrollPane.setPannable(true);
        scrollPane.setContent(grid);

    }

    private void setupGrid(){

        grid.setGridLinesVisible(true);
        grid.setCursor(Cursor.HAND);
        grid.setStyle("-fx-background-color: #fff");

        grid.setPadding(new Insets(10,10,10,10));
        setGridHeadings();
    }

    private void setGridHeadings(){
        grid.addColumn(0,BuildHeadingLabel("Channels"));

        grid.getColumnConstraints().add(setColumnConstaints(150,HPos.LEFT));

        for(int x = 0; x < columnNames.size(); x++){

            grid.addColumn(x+1,BuildHeadingLabel(columnNames.get(x)));
            grid.addRow(columnNames.size()+x,BuildHeadingLabel("Station: "+x));

            grid.getColumnConstraints().add(setColumnConstaints(250,HPos.CENTER));
        }
    }


    private Label BuildHeadingLabel(String name){
        Label label = new Label(name);

        label.setPadding(new Insets(10,10,10,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setFont(new Font("Rockwell",16));

        return label;
    }

    private ColumnConstraints setColumnConstaints(double width, HPos pos){

        ColumnConstraints colconst = new ColumnConstraints();

        colconst.setMinWidth(width);
        colconst.setHgrow(Priority.ALWAYS);
        colconst.setHalignment(pos);
        return colconst;
    }

}
