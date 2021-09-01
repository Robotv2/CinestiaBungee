package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
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

public class mainUtil {

    private final main main;
    public TextComponent prefix;

    public mainUtil(main main) {
        this.main = main;
        setupPrefix();
    }

    public void setupPrefix() {
        prefix = new TextComponent("CINESTIA");
        prefix.setColor(ChatColor.of("#29e3e0"));
        prefix.setBold(true);
    }

    public void sendMessage(CommandSender player, String message, boolean prefix) {
        TextComponent msg = this.prefix.duplicate();
        if(prefix) {
            msg.addExtra((" &7&l» " + message).replace('&', ChatColor.COLOR_CHAR));
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

    public void teleportToLocation(ProxiedPlayer player, Double X, Double Y, Double Z, Float yaw, Float pitch, String world, String server) {
        ServerInfo srv = ProxyServer.getInstance().getServerInfo(server);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("initialize-teleport");
        out.writeUTF(player.getUniqueId().toString());
        out.writeDouble(X);
        out.writeDouble(Y);
        out.writeDouble(Z);
        out.writeFloat(yaw);
        out.writeFloat(pitch);
        out.writeUTF(world);

        srv.sendData(main.channel, out.toByteArray());

        main.getProxy().getScheduler().schedule(main, () -> {
            if(!player.getServer().getInfo().equals(srv))
                player.connect(srv);
            }, 150, TimeUnit.MILLISECONDS);
    }

    public void teleportToPlayer(ProxiedPlayer player, ProxiedPlayer target) {
        ServerInfo srv = target.getServer().getInfo();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("teleport-to-player");
        out.writeUTF(player.getUniqueId().toString());
        out.writeUTF(target.getUniqueId().toString());

        target.getServer().sendData(main.channel, out.toByteArray());

        main.getProxy().getScheduler().schedule(main, () -> {
            if(!target.isConnected()) {
                main.getUtils().getMain().sendMessage(player, "&cLe joueur visé vient de se déconnecter.", true);
                return;
            }
            if(!player.getServer().getInfo().equals(srv)) player.connect(srv);
        }, 150, TimeUnit.MILLISECONDS);
    }
}
