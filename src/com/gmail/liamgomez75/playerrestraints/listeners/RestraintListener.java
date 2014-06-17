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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class RestraintListener implements Listener {
    private PlayerRestraints plugin;

    public RestraintListener(PlayerRestraints plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Player) {
            Player restrainedPlayer = (Player) e.getRightClicked();
            Player player = e.getPlayer();
            if(ConfigUtils.isRestrained(player, plugin)) {
                player.sendMessage("§7You can't do that while tied up!");
            } else {
                if(player.getItemInHand().getType() == Material.LEASH && player.hasPermission("pRestraint.canRestrain") && !restrainedPlayer.hasPermission("pRestrain.immune")) {
                if(!ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
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
                } else {
                    player.sendMessage("§7" + restrainedPlayer.getName() + " is already tied up.");
                }
                
                } else if(player.getItemInHand().getType() == Material.SHEARS && player.hasPermission("pRestraint.canFree") && ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
                    ConfigUtils.setPlayerRestrained(restrainedPlayer, false, plugin);
                    player.sendMessage("§7You have freed " + restrainedPlayer.getName() + ".");
                    restrainedPlayer.sendMessage("§7" + player.getName() + " has set you free.");
                } else if(player.getItemInHand().getType() == null && player.hasPermission("pRestraint.canCarry") && ConfigUtils.isRestrained(restrainedPlayer, plugin)) {
                    player.setPassenger(restrainedPlayer);
                }
            }
            
        }
    }
    
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(ConfigUtils.isRestrained(player, plugin)) {
            player.sendMessage("§7You can't do that while tied up!");
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(ConfigUtils.isRestrained(player, plugin)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if(ConfigUtils.isRestrained(player, plugin)) {
            e.setCancelled(true);
        } else if(player.isSneaking()) {
            player.setPassenger(null);
        }
        
    }
    
    
    
    
    
}
