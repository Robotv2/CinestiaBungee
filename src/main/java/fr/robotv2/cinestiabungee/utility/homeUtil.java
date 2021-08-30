package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Set;
import java.util.UUID;

public class homeUtil {

    private main main;
    public homeUtil(main main) {
        this.main = main;
    }

    public int getTotal(ProxiedPlayer player) {
        Set<String> total = main.getHomes().get().getKeys("home." + player.getUniqueId().toString(), false);
        return (total != null ? total.size() : 0);
    }

    public int getLimit(ProxiedPlayer player) {
        int result = 0;
        for(Node perm : main.getUtils().getMain().getPermission(player)) {
            String permStr = perm.getKey();
            if(permStr.startsWith("cinestia.home.limit.")) {
                int limit = Integer.parseInt(permStr.replace("cinestia.home.limit.", ""));
                if(limit > result) {
                    result = limit;
                }
            }
        }
        return result;
    }

    public void sethome(ProxiedPlayer player, String name, Double x, Double y, Double z, Float yaw, Float pitch, String world, String serveur) {
        Config homes = main.getHomes().get();
        UUID uuid = player.getUniqueId();

        homes.set(uuid + ".homes." + name + ".X", x);
        homes.set(uuid + ".homes." + name + ".Y", y);
        homes.set(uuid + ".homes." + name + ".Z", z);
        homes.set(uuid + ".homes." + name + ".yaw", yaw);
        homes.set(uuid + ".homes." + name + ".pitch", pitch);
        homes.set(uuid + ".homes." + name + ".world", world);
        homes.set(uuid + ".homes." + name + ".serveur", serveur);
        homes.save();
    }

}
