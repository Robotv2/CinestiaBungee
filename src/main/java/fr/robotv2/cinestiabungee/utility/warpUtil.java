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
        return new ArrayList<>(main.getWarps().get().getKeys("warps", false));
    }

    public Set<String> getAccessibleWarps(ProxiedPlayer player) {
        Set<String> result = new HashSet<String>();
        for(String warp : getWarps()) {
            if(main.hasPermission(player, "cinestia.warp." + warp.toLowerCase()))
                result.add(warp);
        }
        return result;
    }

    public boolean hasAccess(ProxiedPlayer player, String name) {
        return getAccessibleWarps(player).contains(name);
    }

    public void setwarp(String name, Double X, Double Y, Double Z, Float YAW, Float PITCH, String WORLD, String SERVER) {
        Config warps = main.getWarps().get();
        warps.set("warps." + name + ".X", X);
        warps.set("warps." + name + ".Y", Y);
        warps.set("warps." + name + ".Z", Z);
        warps.set("warps." + name + ".WORLD", WORLD);
        warps.set("warps." + name + ".YAW", YAW);
        warps.set("warps." + name + ".PITCH", PITCH);
        warps.set("warps." + name + ".SERVER", SERVER);
        warps.save();
    }
}
