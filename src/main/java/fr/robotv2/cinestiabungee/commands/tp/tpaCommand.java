package fr.robotv2.cinestiabungee.commands.tp;

import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class tpaCommand extends Command implements TabExecutor {

    public HashMap<ProxiedPlayer, ProxiedPlayer> inInvite = new HashMap<>();

    private final main main;
    public tpaCommand(main main) {
        super("tpa");
        this.main = main;
    }

    List<String> playerList = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.tpa")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&c&lUTILISATION&c: /tpa <joueur> / accept / deny", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        String arg1 = args[0];

        if(arg1.equalsIgnoreCase("accept") || arg1.equalsIgnoreCase("deny")) {
            switch(arg1.toLowerCase()) {
                case "accept":
                    this.onAccept(player);
                    return;
                case "deny":
                    this.onDeny(player);
                    return;
            }
        }

        ProxiedPlayer target = main.getProxy().getPlayer(arg1);

        if(target == null || !target.isConnected()) {
            main.getUtils().getMain().sendMessage(player, "&cCe joueur n'est pas connecté.", true);
            return;
        }
        if(target.equals(player)) {
            main.getUtils().getMain().sendMessage(player, "§cVous ne pouvez pas vous inviter !", true);
            return;
        }
        if(inInvite.containsKey(target)) {
            main.getUtils().getMain().sendMessage(player, "&cLe joueur a déjà une invitation en cours, merci d'attendre avant de lui en renvoyer une autre !", true);
            return;
        }

        inInvite.put(target, player);
        main.getUtils().getMain().sendMessage(target, "§fVous avez reçu une demande de téléportation de la part de §b" + player.getName() + " §f!", true);
        main.getUtils().getMain().sendMessage(target, "§fFaites §a/tpa accept §fpour l'accepter.", true);
        main.getUtils().getMain().sendMessage(target, "§fFaites §c/tpa deny §fpour la refuser.", true);
        main.getUtils().getMain().sendMessage(player, "§fVous venez d'envoyer une demande de téléportation à " + target.getName() + " §f!", true);

        ProxyServer.getInstance().getScheduler().schedule(main, () -> {
            if(inInvite.containsKey(target)) {
                inInvite.remove(target);
                main.getUtils().getMain().sendMessage(player, "&cLe joueur n'a pas accepté votre demande de téléportation.", true);
                main.getUtils().getMain().sendMessage(target, "&cVous venez de refuser la demande de téléportation.", true);
            }
        }, 20, TimeUnit.SECONDS);
    }

    public void onAccept(ProxiedPlayer player) {
        if(inInvite.containsKey(player)) {

            ProxiedPlayer sender = inInvite.get(player);
            inInvite.remove(player);

            main.getUtils().getMain().sendMessage(player, "&fVous avez accepté la demande de téléportation.", true);
            main.getUtils().getMain().sendMessage(sender, "&b" + player.getName() + " &fa accepté votre demande, téléportation en cours.", true);

            main.getUtils().getMain().teleportToPlayer(sender, player, 3);
            return;
        }
        main.getUtils().getMain().sendMessage(player, "&cVous n'avez aucune demande en attente.", true);
    }

    public void onDeny(ProxiedPlayer player) {
        if(inInvite.containsKey(player)) {
            ProxiedPlayer sender = inInvite.get(player);
            inInvite.remove(player);

            main.getUtils().getMain().sendMessage(player, "&cVous venez de refuser la demande de téléportation.", true);
            main.getUtils().getMain().sendMessage(sender, "&cLe joueur n'a pas accepté votre demande de téléportation.", true);
            return;
        }
        main.getUtils().getMain().sendMessage(player, "&cVous n'avez aucune demande en attente.", true);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;
        playerList.clear();

        ProxiedPlayer player = (ProxiedPlayer) sender;

        for(ProxiedPlayer playerOnline : main.getProxy().getPlayers()) {
            if(!playerOnline.equals(player)) {
                playerList.add(playerOnline.getName());
            }
        }

        playerList.add("accept");
        playerList.add("deny");

        if (args[0].length() == 0) return playerList;

        if(args.length == 1) {
            List<String> result = new ArrayList<>() ;
            for(int i = 0; i < playerList.size(); ++i) {
                if (playerList.get(i).startsWith(args[0])) {
                    result.add(playerList.get(i));
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
}
