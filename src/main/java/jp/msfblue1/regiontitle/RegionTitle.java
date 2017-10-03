package jp.msfblue1.regiontitle;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static jp.msfblue1.regiontitle.Util.getWorldGuard;


public final class RegionTitle extends JavaPlugin implements Listener{



    Map<Player,ProtectedRegion> inPlayer = new HashMap<>();
    //List<Player> players = new ArrayList<>();
    List<Data> configs = new ArrayList<>();

    @Override
    public void onEnable() {
        //this.saveDefaultConfig();
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
        Util.infoPuts(Arrays.asList(ChatColor.GREEN+"終了しました"),Bukkit.getConsoleSender());
    }

    @EventHandler
    public void getPlayerMove(PlayerMoveEvent e){
        RegionContainer container = getWorldGuard().getRegionContainer();
        RegionQuery query = container.createQuery();

        for(ProtectedRegion reg : query.getApplicableRegions(e.getTo()).getRegions()){
            for(Data conf : configs){
                if(conf.RegionName.equalsIgnoreCase(reg.getId())){
                    if(!inPlayer.containsKey(e.getPlayer())){
                        inPlayer.put(e.getPlayer(),reg);
                        String t = ChatColor.translateAlternateColorCodes('&', conf.Title);
                        String s = ChatColor.translateAlternateColorCodes('&', conf.SubTitle);
                        e.getPlayer().sendTitle(t,s,7,25,7);
                    }
                }else if(inPlayer.containsKey(e.getPlayer())){
                    inPlayer.remove(e.getPlayer());
                }
            }
        }
        if(query.getApplicableRegions(e.getTo()).getRegions().size() == 0){
            inPlayer.remove(e.getPlayer());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("rt".equalsIgnoreCase(command.getName()) && sender.hasPermission("regiontitle.reg")){
            if(args.length > 0){
                if("reg".equalsIgnoreCase(args[0]) && args.length > 3){
                    Data data = new Data();
                    for (int i = 1; i < 4; i++) {
                        if(args[i].startsWith("n:")){
                            String word = args[i].replaceFirst("n:","");
                            data.RegionName = word;
                            //Util.puts(word,sender);
                        }else if(args[i].startsWith("t:")){
                            String word = args[i].replaceFirst("t:","");
                            data.Title = word;
                            //Util.puts(word,sender);
                        }else if(args[i].startsWith("s:")){
                            String word = args[i].replaceFirst("s:","");
                            data.SubTitle = word;
                            //Util.puts(word,sender);
                        }
                    }
                    if(DataManager.saveData(data)){
                        Util.successPuts(sender);
                        configs.addAll(DataManager.getRegionDatas(this));
                        return true;
                    }else{
                        Util.warnPuts(Arrays.asList(ChatColor.RED+"操作は失敗しました。理由 : ファイルの作成・書き込みに失敗。"),sender);
                        return true;
                    }
                }
            }
            Util.sendHelp(sender);
            return true;
        }
        return true;
    }
}
