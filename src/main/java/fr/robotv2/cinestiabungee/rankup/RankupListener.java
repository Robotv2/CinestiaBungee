package fr.robotv2.cinestiabungee.rankup;

import fr.robotv2.cinestiabungee.Main;
import fr.robotv2.cinestiabungee.utility.RankupUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RankupListener implements Listener {

    private final Main main;
    private final RankupUtil util;
    public RankupListener(Main main) {
        this.main = main;
        this.util = main.getUtils().getRankUp();
    }

    public HashMap<ProxiedPlayer, ScheduledTask> tasks = new HashMap<>();

    @EventHandler
    public void join(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if(player == null) return;

        ScheduledTask task = main.getProxy().getScheduler().schedule(main, new Runnable() {

            int count = 0;

            @Override
            public void run() {
                if(player.getServer() != null) {
                    actualizePlayer(player);
                    tasks.get(player).cancel();
                } else {
                    count++;
                    if(count > 20)
                        tasks.get(player).cancel();
                }
            }
        }, 500, 200, TimeUnit.MILLISECONDS);
        tasks.put(player, task);
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if(player == null) return;

        ScheduledTask task = main.getProxy().getScheduler().schedule(main, new Runnable() {

            int count = 0;

            @Override
            public void run() {
                if(player.getServer() != null) {
                    actualizePlayer(player);
                    tasks.get(player).cancel();
                } else {
                    count++;
                    if(count > 20)
                        tasks.get(player).cancel();
                }
            }
        }, 500, 200, TimeUnit.MILLISECONDS);
        tasks.put(player, task);
    }

    public void actualizePlayer(ProxiedPlayer player) {
        if(!util.hasAccount(player))
            util.createAccount(player);
        util.sendLevel(player);
        util.sendExp(player);
    }
}
