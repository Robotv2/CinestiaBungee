package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.TimeUnit;

public class rtpUtil {
    private main main;
    public rtpUtil(main main) {
        this.main = main;
    }

    public enum worldType {
        OVERWORLD, NETHER, END;
    }

    public void rtpPlayer(ProxiedPlayer player,worldType type) {
        ServerInfo info = ProxyServer.getInstance().getServerInfo("ressource");
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("prepare-rtp");
        out.writeUTF(player.getUniqueId().toString());
        out.writeUTF(type.toString());

        info.sendData(main.channel, out.toByteArray());

        main.getProxy().getScheduler().schedule(main, () -> {
            player.connect(info);
        }, 200, TimeUnit.MILLISECONDS);
    }
}
