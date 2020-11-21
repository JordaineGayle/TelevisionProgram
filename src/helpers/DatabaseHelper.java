package helpers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseHelper {

    public static DatabaseHelper db = new DatabaseHelper();

    private static Map<String, String> channelMap = new TreeMap<>();

    private static List<Object> programs = new ArrayList<>();

    private  static List<Object> markedPrograms = new ArrayList<>();

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
        channelMap = readJson("channels.json",new TypeToken<>(){});
        programs = readJson("programs.json",new TypeToken<>(){});
        markedPrograms = readJson("marked_programs.json",new TypeToken<>(){});
    }







    public static Map<String, String> getChannels(){
        return channelMap;
    }

    public  static List<Object> getPrograms(){
        return programs;
    }

    public static List<Object> getMarkedPrograms(){
        return markedPrograms;
    }

    public static <T> T toType(Object item, TypeToken<T> type){

        String covertedItem = db.toJson(item);

        return db.fromJson(covertedItem,type);
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
