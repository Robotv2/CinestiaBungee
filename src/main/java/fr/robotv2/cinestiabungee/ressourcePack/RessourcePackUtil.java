package fr.robotv2.cinestiabungee.ressourcePack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RessourcePackUtil {

    public static void sendDemand(ProxiedPlayer player) {
        try {

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ressource-pack-demand");
            player.getServer().sendData(Main.channel, out.toByteArray());

        } catch (NullPointerException ignored) {
        }
    }
}
