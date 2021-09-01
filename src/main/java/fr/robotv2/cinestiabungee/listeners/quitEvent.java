package fr.robotv2.cinestiabungee.listeners;

import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class quitEvent implements Listener {

    private main main;
    public quitEvent(main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        main.getUtils().getBack().clear(e.getPlayer());
    }
}
