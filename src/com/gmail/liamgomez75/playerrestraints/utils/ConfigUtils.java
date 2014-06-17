
package com.gmail.liamgomez75.playerrestraints.utils;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public abstract class ConfigUtils {
    public static final String RESTRAINED_CONFIG_STRING = "Restrained";
    
    public static void setPlayerRestrained(Player player, Boolean restrained, Plugin plugin) {
        UUID playerID = player.getUniqueId();
        final String path = "Users." + playerID.toString() + "." + RESTRAINED_CONFIG_STRING;
        plugin.getConfig().set(path, restrained);
        plugin.saveConfig();
        if(isRestrained(player, plugin)) {
            player.setDisplayName(ChatColor.GOLD + player.getName() + " (Tied up)");
        } else {
            player.setDisplayName(player.getName());
        }
    }
    
    public static boolean isRestrained(Player player, Plugin plugin) {
        UUID playerID = player.getUniqueId();
        final String path = "Users." + playerID.toString() + "." + RESTRAINED_CONFIG_STRING;
        return plugin.getConfig().getBoolean(path);
    }
    
    public static void setPlayerName(Player player, Plugin plugin) {
        UUID playerID = player.getUniqueId();
        final String NAME = player.getName();
        final String path = "Users." + playerID.toString() + "." + RESTRAINED_CONFIG_STRING;
        plugin.getConfig().set (path, NAME);
        plugin.saveConfig();
    }
}
