package jp.msfblue1.regiontitle;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by msfblue1 on 2017/09/24.
 */
public class DataManager {

    private static File database = new File("plugins/RegionTitle/DataBase");

    public static List<Data> getRegionDatas(Plugin pl){
        List<Data> Datas = new ArrayList<>();
        if(!database.exists()){
            createDataBase();
        }else{
            Arrays.asList(database.listFiles()).forEach(file -> {
                YamlConfiguration Data = new YamlConfiguration();
                try {
                    Data.load(file);

                    jp.msfblue1.regiontitle.Data putdata = new Data();

                    putdata.RegionName = Data.getString("RegionName");
                    putdata.Title = Data.getString("Title");
                    putdata.SubTitle = Data.getString("SubTitle");

                    Datas.add(putdata);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            });
        }
        return Datas;
    }

    public static void createDataBase(){
        if(!database.exists()){
            database.mkdirs();
        }
    }

    public static boolean saveData(Data data){
        if(saveData(data.RegionName,data.Title,data.SubTitle)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean saveData(String RegName,String Title,String SubTitle){
        YamlConfiguration Data = new YamlConfiguration();
        Data.set("RegionName",RegName);
        Data.set("Title",Title);
        Data.set("SubTitle",SubTitle);
        try {
            Data.save(new File(database,RegName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
