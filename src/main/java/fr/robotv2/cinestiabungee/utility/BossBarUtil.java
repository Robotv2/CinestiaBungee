package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.Main;
import fr.robotv2.cinestiabungee.bossbar.BossBar;
import fr.robotv2.cinestiabungee.bossbar.BossBarRunnable;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BossBarUtil implements Listener {

    private final Main main;
    public BossBarUtil(Main main) {
        this.main = main;
    }

    private static final HashMap<UUID, ScheduledTask> tasks = new HashMap<>();
    private static final HashMap<UUID, BossBar> bars = new HashMap<>();

    public void init(ProxiedPlayer player, int seconds, String text) {
        BossBarRunnable runnable = new BossBarRunnable(player, seconds, text);
        ScheduledTask task = main.getProxy().getScheduler().schedule(main, runnable, 0, 1, TimeUnit.SECONDS);
        runnable.setTaskId(task.getId());

        stopTask(player.getUniqueId());
        tasks.put(player.getUniqueId(), task);
    }

    public static void stopTask(UUID playerUUID) {
        if(tasks.containsKey(playerUUID)) {
            tasks.get(playerUUID).cancel();
            tasks.remove(playerUUID);
        }

        if(bars.containsKey(playerUUID)) {
            bars.get(playerUUID).removeAll();
            bars.remove(playerUUID);
        }
    }

    public static void setBossBar(ProxiedPlayer player, BossBar bar) {
        bars.put(player.getUniqueId(), bar);
    }

    public BossBar getBossBar(ProxiedPlayer player) {
        return bars.get(player.getUniqueId());
    }

    public boolean hasBossBar(ProxiedPlayer player) {
        return bars.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if(tasks.containsKey(player.getUniqueId()) && hasBossBar(player))
              getBossBar(player).addPlayer(player);
    }
}
