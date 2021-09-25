package fr.robotv2.cinestiabungee.rankup;

import fr.robotv2.cinestiabungee.main;
import fr.robotv2.cinestiabungee.utility.rankupUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class rankupListener implements Listener {

    private main main;
    private rankupUtil util;
    public rankupListener(main main) {
        this.main = main;
        this.util = main.getUtils().getRankUp();
    }

    @EventHandler
    public void join(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if(player == null) return;
        main.getProxy().getScheduler().schedule(main, () -> {
            if(!util.hasAccount(player))
                util.createAccount(player);
            util.sendLevel(player);
            util.sendExp(player);
        }, 500, TimeUnit.MILLISECONDS);
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if(player == null) return;
        main.getProxy().getScheduler().schedule(main, () -> {
            if(!util.hasAccount(player))
                util.createAccount(player);
            util.sendLevel(player);
            util.sendExp(player);
        }, 500, TimeUnit.MILLISECONDS);    }
}
