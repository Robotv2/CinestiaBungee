package fr.robotv2.cinestiabungee.discord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class discordMessage implements Listener {

    private main main;
    public discordMessage(main main) {
        this.main = main;
    }

    @EventHandler
    public void onDiscordMessage(PluginMessageEvent e) {
        if(e.getTag().equalsIgnoreCase(main.channel)) {
            ProxiedPlayer player = main.getProxy().getPlayer(e.getReceiver().toString());
            UUID uuid = player.getUniqueId();

            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();
            switch(sub.toLowerCase()) {

            }
        }
    }
}
