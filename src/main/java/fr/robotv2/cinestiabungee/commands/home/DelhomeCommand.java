package fr.robotv2.cinestiabungee.commands.home;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DelhomeCommand extends Command implements TabExecutor {

    private Main main;
    public DelhomeCommand(Main main) {
        super("delhome");
        this.main = main;
    }

    List<String> homesName = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if(!sender.hasPermission("cinestia.command.delhome")) {
            main.getUtils().getMain().sendMessage(sender, "&cVous n'avez pas la permission de faire cette commande.", true);
            return;
        }
        if(args.length == 0) {
            main.getUtils().getMain().sendMessage(sender, "&c&lUTILISATION&c: /delhome <home>", true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)sender;
        String home = args[0];
        Set<String> homeList = main.getHomes().get().getKeys(player.getUniqueId().toString() + ".homes", false);

        if(!homeList.contains(home)) {
            main.getUtils().getMain().sendMessage(player, "&cVous ne possédez pas ce home.", true);
            return;
        }

        deleteHome(home, player);
        main.getUtils().getMain().sendMessage(player, "&fVous venez de supprimer le home: &e" + home, true);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return null;
        if(!homesName.isEmpty()) homesName.clear();
        if (args[0].length() == 0) return homesName;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        homesName = new ArrayList<String>((main.getHomes().get().getKeys(player.getUniqueId().toString() + ".homes", false)));

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

    public void deleteHome(String home, ProxiedPlayer player) {
        Config homes = main.getHomes().get();
        UUID uuid = player.getUniqueId();

        homes.set(uuid + ".homes." + home + ".X", null);
        homes.set(uuid + ".homes." + home + ".Y", null);
        homes.set(uuid + ".homes." + home + ".Z", null);
        homes.set(uuid + ".homes." + home + ".yaw", null);
        homes.set(uuid + ".homes." + home + ".pitch", null);
        homes.set(uuid + ".homes." + home + ".world", null);
        homes.set(uuid + ".homes." + home + ".serveur", null);
        homes.set(uuid + ".homes." + home, null);

        main.getHomes().saveDB();
    }
}
