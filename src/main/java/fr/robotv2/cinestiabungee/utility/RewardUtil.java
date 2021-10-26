package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RewardUtil {

    private final Main main;
    private ScheduledTask task;

    public RewardUtil(Main main) {
        this.main = main;
        init();
    }

    public void init() {
        task = main.getProxy().getScheduler().schedule(main, this::sendRewards,30 , 30, TimeUnit.MINUTES);
    }

    public void stop() {
        task.cancel();
    }

    public void sendRewards() {
        long NOW = Instant.now().toEpochMilli();
        MainUtil util = main.getUtils().getMain();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("execute-reward");

        for(ProxiedPlayer player : main.getProxy().getPlayers()) {

            util.sendMessage(player, "&8&m&l-»-------------»-", false);
            util.sendMessage(player, "&8&l» &3RÉCOMPENSE DE CONNEXION", false);
            util.sendMessage(player, "&8&m&l-»-------------»-", false);

            long LAST_CON = main.getPlayerInfo().get().getLong(player.getUniqueId() + ".LAST-CONNECTION");

            if(NOW/1000 - LAST_CON/1000 < 180)
                main.getUtils().getMain().sendMessage(player, "&cVous n'avez rien gagné car cela ne fait pas 3O minutes que vous jouez sur le serveur.", false);
            else
                player.getServer().sendData(Main.channel, out.toByteArray());
        }
    }
}
