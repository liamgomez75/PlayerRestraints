/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmail.liamgomez75.playerrestraints.listeners;

import com.gmail.liamgomez75.playerrestraints.PlayerRestraints;
import com.gmail.liamgomez75.playerrestraints.utils.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class TagListener implements Listener {
    private PlayerRestraints plugin;
    
    public TagListener(PlayerRestraints plugin) {
        this.plugin = plugin;
    }
    
    public void onNameTag(AsyncPlayerReceiveNameTagEvent e) {
        Player namedPlayer = e.getNamedPlayer();
        if(ConfigUtils.isRestrained(namedPlayer, plugin)) {
            e.setTag("§6" + namedPlayer.getName());
        } else {
            e.setTag(namedPlayer.getName());
        }
    }
}
