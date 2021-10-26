package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BackUtil {

    private String serveur;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    private Main main;
    public BackUtil(Main main) {
        this.main = main;
    }

    public void setBack(ProxiedPlayer player, Double x, Double y, Double z, Float yaw, Float pitch, String world, String serveur) {
        Config backs = main.getBack().get();
        UUID uuid = player.getUniqueId();

        backs.set(uuid + ".X", x);
        backs.set(uuid + ".Y", y);
        backs.set(uuid + ".Z", z);
        backs.set(uuid + ".yaw", yaw);
        backs.set(uuid + ".pitch", pitch);
        backs.set(uuid + ".world", world);
        backs.set(uuid + ".serveur", serveur);
        backs.save();
    }

    public void clear(ProxiedPlayer player) {
        Config backs = main.getBack().get();
        UUID uuid = player.getUniqueId();

        backs.set(uuid + ".X", null);
        backs.set(uuid + ".Y", null);
        backs.set(uuid + ".Z", null);
        backs.set(uuid + ".yaw", null);
        backs.set(uuid + ".pitch", null);
        backs.set(uuid + ".world", null);
        backs.set(uuid + ".serveur", null);
        backs.save();
    }

    public boolean has(ProxiedPlayer player) {
        return main.getBack().get().get(player.getUniqueId() + ".X") != null;
    }

    public void teleportToBack(ProxiedPlayer player) {
        if(!has(player)) return;

        UUID uuid = player.getUniqueId();
        Config backs = main.getBack().get();
        x = backs.getDouble(uuid + ".X");
        y = backs.getDouble(uuid + ".Y");
        z = backs.getDouble(uuid + ".Z");
        yaw = backs.getFloat(uuid + ".yaw");
        pitch = backs.getFloat(uuid + ".pitch");
        world = backs.getString(uuid + ".world");
        serveur = backs.getString(uuid + ".serveur");

        main.getUtils().getMain().teleportToLocation(player, x, y, z, yaw, pitch, world, serveur, 5, MainUtil.teleportReason.BACK, null);
    }
}
