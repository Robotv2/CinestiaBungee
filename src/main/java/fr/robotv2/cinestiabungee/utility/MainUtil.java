package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class MainUtil {

    private final Main main;

    public MainUtil(Main main) {
        this.main = main;
    }

    public enum teleportReason {
        WARP, HOME, SPAWN, BACK;
    }

    public void sendMessage(CommandSender player, String message, boolean prefix) {
        TextComponent msg;
        if(prefix) {
            msg = new TextComponent(("&3&lCINESTIA &7&l» " + message).replace('&', ChatColor.COLOR_CHAR));
        } else {
            msg = new TextComponent(message.replace('&', ChatColor.COLOR_CHAR));
        }
        player.sendMessage(msg);
    }

    public Collection<Node> getPermission(ProxiedPlayer player) {
        User user = main.getLuckperms().getPlayerAdapter(ProxiedPlayer.class).getUser(player);
        return user.getNodes();
    }

    public void showBanner(Long current) {
        main.getLogger().info("§8==============================================");
        main.getLogger().info("");
        main.getLogger().info("§7   ___ _             _   _        ___ _   _ _  _  ___ ___ ___ ");
        main.getLogger().info("§7  / __(_)_ _  ___ __| |_(_)__ _  | _ ) | | | \\| |/ __| __| __|");
        main.getLogger().info("§7 | (__| | ' \\/ -_|_-<  _| / _` | | _ \\ |_| | .` | (_ | _|| _| ");
        main.getLogger().info("§7  \\___|_|_||_\\___/__/\\__|_\\__,_| |___/\\___/|_|\\_|\\___|___|___|");
        main.getLogger().info("");
        main.getLogger().info("§7Le plugin a été chargé en §e" + (System.currentTimeMillis() - current) + " MS");
        main.getLogger().info("");
        main.getLogger().info("§8==============================================");
    }

    public void broadcast(String message) {
        for(ProxiedPlayer p : main.getProxy().getPlayers()) {
            main.getUtils().getMain().sendMessage(p, message, false);
        }
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
