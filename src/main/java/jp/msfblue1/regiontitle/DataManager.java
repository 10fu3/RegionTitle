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

        if(database.exists()){
            Arrays.asList(database.listFiles()).forEach(file -> {
                YamlConfiguration Data = new YamlConfiguration();
                try {
                    Data.load(file);

                    jp.msfblue1.regiontitle.Data putdata = new Data();

                    putdata.Name = Data.getString("Name");
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
        if(Util.getWorldGuard() != null){

            return false;
        }
        return false;
    }
}
