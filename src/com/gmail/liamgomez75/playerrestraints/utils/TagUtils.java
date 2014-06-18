
package com.gmail.liamgomez75.playerrestraints.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.TagAPI;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public abstract class TagUtils {
    public static boolean isEnabled(Plugin plugin) {
        return plugin.getServer().getPluginManager().isPluginEnabled("TagAPI");
    }
    
    
    public static void refreshPlayer(Player player, Plugin plugin) {
        if (isEnabled(plugin)) {
            TagAPI.refreshPlayer(player);
        }
    }
    
    
    public static void refreshPlayer(Player player, Player forWhom, Plugin plugin) {
        if (isEnabled(plugin)) {
            TagAPI.refreshPlayer(player, forWhom);
        }
    }
    
    
    public static void refreshPlayer(Player player, Set<Player> forWhom, Plugin plugin) {
        if (isEnabled(plugin)) {
            TagAPI.refreshPlayer(player, forWhom);
        }
    }
    
    public static void reload(Plugin plugin) {
        if (isEnabled(plugin)) {
            final int maxPlayerViewDistance = plugin.getServer().getViewDistance() * 16;

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                final int maxWorldHeight = player.getWorld().getMaxHeight();
                final Set<Player> tagsToUpdate = new HashSet<>();

                final List<Entity> nearbyEntities = player.getNearbyEntities(maxPlayerViewDistance, maxWorldHeight, maxPlayerViewDistance);
                for (Entity e : nearbyEntities) {
                    if (e instanceof Player) {
                        final Player p = (Player) e;
                        if (player.canSee(p)) {
                            tagsToUpdate.add(p);
                        }
                    }
                }

                if (!tagsToUpdate.isEmpty()) {
                    TagAPI.refreshPlayer(player, tagsToUpdate);
                }
            }
        }
    }
}
