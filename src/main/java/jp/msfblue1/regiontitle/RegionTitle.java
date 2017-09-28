package jp.msfblue1.regiontitle;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jp.msfblue1.regiontitle.Util.getWorldGuard;


public final class RegionTitle extends JavaPlugin implements Listener{



    public List<Player> players = new ArrayList<>();
    List<Data> configs = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        configs.addAll(DataManager.getRegionDatas(this));
        if(getWorldGuard() == null){
            Util.errorPuts(Arrays.asList("ワールドガードを導入してください。"), Bukkit.getConsoleSender());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void getPlayerMove(PlayerMoveEvent e){
        RegionContainer container = getWorldGuard().getRegionContainer();
        RegionQuery query = container.createQuery();

        for(ProtectedRegion reg : query.getApplicableRegions(e.getTo()).getRegions()){
            for(Data conf : configs){
                if(conf.Name.equalsIgnoreCase(reg.getId())){
                    if(!players.contains(e.getPlayer())){
                        players.add(e.getPlayer());
                        e.getPlayer().sendTitle(conf.Title,conf.SubTitle,5,7,5);
                    }
                }else if(players.contains(e.getPlayer())){
                    players.remove(e.getPlayer());
                }
            }
        }
        if(query.getApplicableRegions(e.getTo()).getRegions().size() == 0 && players.contains(e.getPlayer())){
            players.remove(e.getPlayer());
        }
    }


}
