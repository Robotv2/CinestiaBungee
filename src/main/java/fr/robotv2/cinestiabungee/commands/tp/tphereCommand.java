package fr.robotv2.cinestiabungee.commands.tp;

import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class tphereCommand extends Command {

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
            main.getUtils().getMain().sendMessage(sender, "&c&lUTILISATION&c: /tphere <joueur>", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ProxiedPlayer target = main.getProxy().getPlayer(args[0]);

        if(target == null || !target.isConnected()) {
            main.getUtils().getMain().sendMessage(player, "&cCe joueur n'est pas connecté.", true);
            return;
        }

        main.getUtils().getMain().teleportToPlayer(target, player);
        main.getUtils().getMain().sendMessage(player, "&7Vous avez téléporté &e" + target.getName() + " &7à votre position.", true);
    }
}
