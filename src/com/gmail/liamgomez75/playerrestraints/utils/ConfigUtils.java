
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
    
    /**
     * 
     * @param player The player that is being restrained or set free
     * @param restrained Value to change whether or not the player is free or restrained
     * @param plugin The plugin that controls the config for storing values
     */
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
    
    /**
     * 
     * @param player The player that is being checked to see if they are restrained
     * @param plugin The plugin that stores and controls the config values
     * @return  returns if the player is either restrained or free with a true or false value that is stored in the config
     */
    public static boolean isRestrained(Player player, Plugin plugin) {
        UUID playerID = player.getUniqueId();
        final String path = "Users." + playerID.toString() + "." + RESTRAINED_CONFIG_STRING;
        return plugin.getConfig().getBoolean(path);
    }
    
    /**
     * 
     * @param player The player that is having their name stored in the config
     * @param plugin The plugin that controls and stores information into the config
     */
    public static void setPlayerName(Player player, Plugin plugin) {
        UUID playerID = player.getUniqueId();
        final String NAME = player.getName();
        final String path = "Users." + playerID.toString() + "." + RESTRAINED_CONFIG_STRING;
        plugin.getConfig().set (path, NAME);
        plugin.saveConfig();
    }
}
