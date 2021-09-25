package fr.robotv2.cinestiabungee.pluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import fr.robotv2.cinestiabungee.utility.rtpUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class pluginMessage implements Listener {

    private Double X;
    private Double Y;
    private Double Z;
    private Float YAW;
    private Float PITCH;
    private String WORLD;
    private String SERVEUR;


    private main main;
    public pluginMessage(main main) {
        this.main = main;
    }

    @EventHandler
    public void onMessage(PluginMessageEvent e) {
        if(e.getTag().equalsIgnoreCase(main.channel)) {
            ProxiedPlayer player = main.getProxy().getPlayer(e.getReceiver().toString());
            UUID uuid = player.getUniqueId();

            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();

            switch (sub.toLowerCase()) {
                case "sethome-player":
                    String name = in.readUTF();
                    X = in.readDouble();
                    Y = in.readDouble();
                    Z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getHome().sethome(player, name, X, Y , Z , YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setwarp-player":
                    name = in.readUTF();
                    X = in.readDouble();
                    Y = in.readDouble();
                    Z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getWarp().setwarp(name, X, Y , Z , YAW, PITCH, WORLD, SERVEUR);
                    System.out.println("oui");
                    break;

                case "setback-player":
                    X = in.readDouble();
                    Y = in.readDouble();
                    Z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getBack().set(player, X, Y, Z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setspawn-player":
                    X = in.readDouble();
                    Y = in.readDouble();
                    Z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getSpawn().setspawn(X, Y, Z, YAW, PITCH, WORLD, SERVEUR);
                    break;

                case "setfirstspawn-player":
                    X = in.readDouble();
                    Y = in.readDouble();
                    Z = in.readDouble();
                    YAW = in.readFloat();
                    PITCH = in.readFloat();
                    WORLD = in.readUTF();
                    SERVEUR = player.getServer().getInfo().getName();

                    main.getUtils().getSpawn().setFirstSpawn(X, Y, Z, YAW, PITCH, WORLD, SERVEUR);
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
                    rtpUtil.worldType world = rtpUtil.worldType.valueOf(in.readUTF());
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
