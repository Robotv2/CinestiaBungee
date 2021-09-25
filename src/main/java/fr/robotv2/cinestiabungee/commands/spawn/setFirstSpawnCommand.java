package fr.robotv2.cinestiabungee.commands.spawn;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class setFirstSpawnCommand extends Command {

    private main main;
    public setFirstSpawnCommand(main main) {
        super("setfirstspawn");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.setfirstspawn")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        setfirstspawn(player);
        main.getUtils().getMain().sendMessage(player, "&cVous venez de mettre la localisation du spawn à cet emplacement.", true);
    }

    public void setfirstspawn(ProxiedPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("setfirstspawn-player");
        player.getServer().sendData(main.channel, out.toByteArray());
    }
}
