package jp.msfblue1.regiontitle;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by msfblue1 on 2017/09/24.
 */
public class Util {

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldGuardPlugin) plugin;
    }

    public static void successPuts(CommandSender sender){
        puts(ChatColor.GREEN+"操作は成功しました",sender);
    }

    public static void puts(String mes , CommandSender sender){
        sender.sendMessage(ChatColor.GREEN+"["+ ChatColor.DARK_GRAY+"RegionTitle"+ChatColor.GREEN+"] "+ChatColor.WHITE+mes);
    }

    public static void errorPuts(List<String> mes , CommandSender sender){
        if(mes != null){
            mes.forEach(message->puts(ChatColor.RED+"ERROR "+ChatColor.WHITE+message,sender));
        }
    }

    public static void infoPuts(List<String> mes , CommandSender sender){
        if(mes != null){
            mes.forEach(message->puts(ChatColor.BLUE +"INFO "+ChatColor.WHITE+message,sender));
        }
    }

    public static void warnPuts(List<String> mes , CommandSender sender){
        if(mes != null){
            mes.forEach(message->puts(ChatColor.YELLOW +"WARN "+ChatColor.WHITE+message,sender));
        }
    }

    public static void sendHelp(CommandSender sender){
        puts(ChatColor.AQUA+ "Help -コマンド一覧",sender);
        puts("/rt reg n:保護名 t:タイトル s:サブタイトル",sender);
    }

    public static void errorAccess(CommandSender sender){
        errorPuts(Arrays.asList("アクセスはプレーヤーからしてください"),sender);
    }
}
