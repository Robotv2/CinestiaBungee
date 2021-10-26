package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.util.concurrent.TimeUnit;

public class RtpUtil {
    private final Main main;
    private final ServerInfo ressource;
    public RtpUtil(Main main) {
        this.main = main;
        ressource = ProxyServer.getInstance().getServerInfo("ressource");
    }

    public enum WorldType {
        OVERWORLD, NETHER, END
    }

    public void rtpPlayer(ProxiedPlayer player, WorldType type) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("prepare-rtp");
        out.writeUTF(player.getUniqueId().toString());
        out.writeUTF(type.toString());

        ressource.sendData(Main.channel, out.toByteArray());

        main.getProxy().getScheduler().schedule(main, () -> {
            player.connect(ressource);
        }, 250, TimeUnit.SECONDS);
    }
}
