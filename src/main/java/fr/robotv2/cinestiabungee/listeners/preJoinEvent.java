package fr.robotv2.cinestiabungee.listeners;

import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static fr.robotv2.cinestiabungee.utility.mainUtil.color;
import static me.devtec.bungeetheapi.TheAPI.colorize;

public class preJoinEvent implements Listener {

    int VERSION_1_16_5 = 754;

    @EventHandler
    public void onPreJoin(PreLoginEvent e) {
        if(e.getConnection().getVersion() < VERSION_1_16_5) {
            e.setCancelReason(color("&cMerci de vous connecter avec une version plus récente. [1.16.5 - 1.17.1]"));
            e.setCancelled(true);
        }
    }
}
