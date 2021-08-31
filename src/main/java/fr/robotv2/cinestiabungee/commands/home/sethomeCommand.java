package fr.robotv2.cinestiabungee.commands.home;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class sethomeCommand extends Command {

    private final main main;
    public sethomeCommand(main main) {
        super("sethome");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.home")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)sender;
        int limit = main.getUtils().getHome().getLimit(player);

        if(main.getUtils().getHome().getTotal(player) >= limit) {
            main.getUtils().getMain().sendMessage(player, "&cVous n'avez plus d'homes disponibles. (" + limit + ")", true);
            return;
        }

        if(args.length == 0) {
            this.sethome("home", player);
            main.getUtils().getMain().sendMessage(player, "&fVous venez de mettre le home &eHome &fà votre localisation.", true);
            return;
        }
        String homeName = args[0];

        sethome(homeName, player);
        main.getUtils().getMain().sendMessage(player, "&fVous venez de mettre le home &e" + homeName +" &fà votre localisation.", true);
    }

    public void sethome(String name, ProxiedPlayer player) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("sethome-player");
        out.writeUTF(name);

        player.getServer().sendData(main.channel, out.toByteArray());
    }
}
