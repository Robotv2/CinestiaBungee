package fr.robotv2.cinestiabungee.commands;

import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static fr.robotv2.cinestiabungee.utility.MainUtil.color;

public class BossBarCommand extends Command {

    private Main main;
    public BossBarCommand(Main main) {
        super("cinestiabossbar");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("cinestia.command.bossbar")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        if(args.length != 3) {
            return;
        }

        ProxiedPlayer player = main.getProxy().getPlayer(args[0]);
        int seconds = Integer.parseInt(args[1]);
        String text = color(args[2].replace("_", " "));

        if(player == null || !player.isConnected()) {
            return;
        }

        main.getUtils().getBossBarUtil().init(player, seconds, text);
    }
}
