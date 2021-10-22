package fr.robotv2.cinestiabungee.commands.spawn;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SpawnCommand extends Command {

    private Main main;
    public SpawnCommand(Main main) {
        super("spawn");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.spawn")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        main.getUtils().getSpawn().teleportToSpawn(player);
    }
}
