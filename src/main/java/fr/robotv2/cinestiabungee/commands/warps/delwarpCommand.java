package fr.robotv2.cinestiabungee.commands.warps;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class delwarpCommand extends Command implements TabExecutor {

    private main main;
    public delwarpCommand(main main) {
        super("delwarp");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.delwarp")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&cUTILISATION: /setwarp <warp>", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        String warpName = args[0];
        List<String> warps = main.getUtils().getWarp().getWarps();

        if(!warps.contains(warpName)) {
            main.getUtils().getMain().sendMessage(sender, "&cCe warp n'existe pas", true);
            return;
        }

        delwarp(warpName);
        main.getUtils().getMain().sendMessage(player, "&fVous venez de supprimer le warp: &e" + warpName, true);
    }

    public void delwarp(String warp) {
        Config warps = main.getWarps().get();
        warps.set("warps." + warp + ".X", null);
        warps.set("warps." + warp + ".Y", null);
        warps.set("warps." + warp + ".Z", null);

        warps.set("warps." + warp + ".monde", null);

        warps.set("warps." + warp + ".yaw", null);
        warps.set("warps." + warp + ".pitch", null);
        warps.set("warps." + warp + ".serveur", null);
        warps.save();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;

        List<String> warps = main.getUtils().getWarp().getWarps();
        if (args[0].length() == 0) return warps;

        if(args.length == 1) {
            List<String> result = new ArrayList<>() ;
            for(int i = 0; i < warps.size(); ++i) {
                if (warps.get(i).startsWith(args[0])) {
                    result.add(warps.get(i));
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
}
