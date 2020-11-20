package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseHelper {

    private static Map<Integer, String> channelMap = new TreeMap<>();

    private final Gson jsonUtility = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public DatabaseHelper(){};

    public void InitializeDB(){
        loadDBContentsInMemory();
        System.out.println(saveFileContents(jsonUtility.toJson(channelMap),"channels1.json"));
    }

    private void loadDBContentsInMemory(){

        channelMap = readJson("channels.json");
    }



    public static Map<Integer, String> GetChannels(){
        return channelMap;
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

            String content = "";

            content = new String(Files.readAllBytes(Paths.get(basePath+filename)));

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
