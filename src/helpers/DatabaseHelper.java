package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
    }

    private void loadDBContentsInMemory(){

        TreeMap<String,Integer> channels = readJson("channels.json", new TypeToken<TreeMap<String,Integer>>(){});

        channels.forEach((key,val) -> {
            channelMap.put(val,key);
        });


    }



    public static Map<Integer, String> GetChannels(){
        return channelMap;
    }










    private <T> T readJson(String source, TypeToken<T> token){

        String data = getFileContents(source);

        Type type = token.getType();

        return jsonUtility.fromJson(data,type);
    }


    private String getFileContents(String source){

        try{
            String basePath = Paths.get(".").normalize().toAbsolutePath().toString()+"/src/database/";

            String content = "";

            content = new String(Files.readAllBytes(Paths.get(basePath+source)));

            return content;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
