package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DatabaseHelper {

    private static Map<String, String> channelMap = new TreeMap<>();

    private static List<Object> programs = new ArrayList<>();

    private  static List<Object> markedPrograms = new ArrayList<>();

    private final Gson jsonUtility = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public DatabaseHelper(){};

    public void InitializeDB(){
        loadDBContentsInMemory();
    }

    private void loadDBContentsInMemory(){
        channelMap = readJson("channels.json");
        programs = readJson("programs.json");
        markedPrograms = readJson("marked_programs.json");
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








    private <T> T readJson(String source){

        String data = getFileContents(source);

        Type type = new TypeToken<T>(){}.getType();

        return jsonUtility.fromJson(data,type);
    }

    private <T> String toJson(T item){
        return jsonUtility.toJson(item,new TypeToken<T>(){}.getType());
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
