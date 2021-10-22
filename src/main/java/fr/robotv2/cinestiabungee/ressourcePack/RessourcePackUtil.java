package fr.robotv2.cinestiabungee.ressourcePack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class RessourcePackUtil {

    private static final List<ProxiedPlayer> players = new ArrayList<>();

    public static void sendDemand(ProxiedPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ressource-pack-demand");
        player.getServer().sendData(Main.channel, out.toByteArray());
    }
}
