/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmail.liamgomez75.playerrestraints;

import com.gmail.liamgomez75.playerrestraints.listeners.RestraintListener;
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
}
