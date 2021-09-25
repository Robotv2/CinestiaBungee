package fr.robotv2.cinestiabungee.commands.tp;

import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class tphereCommand extends Command {

    private ScheduledTask task;
    private int count = 0;

    private main main;
    public tphereCommand(main main) {
        super("tphere");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        if(!sender.hasPermission("cinestia.command.tphere")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&c&lUTILISATION&c: /tphere <joueur> / all", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        String arg1 = args[0];

        if(!arg1.equalsIgnoreCase("all")) {
            tpHereOne(player, args);
        } else {
            tpAll(player);
        }
    }

    public void tpHereOne(ProxiedPlayer player, String[] args) {
        ProxiedPlayer target = main.getProxy().getPlayer(args[0]);
        if(target == null || !target.isConnected()) {
            main.getUtils().getMain().sendMessage(player, "&cCe joueur n'est pas connecté.", true);
            return;
        }
        main.getUtils().getMain().teleportToPlayer(target, player, 0);
        main.getUtils().getMain().sendMessage(player, "&7Vous avez téléporté &e" + target.getName() + " &7à votre position.", true);
    }

    public void tpAll(ProxiedPlayer player) {
        count = 0;
        List<ProxiedPlayer> players = new ArrayList<>(main.getProxy().getPlayers());

        task = ProxyServer.getInstance().getScheduler().schedule(main, () -> {
            ProxiedPlayer target = players.get(count);
            if(count < players.size() - 1 ) {
                if(target != null && target.isConnected())
                    main.getUtils().getMain().teleportToPlayer(player, target, 0);
            } else {
                task.cancel();
            }
        }, 0, 50, TimeUnit.MILLISECONDS);

        main.getUtils().getMain().sendMessage(player, "&7Vous avez téléporté &etous les joueurs &7à votre position.", true);
    }
}
