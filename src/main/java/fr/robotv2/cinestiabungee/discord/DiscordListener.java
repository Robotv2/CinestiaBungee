package fr.robotv2.cinestiabungee.discord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DiscordListener implements Listener {

    private final HashMap<ProxiedPlayer, ScheduledTask> tasks = new HashMap<>();

    private Main main;
    public DiscordListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        ScheduledTask task = main.getProxy().getScheduler().schedule(main, () -> {
            if(player != null) {
                if(!main.hasPermission(player, "cinestia.discord.silentjoin"))
                    sendToBukkit(player, DiscordType.JOIN);
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
            sendToBukkit(e.getPlayer(), DiscordType.QUIT);
    }

    public void sendToBukkit(ProxiedPlayer player, DiscordType type) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("discord-" + type);
        out.writeInt(main.getProxy().getOnlineCount());
        try {
            player.getServer().sendData(main.channel, out.toByteArray());
        } catch (NullPointerException ignored) {
        }
    }
}
