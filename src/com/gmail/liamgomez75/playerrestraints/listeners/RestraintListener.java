/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmail.liamgomez75.playerrestraints.listeners;

import com.gmail.liamgomez75.playerrestraints.PlayerRestraints;
import com.gmail.liamgomez75.playerrestraints.utils.ConfigUtils;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class RestraintListener implements Listener {
    private PlayerRestraints plugin;

    /**
     * 
     * @param plugin The plugin that is responsible for the actions in this class. 
     */
    public RestraintListener(PlayerRestraints plugin) {
        this.plugin = plugin;
    }
    
    /**
     * 
     * @param e The event that takes place when a player has interacted with an entity
     * In this case I am testing to see if the player has interacted with another player
     */
    
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Player) {
            Player restrainedPlayer = (Player) e.getRightClicked();
            Player player = e.getPlayer();
            if(ConfigUtils.isRestrained(player, plugin)) {
                player.sendMessage("§7You can't do that while tied up!");
            } else {
                performAction(player, restrainedPlayer);
            }
            
        }
    }
    
    /**
     * 
     * @param player The player that is restraining the restrained player
     * @param restrainedPlayer The player that is being restrained or is at risk of being restrained
     */

    private void performAction(Player player, Player restrainedPlayer) {
        if(player.getItemInHand().getType() == Material.LEASH && player.hasPermission("pRestraint.canRestrain") && !restrainedPlayer.hasPermission("pRestrain.immune")) {
            if(!ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
                restrainPlayer(restrainedPlayer, player);
            } else {
                player.sendMessage("§7" + restrainedPlayer.getName() + " is already tied up.");
            }
        } else if(player.getItemInHand().getType() == Material.SHEARS && player.hasPermission("pRestraint.canFree") && ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
            ConfigUtils.setPlayerRestrained(restrainedPlayer, false, plugin);
            player.sendMessage("§7You have freed " + restrainedPlayer.getName() + ".");
            restrainedPlayer.sendMessage("§7" + player.getName() + " has set you free.");
        } else if(player.getItemInHand().getType() == Material.AIR && player.hasPermission("pRestraint.canCarry") && ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
            player.setPassenger(restrainedPlayer);
        }
    }

    /**
     * 
     * @param restrainedPlayer The player that is being restrained or at risk of being restrained
     * @param player The player that is restraining the restrainedPlayer
     */
    
    private void restrainPlayer(Player restrainedPlayer, Player player) {
        
        ConfigUtils.setPlayerRestrained(restrainedPlayer, true, plugin);
        
        player.sendMessage("§7You have detained " + restrainedPlayer.getName() + ".");
        
        restrainedPlayer.sendMessage("§7" + player.getName() + " has tied you up.");
        
        ItemStack hand = player.getItemInHand();
        
        if(hand != null) {
            if(hand.getAmount() > 1) {
                hand.setAmount(hand.getAmount() - 1);
                player.setItemInHand(hand);
            } else {
                player.setItemInHand(null);
            }
        }
    }
    
    /**
     * 
     * @param e The event that takes place when a player interacts with the world around him.
     */
    
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(ConfigUtils.isRestrained(player, plugin)) {
            player.sendMessage("§7You can't do that while tied up!");
            e.setCancelled(true);
        }
    }
    
    /**
     * 
     * @param e The event that takes place when an entity is damaged
     */
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(ConfigUtils.isRestrained(player, plugin)) {
                e.setCancelled(true);
            }
        }
    }
    
    
    /**
     * 
     * @param e The event that takes place when a player moves
     */
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(ConfigUtils.isRestrained(player, plugin)) {
            player.setVelocity(new Vector().zero());
        } else if(player.isSneaking()) {
            player.eject();
        }
    }
    
    
    
    
    
}
