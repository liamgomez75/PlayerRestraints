/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmail.liamgomez75.playerrestraints;

import com.gmail.liamgomez75.playerrestraints.listeners.RestraintListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class PlayerRestraints extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RestraintListener(this), this);
        saveDefaultConfig();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pRestraint")) {
            if(args.length > 0) {
                if ((args[0].equalsIgnoreCase("reload"))) {
                    return reload(sender);
                }
            }
        }
        return false;
    }
    
    public boolean reload(CommandSender sender) {
        if (sender.hasPermission("pRestraint.admin")) {
            
            reloadConfig();
            
            sender.sendMessage("§dConfig Reloaded.");
        
        } else {
            
            sender.sendMessage("§cYou do not have permission to do this.");
        
        }
        
        return true;
    
    }
}

