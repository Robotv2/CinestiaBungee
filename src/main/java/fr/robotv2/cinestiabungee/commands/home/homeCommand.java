package fr.robotv2.cinestiabungee.commands.home;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.TheAPI;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class homeCommand extends Command implements TabExecutor {

    private String serveur;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    private main main;
    public homeCommand(main main) {
        super("home");
        this.main = main;
    }

    List<String> homesName = new ArrayList<>();

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
        Set<String> homeList = main.getHomes().get().getKeys(player.getUniqueId().toString() + ".homes", false);

        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(player, "&fListe des homes disponibles: " + getHomes(player, homeList), true);;
            return;
        }

        if(!homeList.contains(args[0])) {
            main.getUtils().getMain().sendMessage(player, "&cVous ne possédez pas ce home.", true);
            return;
        }

        if(!TheAPI.getCooldownAPI(player).expired("home")) {
            main.getUtils().getMain().sendMessage(player, "&cMerci d'attendre " + TheAPI.getCooldownAPI(player).getTimeToExpire("home")/20 + " seconde(s) avant de pouvoir refaire la commande.", true);
            return;
        }

        Config homes = main.getHomes().get();
        UUID uuid = player.getUniqueId();
        String home = args[0];

        x = homes.getDouble(uuid.toString() + ".homes." + home + ".X");
        y = homes.getDouble(uuid.toString() + ".homes." + home + ".Y");
        z = homes.getDouble(uuid.toString() + ".homes." + home + ".Z");
        yaw = homes.getFloat(uuid.toString() + ".homes." + home + ".yaw");
        pitch = homes.getFloat(uuid.toString() + ".homes." + home + ".pitch");
        world = homes.getString(uuid.toString() + ".homes." + home + ".world");
        serveur = homes.getString(uuid.toString() + ".homes." + home + ".serveur");

        main.getUtils().getMain().teleportToLocation(player, x, y, z, yaw, pitch, world, serveur);
        TheAPI.getCooldownAPI(player).createCooldown("home", 200);
    }

    public String getHomes(ProxiedPlayer player, Set<String> homeList) {
        if(homeList == null) {
            return "&cVous n'avez pas d'homes.";
        }

        int num = 0;
        int totalHomes = homeList.size();
        StringBuilder homes = new StringBuilder();

        for(String homeName : homeList) {
            num = num + 1;
            if(totalHomes - 1 != num && totalHomes != 1 && totalHomes != num) {
                homes.append("&e").append(homeName).append("&f, ");
            }
            else if(totalHomes == 1 || totalHomes == num) {
                homes.append("&e").append(homeName);
            }
            else {
                homes.append("&e").append(homeName).append(" &fet ");
            }
        }
        return homes.toString();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;
        if(!homesName.isEmpty())
            homesName.clear();

        ProxiedPlayer player = (ProxiedPlayer)sender;
        homesName = new ArrayList<>(main.getHomes().get().getKeys(player.getUniqueId().toString() + ".homes", false));

        if (args[0].length() == 0) return homesName;
        if(args.length == 1) {
            List<String> result = new ArrayList<>() ;
            for(int i = 0; i < homesName.size(); ++i) {
                if (homesName.get(i).startsWith(args[0])) {
                    result.add(homesName.get(i));
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
}
