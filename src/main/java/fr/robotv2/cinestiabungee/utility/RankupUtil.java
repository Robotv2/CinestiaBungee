package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class RankupUtil {

    private Main main;
    public RankupUtil(Main main) {
        this.main = main;
    }

    public int getLevel(UUID playerUUID) {
        return main.getRankUp().get().getInt(playerUUID.toString() + ".level");
    }

    public int getLevel(ProxiedPlayer player) {
        return getLevel(player.getUniqueId());
    }

    public Double getExp(UUID playerUUID) {
        return main.getRankUp().get().getDouble(playerUUID.toString() + ".exp");
    }

    public Double getExp(ProxiedPlayer player) {
        return getExp(player.getUniqueId());
    }

    public void setLevel(UUID playerUUID, int level) {
        main.getRankUp().get().set(playerUUID.toString() + ".level", level);
        main.getRankUp().saveDB();
    }

    public void setLevel(ProxiedPlayer player, int level) {
        setLevel(player.getUniqueId(), level);
    }

    public void setExp(UUID playerUUID, double exp) {
        main.getRankUp().get().set(playerUUID.toString() + ".exp", exp);
        main.getRankUp().saveDB();
    }

    public void setExp(ProxiedPlayer player, double exp) {
        setExp(player.getUniqueId(), exp);
    }

    public void sendLevel(ProxiedPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("set-level");
        out.writeInt(getLevel(player));

        try {
            player.getServer().sendData(main.rankup_channel, out.toByteArray());
        } catch(NullPointerException ignored) {
        }

    }

    public void sendExp(ProxiedPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("set-exp");
        out.writeDouble(getExp(player));

        player.getServer().sendData(main.rankup_channel, out.toByteArray());
    }

    public boolean hasAccount(ProxiedPlayer player) {
        return hasAccount(player.getUniqueId());
    }

    public boolean hasAccount(UUID playerUUID) {
        return main.getRankUp().get().get(playerUUID.toString() + ".level") != null;
    }

    public void createAccount(UUID playerUUID) {
        setLevel(playerUUID, 0);
        setExp(playerUUID, 0.0);
    }

    public void createAccount(ProxiedPlayer player) {
        createAccount(player.getUniqueId());
    }
}
