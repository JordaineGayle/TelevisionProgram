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
import java.util.stream.Stream;

public class DatabaseHelper {

    public static DatabaseHelper db = new DatabaseHelper();

    private static Map<String, String> channelMap = new TreeMap<>();

    private static List programs = new ArrayList();

    private  static List markedPrograms = new ArrayList();

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

    public DatabaseHelper(){};

    public void InitializeDB(){
        loadDBContentsInMemory();
    }

    private void loadDBContentsInMemory(){

        new Thread(){
            @Override
            public void run() {
                try{
                    channelMap = readJson("channels.json",new TypeToken<>(){});
                }catch (Exception e){}
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try{
                    programs = readJson("programs.json",new TypeToken<>(){});

                    if(programs == null){
                        programs = new ArrayList();
                    }
                }catch (Exception e){}
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try{
                    markedPrograms = readJson("marked_programs.json",new TypeToken<>(){});

                    if(markedPrograms == null){
                        markedPrograms = new ArrayList();
                    }
                }catch (Exception e){}
            }
        }.start();


    }







    public static Map<String, String> getChannels(){
        return channelMap;
    }

    public  static List getPrograms(){
        return programs;
    }

    public static List getMarkedPrograms(){
        return markedPrograms;
    }

    public static <T> T toType(Object item, TypeToken<T> type){

        String convertedItem = db.toJson(item);

        return db.fromJson(convertedItem,type);
    }

    public static <T extends IProgram> T convertToSpecifiedType(IProgram program, TypeToken<T> type){

        for(Object o : programs){

            String covertedItem = db.toJson(o);

            T t = db.fromJson(covertedItem,type);

            if (t.getId().equals(program.getId())){
                return t;
            }
        }

        return null;
    }

    public static <T extends IProgram> boolean addMarkedProgram(T program){

        ArrayList<Program> markedItems = toType(markedPrograms,new TypeToken<>(){});

        Stream<Program> result = markedItems.stream().filter(e -> e.getId().equals(program.getId()));

        if(result.count() > 0) return false;

        markedPrograms.add(program);

        System.out.println(markedPrograms);

        return db.saveFileContents(db.toJson(markedPrograms),"marked_programs.json");
    }

    public static IProgram convertToSpecifiedType(IProgram program){
        if(program.getProgramType().equals("Comedy")){
            return convertToSpecifiedType(program,new TypeToken<Comedy>(){});
        }else if(program.getProgramType().equals("General")){
            return convertToSpecifiedType(program,new TypeToken<General>(){});
        }else if(program.getProgramType().equals("Gospel")){
            return convertToSpecifiedType(program,new TypeToken<Gospel>(){});
        }else if(program.getProgramType().equals("Kids")){
            return convertToSpecifiedType(program,new TypeToken<Kids>(){});
        }else if(program.getProgramType().equals("Movie")){
            return convertToSpecifiedType(program,new TypeToken<Movie>(){});
        }else if(program.getProgramType().equals("News")){
            return convertToSpecifiedType(program,new TypeToken<News>(){});
        }else if(program.getProgramType().equals("Weather")){
            return convertToSpecifiedType(program,new TypeToken<General>(){});
        }

        return convertToSpecifiedType(program,new TypeToken<Program>(){});
    }

    public <T> T readJson(String source, TypeToken<T> token){

        String data = getFileContents(source);

        return jsonUtility.fromJson(data,token.getType());
    }


    public <T> T fromJson(String data, TypeToken<T> type){

        T converted = jsonUtility.fromJson(data,type.getType());

        return converted;
    }


    public <T> String toJson(T item){
        return jsonUtility.toJson(item);
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
