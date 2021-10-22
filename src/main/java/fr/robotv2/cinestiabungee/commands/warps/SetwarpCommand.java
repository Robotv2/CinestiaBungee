package fr.robotv2.cinestiabungee.commands.warps;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SetwarpCommand extends Command {

    private Main main;
    public SetwarpCommand(Main main) {
        super("setwarp");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.setwarp")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&cUTILISATION: /setwarp <warp>", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        String warpName = args[0];

        setwarp(player, warpName);
        main.getUtils().getMain().sendMessage(player, "&fVous venez de mettre le warp &e" + warpName +" &fà votre localisation.", true);
    }

    public void setwarp(ProxiedPlayer player, String name) {
        ByteArrayDataOutput outTeleport = ByteStreams.newDataOutput();

        outTeleport.writeUTF("setwarp-player");
        outTeleport.writeUTF(name.toLowerCase());

        player.getServer().sendData(main.channel, outTeleport.toByteArray());
    }
}
