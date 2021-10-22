package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SpawnUtil {

    private String serveur;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    private Main main;
    public SpawnUtil(Main main) {
        this.main = main;
    }

    public void setFirstSpawn(Double x, Double y, Double z, Float yaw, Float pitch, String world, String serveur) {
        Config spawn = main.getSpawn().get();

        spawn.set("FIRST-SPAWN.X", x);
        spawn.set("FIRST-SPAWN.Y", y);
        spawn.set("FIRST-SPAWN.Z", z);
        spawn.set("FIRST-SPAWN.yaw", yaw);
        spawn.set("FIRST-SPAWN.pitch", pitch);
        spawn.set("FIRST-SPAWN.world", world);
        spawn.set("FIRST-SPAWN.serveur", serveur);
        spawn.save();
    }

    public void teleportToFirstSpawn(ProxiedPlayer player) {
        Config spawn = main.getSpawn().get();

        x = spawn.getDouble("FIRST-SPAWN.X");
        y = spawn.getDouble("FIRST-SPAWN.Y");
        z = spawn.getDouble("FIRST-SPAWN.Z");
        yaw = spawn.getFloat("FIRST-SPAWN.yaw");
        pitch = spawn.getFloat("FIRST-SPAWN.pitch");
        world = spawn.getString("FIRST-SPAWN.world");
        serveur = spawn.getString("FIRST-SPAWN.serveur");

        main.getUtils().getMain().teleportToLocation(player, x, y, z, yaw, pitch, world, serveur, 5, null, null);
    }

    public void setspawn(Double x, Double y, Double z, Float yaw, Float pitch, String world, String serveur) {
        Config spawn = main.getSpawn().get();

        spawn.set("SPAWN.X", x);
        spawn.set("SPAWN.Y", y);
        spawn.set("SPAWN.Z", z);
        spawn.set("SPAWN.yaw", yaw);
        spawn.set("SPAWN.pitch", pitch);
        spawn.set("SPAWN.world", world);
        spawn.set("SPAWN.serveur", serveur);
        spawn.save();
    }

    public void teleportToSpawn(ProxiedPlayer player) {
        Config spawn = main.getSpawn().get();

        x = spawn.getDouble("SPAWN.X");
        y = spawn.getDouble("SPAWN.Y");
        z = spawn.getDouble("SPAWN.Z");
        yaw = spawn.getFloat("SPAWN.yaw");
        pitch = spawn.getFloat("SPAWN.pitch");
        world = spawn.getString("SPAWN.world");
        serveur = spawn.getString("SPAWN.serveur");

        main.getUtils().getMain().teleportToLocation(player, x, y, z, yaw, pitch, world, serveur, 5, MainUtil.teleportReason.SPAWN, null);
    }
}
