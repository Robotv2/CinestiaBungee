package fr.robotv2.cinestiabungee.commands.tp;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.TheAPI;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Set;
import java.util.UUID;

public class backCommand extends Command {

    private main main;
    public backCommand(main main) {
        super("back");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.back")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)sender;

        if(!main.getUtils().getBack().has(player)) {
            main.getUtils().getMain().sendMessage(player, "&cAucune localisation enregistrée.", true);
            return;
        }

        if(!TheAPI.getCooldownAPI(player).expired("back")) {
            main.getUtils().getMain().sendMessage(player, "&cMerci d'attendre " + TheAPI.getCooldownAPI(player).getTimeToExpire("home")/20 + " seconde(s) avant de pouvoir refaire la commande.", true);
            return;
        }

        main.getUtils().getBack().teleportToBack(player);
        TheAPI.getCooldownAPI(player).createCooldown("back", 200);
    }
}
