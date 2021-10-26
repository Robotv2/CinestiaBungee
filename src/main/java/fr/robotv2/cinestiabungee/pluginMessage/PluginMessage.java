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
        if(e.getTag().equalsIgnoreCase(Main.channel)) {
            ProxiedPlayer player = main.getProxy().getPlayer(e.getReceiver().toString());
            UUID uuid = player.getUniqueId();

            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();

            switch (sub.toLowerCase()) {
                case "sethome-player":
                    String name = in.readUTF();
                    double x = in.readDouble();
                    double y = in.readDouble();
                    double z = in.readDouble();
                    float YAW = in.readFloat();
                    float PITCH = in.readFloat();
                    String WORLD = in.readUTF();
                    String SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getHome().sethome(player, name, x, y, z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setwarp-player":
                    name = in.readUTF();
                    x = in.readDouble();
                    y = in.readDouble();
                    z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getWarp().setwarp(name, x, y, z, YAW, PITCH, WORLD, SERVEUR);
                    System.out.println("oui");
                    break;

                case "setback-player":
                    x = in.readDouble();
                    y = in.readDouble();
                    z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getBack().setBack(player, x, y, z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setspawn-player":
                    x = in.readDouble();
                    y = in.readDouble();
                    z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getSpawn().setspawn(x, y, z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setfirstspawn-player":
                    x = in.readDouble();
                    y = in.readDouble();
                    z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getSpawn().setFirstSpawn(x, y, z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "command":
                    main.getProxy().getPluginManager().dispatchCommand(player, in.readUTF());
                    break;

                case "advancement-player":
                    String advancement = in.readUTF();
                    main.getUtils().getAdv().addAdvancement(player, advancement);
                    break;

                case "get-advancement":
                    main.getUtils().getAdv().initPlayer(player);
                    break;

                case "prepare-rtp":
                    RtpUtil.WorldType world = RtpUtil.WorldType.valueOf(in.readUTF());
                    main.getUtils().getRtp().rtpPlayer(player, world);
                    break;

                case "stop-player":
                    main.getUtils().getAdv().stop(player);
                    break;

                case "broadcast":
                    String broadcast = in.readUTF();
                    main.getUtils().getMain().broadcast(broadcast);
                    break;

            }
            return;
        }
        if(e.getTag().equalsIgnoreCase(main.rankup_channel)) {
            ProxiedPlayer player = main.getProxy().getPlayer(e.getReceiver().toString());

            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();

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
