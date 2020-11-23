package helpers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
import java.util.stream.Stream;

public class DatabaseHelper {

    public static DatabaseHelper db = new DatabaseHelper();

    private static Map<String, String> channelMap = new TreeMap<>();

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

    public static Map<String, String> getChannels(){
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

        Stream<IProgram> result = markedPrograms.stream().filter(e -> e.getId().equals(program.getId()));

        if(result!=null && result.collect(Collectors.toList()).size() > 0){
            IProgram item = result.findFirst().get();
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

        System.out.println(db.toJson(markedPrograms));

        return success;
    }

    public static boolean addOrUpdateProgram(IProgram program){

        Stream<IProgram> result = programs.stream().filter(e -> e.getId().equals(program.getId()));

        if(result.count() > 0) return false;

        programs.add(program);

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
        if(program.getProgramType().equals("Comedy")){
            return toType(original,new TypeToken<Comedy>(){});
        }else if(program.getProgramType().equals("General")){
            return toType(original,new TypeToken<General>(){});
        }else if(program.getProgramType().equals("Gospel")){
            return toType(original,new TypeToken<Gospel>(){});
        }else if(program.getProgramType().equals("Kids")){
            return toType(original,new TypeToken<Kids>(){});
        }else if(program.getProgramType().equals("Movie")){
            return toType(original,new TypeToken<Movie>(){});
        }else if(program.getProgramType().equals("News")){
            return toType(original,new TypeToken<News>(){});
        }else if(program.getProgramType().equals("Weather")){
            return toType(original,new TypeToken<General>(){});
        }

        return toType(original,new TypeToken<Program>(){});

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

        channelMap = readJson("channels.json",new TypeToken<>(){});

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
