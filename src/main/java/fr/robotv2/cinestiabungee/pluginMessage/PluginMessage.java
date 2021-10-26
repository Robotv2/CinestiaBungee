package fr.robotv2.cinestiabungee.pluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PluginMessage implements Listener {

    private final Main main;
    public PluginMessage(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMessage(PluginMessageEvent e) {
        ProxiedPlayer player = main.getProxy().getPlayer(e.getReceiver().toString());

        final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        final String sub = in.readUTF();

        if(e.getTag().equalsIgnoreCase(Main.channel)) {

            if (sub.equalsIgnoreCase("broadcast")) {
                String broadcast = in.readUTF();
                main.getUtils().getMain().broadcast(broadcast);
            }
        }

        else if(e.getTag().equalsIgnoreCase(Main.rankup_channel)) {
            switch(sub.toLowerCase()) {
                case "set-level":
                    int level = in.readInt();
                    main.getUtils().getRankUp().setLevel(player, level);
                    break;
                case "set-exp":
                    double exp = in.readDouble();
                    main.getUtils().getRankUp().setExp(player, exp);
                    break;
            }
        }
    }
}
