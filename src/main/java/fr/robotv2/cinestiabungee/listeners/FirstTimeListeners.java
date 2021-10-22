package fr.robotv2.cinestiabungee.listeners;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class FirstTimeListeners implements Listener {

    private final Main main;
    public FirstTimeListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        main.getProxy().getScheduler().schedule(main, () -> {
            ProxiedPlayer player = e.getPlayer();

            boolean HAS_PLAYED_BEFORE = main.getPlayerInfo().get().getBoolean(player.getUniqueId() + ".HAS-PLAYED-BEFORE");
            main.getPlayerInfo().get().set(player.getUniqueId() + ".LAST-CONNECTION", Instant.now().toEpochMilli());

            if(!HAS_PLAYED_BEFORE) {
                //Le joueur ne s'est jamais connecté.
                for(ProxiedPlayer connected : main.getProxy().getPlayers()) {
                    main.getUtils().getMain().sendMessage(connected, "&3", false);
                    main.getUtils().getMain().sendMessage(connected, "&3&l> &fLe joueur &b" + player.getName() + " &fvient de rejoindre le serveur &bpour la première fois &3!", false);
                    main.getUtils().getMain().sendMessage(connected, "&3", false);
                }
            }

        }, 200, TimeUnit.MILLISECONDS);
    }
}
