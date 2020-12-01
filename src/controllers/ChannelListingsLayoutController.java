package controllers;
import helpers.DatabaseHelper;
import helpers.ScenesHelper;
import helpers.TimeHelper;
import interfaces.IProgram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
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

    private List<String> channels = DatabaseHelper.getChannels();

    private Map<String, Integer> channelsRowMapping = new TreeMap<>();

    private Map<String, Integer> channelsColumnMapping = new HashMap<>();

    private List<IProgram> programs = DatabaseHelper.getPrograms();

    private int currentHour = LocalDateTime.now().getHour();

    private ArrayList<String> columnNames = new ArrayList<>();

    private static IProgram viewNowProgram;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting the current date time for the channel listing

        String now = TimeHelper.convertToHumanReadableFormat(LocalDateTime.now());
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

    public static IProgram getCurrentViewingNowProgram(){
        return viewNowProgram;
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

        channels.forEach((cname) -> {

            String combinedChannelName = cname;

            int currentRowKey = rowKey.get()+1;

            grid.add(buildChildrenLabel(combinedChannelName),0,currentRowKey);
            grid.getRowConstraints().add(setRowConstraints(0,VPos.CENTER));

            channelsRowMapping.put(combinedChannelName.toUpperCase(),currentRowKey);

            rowKey.getAndIncrement();

        });
    }

    private void loadProgramsInCell(){
        /**this was built with the ability to display programs from another day but due to time
        we had to limit down the program within a day,  the logic for adding was becoming to complex.*/
        if(programs==null) return;

        programs.forEach((program) -> {

            LocalDateTime correctDate = TimeHelper.correctProgramDateLength(program);

            LocalDateTime fullCorrectDate = TimeHelper.correctProgramDate(program);

            if(TimeHelper.isDateEqualToNow(correctDate)){

                handleDurationHours(program);
            }
            else if(LocalDateTime.now().isAfter(correctDate) && program.getDuration() > 0){
                if(fullCorrectDate.isAfter(LocalDateTime.now()) || TimeHelper.isDateEqualToNow(fullCorrectDate)){

                    int totalHours = (int)(program.getProgramAirDateTime().getHour() + program.getLength());

                    if(totalHours > 23){
                        handleDurationHoursOffset(program);
                    }

                    handleDurationDays(program);

                }
            }
        });
    }

    private void handleDurationDays(IProgram program){

        LocalDateTime accurateDate = program.getProgramAirDateTime();

        double totalHours = (program.getProgramAirDateTime().getHour() + program.getLength());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:00 a");

        String colKey = accurateDate.format(formatter);

        String rowKey = program.getChannelName();

        if(program.getLength() > 0){

            String newColKey = "";

            for(int t = accurateDate.getHour(); t < totalHours; t++){

                if(t >= currentHour && t < 24){

                    newColKey = formatTo12Hour(t,"00");

                    Label node = buildChildrenLabel(program.getTitle());
                    node.setGraphic(buildChildrenIndicators(program));

                    if(t == currentHour){
                        setupCellNode(program,node,true);
                    }else{
                        setupCellNode(program,node,false);
                    }

                    grid.add(node,channelsColumnMapping.get(newColKey),channelsRowMapping.get(rowKey));

                }
            }
        }
        else{
            if(program.getProgramAirDateTime().getHour() >= currentHour){

                Label node = buildChildrenLabel(program.getTitle());
                node.setGraphic(buildChildrenIndicators(program));

                if(program.getProgramAirDateTime().getHour() == currentHour){
                    setupCellNode(program,node,true);
                }else{
                    setupCellNode(program,node,false);
                }

                grid.add(node,channelsColumnMapping.get(colKey),channelsRowMapping.get(rowKey));
            }
        }
    }

    private void handleDurationHours(IProgram program){

        LocalDateTime correctDate = TimeHelper.correctProgramDateLength(program);

        LocalDateTime accurateDate = program.getProgramAirDateTime();

        double totalHours = 0.0;

        if(!TimeHelper.isDateEqualToNow(accurateDate)){

            accurateDate = LocalDate.now().atStartOfDay();

            Duration duration = Duration.between(accurateDate,correctDate);

            totalHours = duration.toHours();

            accurateDate = LocalDateTime.now();

        }
        else{
            totalHours = (program.getProgramAirDateTime().getHour() + program.getLength());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:00 a");

        String colKey = accurateDate.format(formatter);

        String rowKey = program.getChannelName();


        if(program.getLength() > 0){

            String newColKey = "";

            for(int t = accurateDate.getHour(); t < totalHours; t++){

                if(t >= currentHour && t < 24){

                    newColKey = formatTo12Hour(t,"00");

                    Label node = buildChildrenLabel(program.getTitle());
                    node.setGraphic(buildChildrenIndicators(program));

                    if(t == currentHour){
                        setupCellNode(program,node,true);
                    }else{
                        setupCellNode(program,node,false);
                    }

                    grid.add(node,channelsColumnMapping.get(newColKey),channelsRowMapping.get(rowKey));

                }
            }

        }
        else{
            if(program.getProgramAirDateTime().getHour() >= currentHour){

                Label node = buildChildrenLabel(program.getTitle());
                node.setGraphic(buildChildrenIndicators(program));

                if(program.getProgramAirDateTime().getHour() == currentHour){
                    setupCellNode(program,node,true);
                }else{
                    setupCellNode(program,node,false);
                }

                grid.add(node,channelsColumnMapping.get(colKey),channelsRowMapping.get(rowKey));
            }
        }
    }

    private void handleDurationHoursOffset(IProgram program){

        LocalDateTime currentProgramDate = LocalDateTime.now();

        LocalDateTime previousProgramDate = LocalDateTime.now();

        LocalDateTime correctDate = LocalDateTime.now();

        LocalDateTime accurateDate = program.getProgramAirDateTime();

        Duration period = Duration.between(accurateDate,TimeHelper.correctProgramDate(program));

        long daysBetween = period.toDays();

        for (int t = 1; t <=daysBetween+1; t++){
            LocalDateTime newDt = accurateDate.plusDays(t);
            System.out.println("New DATE "+newDt.toString());
            if(TimeHelper.isDateEqualToNow(newDt)){
                currentProgramDate = newDt;
                break;
            }
        }

        previousProgramDate = currentProgramDate.minusDays(1);

        correctDate = previousProgramDate.plusHours((long)program.getLength());

        accurateDate = LocalDate.now().atStartOfDay();

        Duration duration = Duration.between(accurateDate,correctDate);

        long totalHours = duration.toHours();

        accurateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:00 a");

        String rowKey = program.getChannelName();

        String newColKey = "";

        for(int t = accurateDate.getHour(); t < totalHours; t++){

            if(t >= currentHour && t < 24){

                newColKey = formatTo12Hour(t,"00");

                Label node = buildChildrenLabel(program.getTitle());
                node.setGraphic(buildChildrenIndicators(program));

                if(t == currentHour){
                    setupCellNode(program,node,true);
                }else{
                    setupCellNode(program,node,false);
                }

                grid.add(node,channelsColumnMapping.get(newColKey),channelsRowMapping.get(rowKey));

            }
        }

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

        label.setPadding(new Insets(0,0,0,10));

        label.setTextAlignment(TextAlignment.CENTER);

        label.setAlignment(Pos.CENTER_LEFT);

        label.setPrefWidth(250);

        label.setMinSize(Label.USE_COMPUTED_SIZE,Label.USE_COMPUTED_SIZE);

        label.setMaxSize(Label.USE_PREF_SIZE,50);

        label.setFont(new Font("Rockwell",12));

        return label;
    }

    private Label buildChildrenIndicators(IProgram program){

        Label label = new Label();
        label.setPrefSize(10,10);
        label.setMinSize(10,10);
        label.setMaxSize(10,10);
        label.setPadding(new Insets(1));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setFont(Font.font("Rockwell", FontWeight.BOLD, 9));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(Color.BLACK);

        if(program.getProgramPhase() != null && program.getProgramPhase().equals(ProgramPhase.New)){
            label.setStyle("-fx-background-color: rgba(0,229,255 ,1);-fx-background-radius: 100;");
        }else if(program.getProgramPhase() != null && program.getProgramPhase().equals(ProgramPhase.Live)){
            label.setStyle("-fx-background-color: rgba(118,255,3 ,1);-fx-background-radius: 100;");
        }else if(program.getProgramPhase() != null && program.getProgramPhase().equals(ProgramPhase.Repeat)){
            label.setStyle("-fx-background-color: rgba(0,77,64 ,1);-fx-background-radius: 100;");
            label.setTextFill(Color.WHITE);
        }else{
            label.setStyle("-fx-background-color: rgba(255,255,255,1);-fx-background-radius: 100;");
        }

        if(program.isClosedCaption()){
            label.setText("C");
            label.setPrefSize(15,15);
            label.setMinSize(15,15);
            label.setMaxSize(15,15);
        }

        return label;
    }

    private void setupCellNode(IProgram program, Label node, boolean playProgram){

        ContextMenu contextMenu = setupCellContextMenu(program,playProgram);

        node.getStyleClass().add(program.getProgramColor().name().toLowerCase());

        node.getStylesheets().clear();

        node.getStylesheets().add(getClass().getResource("/assets/styles/labelstyle.css").toExternalForm());

        node.setId(program.getId());

        node.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                contextMenu.show(node, event.getScreenX(), event.getScreenY());
            }

            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2){
                viewNowProgram = program;
                ScenesHelper.InvokeMediaPlayer(new Stage());
            }
        });

    }

    private ContextMenu setupCellContextMenu(IProgram program, boolean playProgram){

        ContextMenu contextMenu = new ContextMenu();

        if(playProgram){
            MenuItem watchNow = new MenuItem("Watch Now");
            contextMenu.getItems().addAll(watchNow);
            watchNow.setOnAction(e -> {
                setViewNowProgram(program);
            });
        }

        MenuItem viewLater = new MenuItem("View Later");

        MenuItem recordProgram = new MenuItem("Record Program");

        MenuItem viewMore = new MenuItem("See Full Description");

        contextMenu.getItems().addAll(viewLater,recordProgram,viewMore);

        Button progList = new Button("Navigate To Programs.");
        progList.setStyle("-fx-background-color:none;-fx-text-fill:rgba(129,212,250 ,1);");
        progList.setFont(new Font("Rockwell",14));
        progList.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                MainLayoutController.navigateToPrograms();
            }
        });

        contextMenu.getItems().forEach( i -> {
            i.setStyle("-fx-text-fill:#fff;-fx-font-size: 12px;-fx-padding:5 80 5 5;-fx-width:250px");
        });

        viewMore.setOnAction(e -> {
            viewNowProgram = program;

            ScenesHelper.InvokeProgramDescription(new Stage());
        });

        viewLater.setOnAction(e -> {
            program.setProgramStatus(ProgramStatus.ViewingLater);
            boolean success = DatabaseHelper.addMarkedProgram(program);

            if(success){
                ScenesHelper.createPopup("MainLayout.fxml","Program added to marked list successfully. You can check programs to see more.", progList);
            }
            else
            {
                ScenesHelper.createPopup("MainLayout.fxml","Failed to add program to view later.", null);
            }

        });

        recordProgram.setOnAction(e -> {
            program.setProgramStatus(ProgramStatus.Recorded);
            boolean success = DatabaseHelper.addMarkedProgram(program);
            if(success){
                ScenesHelper.createPopup("MainLayout.fxml","Program will be recorded and saved in marked programs list. You can check programs to see more.", progList);
            }
            else
            {
                ScenesHelper.createPopup("MainLayout.fxml","Failed to reocrd program.", null);
            }
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

        rowconst.setVgrow(Priority.ALWAYS);
        rowconst.setFillHeight(true);
        rowconst.setValignment(VPos.CENTER);
        rowconst.setMinHeight(40);
        rowconst.setMaxHeight(100);


        return rowconst;
    }

    public static void setViewNowProgram(IProgram program){
        viewNowProgram = program;

        if(isProgramReady(program)){
            ScenesHelper.InvokeMediaPlayer(new Stage());
        }else{
            ScenesHelper.createPopup("MainLayout.fxml","The program you are trying to view isn't available.",null);
        }
    }

    private static boolean isProgramReady(IProgram program){
        viewNowProgram = program;

        LocalDateTime currentDate = viewNowProgram.getProgramAirDateTime();

        if((TimeHelper.correctProgramDate(viewNowProgram).isAfter(LocalDateTime.now()) ||
                TimeHelper.correctProgramDate(viewNowProgram).toLocalDate().equals(LocalDateTime.now().toLocalDate())) && viewNowProgram.getDuration() > 0){
            for(int x =0; x < viewNowProgram.getDuration(); x++){
                currentDate = viewNowProgram.getProgramAirDateTime().plusDays(x);

                if(currentDate.toLocalDate().equals(LocalDateTime.now().toLocalDate())){
                    break;
                }
            }
        }

        LocalDateTime tempCurrentdate = currentDate;

        if((TimeHelper.correctProgramDate(viewNowProgram).isAfter(LocalDateTime.now()) ||
                TimeHelper.correctProgramDate(viewNowProgram).toLocalDate().equals(LocalDateTime.now().toLocalDate())) && viewNowProgram.getLength() > 0){
            for(int y =0; y < viewNowProgram.getLength(); y++){
                currentDate = tempCurrentdate.plusHours(y);
                if(currentDate.toLocalDate().equals(LocalDateTime.now().toLocalDate()) && currentDate.getHour() == LocalDateTime.now().getHour()){
                    break;
                }
            }
        }

        if( (currentDate.getHour() == LocalDateTime.now().getHour()
                && TimeHelper.correctProgramDate(viewNowProgram).isAfter(LocalDateTime.now())
                && (viewNowProgram.getProgramStatus() == null || viewNowProgram.getProgramStatus().equals(ProgramStatus.ViewingLater)
                || viewNowProgram.getProgramStatus().equals(ProgramStatus.WatchNow)) )

                || (viewNowProgram.getProgramStatus() != null && viewNowProgram.getProgramStatus().equals(ProgramStatus.Recorded)
                && viewNowProgram.getProgramAirDateTime().isBefore(LocalDateTime.now()))
        ){
            return true;
        }
        return false;
    }
}
