package fr.robotv2.cinestiabungee.commands.warps;

import fr.robotv2.cinestiabungee.Main;
import fr.robotv2.cinestiabungee.utility.MainUtil;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WarpCommand extends Command implements TabExecutor {

    private String serveur;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    private Main main;
    public WarpCommand(Main main) {
        super("warp", null, "warps");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        if(!sender.hasPermission("cinestia.command.warp")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)sender;
        Set<String> warps = main.getWarps().get().getKeys("warps", false);

        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&fListe de(s) warp(s) disponible(s): " + getWarps(player, warps), true);
            return;
        }
        String warpName = args[0];
        if(!main.getUtils().getWarp().hasAccess(player, warpName)) {
            main.getUtils().getMain().sendMessage(player,"&cCe warp n'est pas disponible.", true);
            return;
        }

        Config config = main.getWarps().get();
        x = config.getDouble("warps." + warpName + ".X");
        y = config.getDouble("warps." + warpName + ".Y");
        z = config.getDouble("warps." + warpName + ".Z");
        world = config.getString("warps." + warpName + ".WORLD");
        yaw = config.getFloat("warps." + warpName + ".YAW");
        pitch = config.getFloat("warps." + warpName + ".PITCH");
        serveur = config.getString("warps." + warpName + ".SERVER");

        main.getUtils().getMain().teleportToLocation(player, x, y, z, yaw, pitch, world, serveur, 3, MainUtil.teleportReason.WARP, warpName);
        //main.getUtils().getMain().sendMessage(player, "&fVous avez été téléporté au warp: &b" + warpName, true);
    }

    public String getWarps(ProxiedPlayer player, Set<String> warpsList) {

        int num = 0;
        int totalWarp = warpsList.size();
        StringBuilder homes = new StringBuilder();

        if(totalWarp == 0) {
            return "&cAucun.";
        }
        for(String warp : warpsList) {
            num++;
            if(totalWarp - 1 != num && totalWarp != 1 && totalWarp != num) {
                homes.append("&e").append(warp).append("&f, ");
            }
            else if(totalWarp == 1 || totalWarp == num) {
                homes.append("&e").append(warp);
            }
            else {
                homes.append("&e").append(warp).append(" &fet ");
            }
        }
        return homes.toString();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;

        ProxiedPlayer player = (ProxiedPlayer)sender;
        List<String> warps = new ArrayList<String>(main.getUtils().getWarp().getAccessibleWarps(player));
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
