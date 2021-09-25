package fr.robotv2.cinestiabungee.discord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class discordListener implements Listener {

    private final HashMap<ProxiedPlayer, ScheduledTask> tasks = new HashMap<>();

    private main main;
    public discordListener(main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        ScheduledTask task = main.getProxy().getScheduler().schedule(main, () -> {
            if(player != null) {
                if(!main.hasPermission(player, "cinestia.discord.silentjoin"))
                    sendToBukkit(player, discordType.JOIN);
                tasks.get(player).cancel();
                tasks.remove(player);
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        tasks.put(player, task);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();

        if(!main.hasPermission(player, "cinestia.discord.silentquit"))
            sendToBukkit(e.getPlayer(), discordType.QUIT);
    }

    public void sendToBukkit(ProxiedPlayer player, discordType type) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("discord-" + type);
        out.writeInt(main.getProxy().getOnlineCount());
        try {
            player.getServer().sendData(main.channel, out.toByteArray());
        } catch (NullPointerException ignored) {
        }

    }
}
