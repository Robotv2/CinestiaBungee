package fr.robotv2.cinestiabungee.ressourcePack;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class RessourcePackListener implements Listener {

    private final Main main;
    public RessourcePackListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        main.getProxy().getScheduler().schedule(main, () -> {
            RessourcePackUtil.sendDemand(e.getPlayer());
        }, 1, TimeUnit.SECONDS);
    }
}
