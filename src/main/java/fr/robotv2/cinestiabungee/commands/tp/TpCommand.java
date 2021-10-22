package fr.robotv2.cinestiabungee.commands.tp;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class TpCommand extends Command implements TabExecutor {

    private final Main main;
    public TpCommand(Main main) {
        super("tp");
        this.main = main;
    }

    List<String> playerList = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.tp")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&c&lUTILISATION&c: /tp <joueur>", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ProxiedPlayer target = main.getProxy().getPlayer(args[0]);

        if(target == null || !target.isConnected()) {
            main.getUtils().getMain().sendMessage(player, "&cCe joueur n'est pas connecté.", true);
            return;
        }

        main.getUtils().getMain().teleportToPlayer(player, target, 0);
        main.getUtils().getMain().sendMessage(player, "&7Vous vous êtes téléporté à &e" + target.getName(), true);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        playerList.clear();

        for(ProxiedPlayer playerOnline : main.getProxy().getPlayers()) {
            if(!playerOnline.equals(player))
                playerList.add(playerOnline.getName());
        }

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
