package fr.robotv2.cinestiabungee.listeners;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitEvent implements Listener {

    private Main main;
    public QuitEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        main.getUtils().getBack().clear(e.getPlayer());
    }
}
