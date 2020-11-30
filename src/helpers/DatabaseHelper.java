package helpers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import controllers.MainLayoutController;
import interfaces.IProgram;
import models.*;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseHelper {

    public static DatabaseHelper db = new DatabaseHelper();

    private static List<String> channelMap = new ArrayList<>();

    private static List<IProgram> programs = new ArrayList<>();

    private  static List<IProgram> markedPrograms = new ArrayList<>();

    private final Gson jsonUtility = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME); }

    }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }

    }).serializeNulls().setPrettyPrinting().create();

    /** Initialization */

    public DatabaseHelper(){};

    public void InitializeDB(){
        loadDBContentsInMemory();
    }

    public void loadPrograms(){
        List objects = readJson("programs.json",new TypeToken<>(){});

        if(objects == null){
            programs = new ArrayList<>();
        }

        programs = convertToIPrograms(objects);
    }

    public void loadMarkedPrograms(){
        List objects = readJson("marked_programs.json",new TypeToken<>(){});

        if(objects == null){
            markedPrograms = new ArrayList<>();
        }

        markedPrograms = convertToIPrograms(objects);

        saveFileContents(toJson(markedPrograms),"marked_programs.json");
    }

    /** Static getters for in memory data store */

    public static List<String> getChannels(){
        return channelMap;
    }

    public  static List<IProgram> getPrograms(){
        return programs;
    }

    public static List<IProgram> getMarkedPrograms(){
        return markedPrograms;
    }

    /**  Type converters */

    public static boolean addMarkedProgram(IProgram program){

        List<IProgram> result = markedPrograms.stream().filter(e -> e.getId().equals(program.getId())).collect(Collectors.toList());

        if(result!=null && result.size() > 0){
            IProgram item = result.stream().findFirst().get();
            markedPrograms.remove(item);
        }

        markedPrograms.add(program);

        boolean success = db.saveFileContents(db.toJson(markedPrograms),"marked_programs.json");

        new Thread(){
            @Override
            public void run() {
                db.loadMarkedPrograms();
            }
        }.start();

        MainLayoutController.incrementMarkedItems(markedPrograms.size());

        return success;
    }

    public static boolean removeMarkedProgram(IProgram program){

        List<IProgram> result = markedPrograms.stream().filter(e -> e.getId().equals(program.getId())).collect(Collectors.toList());

        if(result!=null && result.size() > 0){

            IProgram item = result.stream().findFirst().get();

            markedPrograms.remove(item);
        }

        boolean success = db.saveFileContents(db.toJson(markedPrograms),"marked_programs.json");

        new Thread(){
            @Override
            public void run() {
                db.loadMarkedPrograms();
            }
        }.start();

        MainLayoutController.incrementMarkedItems(markedPrograms.size());

        return success;
    }

    public static boolean addOrUpdateProgram(IProgram program) throws Exception{


        if(program.getTitle() == null || program.getTitle().isEmpty()) throw new Exception("A title is mandatory please add one.");
        if(program.getProgramType() == null || program.getProgramType().isEmpty()) throw new Exception("A program type is mandatory please add one.");

        if(program.getChannelName() == null || program.getChannelName().isEmpty()) throw new Exception("A channel is mandatory please add one.");

        if(program.getTitle() == null || program.getTitle().isEmpty()) throw new Exception("A title is mandatory please add one.");

        if(program.getProgramPhase() != null && program.getProgramPhase().equals(ProgramPhase.Repeat) && program.getDuration() <= 0){
            throw new Exception("You cannot set the program to repeat without a 'Duration'. Please set 'Duration'.");
        }

        if(program.getLength() < 0 || program.getDuration() < 0
                || program.getRating() < 0 || program.getSeverityRating() < 0
                || (program.getAgeRange() != null && program.getAgeRange().getMin() < 0)
                || (program.getAgeRange() != null && program.getAgeRange().getMax() < 0)) throw new Exception("Invalid Number. All number entered must be a whole number.");


        if(program.getProgramType().equals(ProgramType.Kids.name()) && (program.getAgeRange() == null || program.getAgeRange().getMax() < program.getAgeRange().getMin()))
            throw new Exception("Please set a valid age range. Kids program require an age range.");

        if(program.getProgramType().equals(ProgramType.Gospel.name()) && program.getDenomination() == null)
            throw new Exception("Please set a valid denomination. Gospel programs require it.");

        if(program.getProgramType().equals(ProgramType.Movie.name()) && program.getDateReleased() !=null && program.getDateReleased().toLocalDate() != null && program.getProgramAirDateTime().toLocalDate().isBefore(program.getDateReleased().toLocalDate()))
            throw new Exception("Please set a valid release date, the air date cant be before the release date.");

        List<IProgram> result = programs.stream().filter(e -> e.getId().equals(program.getId())).collect(Collectors.toList());

        if(result != null && result.size() > 0){

            return db.saveFileContents(db.toJson(programs),"programs.json");
        }

        /* Temporal Logic*/

        if(program.getProgramAirDateTime() == null || program.getProgramAirDateTime().toLocalDate().isBefore(LocalDateTime.now().toLocalDate()))
            throw new Exception("Please use a valid air date for program.");

        if(!TimeHelper.correctProgramDateLength(program).toLocalDate().equals(program.getProgramAirDateTime().toLocalDate()))
            throw new Exception("Your temporal window is exceeding the 24 hours of a day, please use a different day or adjust the times.");

        List<IProgram> subsetOfProgramsByChannel = programs.stream().filter(p -> p.getChannelName().equals(program.getChannelName())).collect(Collectors.toList());

        if(subsetOfProgramsByChannel != null && subsetOfProgramsByChannel.size() > 0){

            TreeMap<Integer,Integer> clashedTimes = new TreeMap<>();

            subsetOfProgramsByChannel.forEach(e ->{

                LocalDateTime mindatetime = e.getProgramAirDateTime();

                LocalDateTime progmindatetime = program.getProgramAirDateTime();

                LocalDateTime maxdatetime = TimeHelper.correctProgramDate(e);

                LocalDateTime progmaxdatetime = TimeHelper.correctProgramDate(program);

                int minhours = e.getProgramAirDateTime().getHour();

                int progminhours = program.getProgramAirDateTime().getHour();

                int maxhours = (int)(e.getProgramAirDateTime().getHour()+e.getLength());

                int progmaxhours = (int)(program.getProgramAirDateTime().getHour()+program.getLength());

                if((progmindatetime.isBefore(progmaxdatetime) && progmaxdatetime.isAfter(mindatetime))
                    || progmindatetime.toLocalDate().equals(mindatetime.toLocalDate())
                    || progmaxdatetime.toLocalDate().equals(maxdatetime.toLocalDate()))
                {
                    if(progminhours <= maxhours  && progmaxhours >= minhours)
                    {
                        clashedTimes.put(minhours,minhours);
                        clashedTimes.put(maxhours,maxhours);
                    }
                }

            });

            if(clashedTimes.size() > 0){

                int min = clashedTimes.firstEntry().getValue();

                int max = clashedTimes.lastEntry().getValue();

                throw new Exception("A clashed was detected with your temporal window. Please select a time range: [0-"+min+"] or ["+max+"-23] hrs.");
            }
        }

        programs.add(program);

        return db.saveFileContents(db.toJson(programs),"programs.json");
    }

    public static boolean removeProgram(IProgram program){

        List<IProgram> result = programs.stream().filter(e -> e.getId().equals(program.getId())).collect(Collectors.toList());

        if(result != null && result.size() > 0){

            IProgram item = result.stream().findFirst().get();

            programs.remove(item);
        }

        return db.saveFileContents(db.toJson(programs),"programs.json");
    }

    public static List<IProgram> convertToIPrograms(List<Object> objects){

        List<IProgram> convertedPrograms = new ArrayList<>();

        for (Object  o: objects) {

            String covertedItem = db.toJson(o);

            Program prog = db.fromJson(covertedItem,new TypeToken<>(){});

            if(prog.getProgramStatus() != null && prog.getProgramStatus().equals(ProgramStatus.ViewingLater))
            {
                try{

                    LocalDateTime dt = TimeHelper.correctProgramDate(prog);

                    if(dt.isEqual(LocalDateTime.now()) || dt.isAfter(LocalDateTime.now())){
                        convertedPrograms.add(convertToSpecifiedType(prog,o));
                    }

                }catch (Exception e){}
            }
            else
            {
                convertedPrograms.add(convertToSpecifiedType(prog,o));
            }
        }

        return convertedPrograms;
    }

    public static <T> T toType(Object item, TypeToken<T> type){

        String convertedItem = db.toJson(item);

        return db.fromJson(convertedItem,type);
    }

    public static IProgram convertToSpecifiedType(IProgram program, Object original){

        IProgram newProgram = null;

        if(program.getProgramType().equals("Comedy")){
            newProgram = toType(original,new TypeToken<Comedy>(){});
            newProgram.setProgramColor(ProgramColor.YELLOW);
        }else if(program.getProgramType().equals("General")){
            newProgram = toType(original,new TypeToken<General>(){});
            newProgram.setProgramColor(ProgramColor.WHITE);
        }else if(program.getProgramType().equals("Gospel")){
            newProgram = toType(original,new TypeToken<Gospel>(){});
            newProgram.setProgramColor(ProgramColor.BLUE);
        }else if(program.getProgramType().equals("Kids")){
            newProgram = toType(original,new TypeToken<Kids>(){});
            newProgram.setProgramColor(ProgramColor.PURPLE);
        }else if(program.getProgramType().equals("Movie")){
            newProgram = toType(original,new TypeToken<Movie>(){});
            newProgram.setProgramColor(ProgramColor.RED);
        }else if(program.getProgramType().equals("News")){
            newProgram = toType(original,new TypeToken<News>(){});
            newProgram.setProgramColor(ProgramColor.WHITE);
        }else if(program.getProgramType().equals("Weather")){
            newProgram = toType(original,new TypeToken<Weather>(){});
            newProgram.setProgramColor(ProgramColor.GREEN);
        }

        return newProgram;

    }

    public <T> T fromJson(String data, TypeToken<T> type){

        T converted = jsonUtility.fromJson(data,type.getType());

        return converted;
    }

    public <T> String toJson(T item){
        return jsonUtility.toJson(item);
    }

    public <T> T readJson(String source, TypeToken<T> token){

        String data = getFileContents(source);

        return jsonUtility.fromJson(data,token.getType());
    }


    /** File,Memory processing & handling */

    private void loadDBContentsInMemory(){

        TreeMap<Integer,String> items = readJson("channels.json",new TypeToken<>(){});

        items.forEach((key,val)-> {
            channelMap.add((key+" - "+val).toUpperCase());
        });

        loadPrograms();

        loadMarkedPrograms();

    }

    private String getFileContents(String filename){

        try{
            String basePath = Paths.get(".").normalize().toAbsolutePath().toString()+"/src/database/";

            Path path = Paths.get(basePath+filename);

            String content = "";

            if(Files.exists(path)){
                content = new String(Files.readAllBytes(path));
            }else{
                Files.createFile(path);
            }

            return content;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private boolean saveFileContents(String contents, String filename){
        String basePath = Paths.get(".").normalize().toAbsolutePath().toString()+"/src/database/";

        try{
            FileWriter writier = new FileWriter(basePath+filename);
            writier.write(contents);
            writier.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
