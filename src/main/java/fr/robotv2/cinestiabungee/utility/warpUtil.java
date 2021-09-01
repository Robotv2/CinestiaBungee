package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class warpUtil {

    private main main;
    public warpUtil(main main) {
        this.main = main;
    }

    public List<String> getWarps() {
        return new ArrayList<String>(main.getWarps().get().getKeys("warps", false));
    }

    public Set<String> getAccessibleWarps(ProxiedPlayer player) {
        Set<String> result = new HashSet<String>();
        for(String warp : getWarps()) {
            if(player.hasPermission("cinestia.warp." + warp))
                result.add(warp);
        }
        return result;
    }

    public boolean hasAccess(ProxiedPlayer player, String name) {
        Set<String> warps = getAccessibleWarps(player);
        return !warps.contains(name) || !player.hasPermission("cinestia.warp." + name);
    }

    public void setwarp(ProxiedPlayer player, String name, Double x, Double y, Double z, Float yaw, Float pitch, String world, String serveur) {
        Config warps = main.getWarps().get();
        warps.set("warps." + name + ".X", x);
        warps.set("warps." + name + ".Y", y);
        warps.set("warps." + name + ".Z", z);

        warps.set("warps." + name + ".monde", world);

        warps.set("warps." + name + ".yaw", yaw);
        warps.set("warps." + name + ".pitch", pitch);
        warps.set("warps." + name + ".serveur", serveur);
        warps.save();
    }
}
