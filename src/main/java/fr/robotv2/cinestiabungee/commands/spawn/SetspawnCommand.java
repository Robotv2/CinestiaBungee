package fr.robotv2.cinestiabungee.commands.spawn;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SetspawnCommand extends Command {

    private Main main;
    public SetspawnCommand(Main main) {
        super("setspawn");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.setspawn")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        setspawn(player);
        main.getUtils().getMain().sendMessage(player, "&cVous venez de mettre la localisation du spawn à cet emplacement.", true);
    }

    public void setspawn(ProxiedPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("setspawn-player");
        player.getServer().sendData(main.channel, out.toByteArray());
    }
}
