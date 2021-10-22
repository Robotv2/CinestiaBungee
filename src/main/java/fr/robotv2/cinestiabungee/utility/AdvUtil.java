package fr.robotv2.cinestiabungee.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.robotv2.cinestiabungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdvUtil {

    public HashMap<ProxiedPlayer, Integer> count = new HashMap<>();
    public HashMap<ProxiedPlayer, ScheduledTask> scheduler = new HashMap<>();

    private Main main;
    public AdvUtil(Main main) {
        this.main = main;
    }

    public void addAdvancement(ProxiedPlayer player, String advancement) {
        List<String> advancements = getAdvancements(player);
        if(advancements.contains(advancement)) return;
        else {
            LinkedList<String> result = new LinkedList<>(advancements);
            result.add(advancement);
            main.getAdv().get().set(player.getUniqueId() + ".advancements", result);
            main.getAdv().saveDB();

            if(getNameFromKey(advancement) != null)
                broadcastAdvancements(player, getNameFromKey(advancement));
        }
    }

    public List<String> getAdvancements(ProxiedPlayer player) {
        return main.getAdv().get().getStringList(player.getUniqueId() + ".advancements");
    }

    private void broadcastAdvancements(ProxiedPlayer player, String advancement) {
        BaseComponent[] message = new ComponentBuilder(
                "> ").color(ChatColor.AQUA).bold(true)
                .append("Le joueur ").color(ChatColor.WHITE).bold(false)
                .append(player.getName()).color(ChatColor.of("#29e3e0")).bold(false)
                .append(" vient d'accomplir le succès ").color(ChatColor.WHITE).bold(false)
                .append("[" + advancement + "]").color(ChatColor.of("#29e3e0"))
                .append(" !").color(ChatColor.WHITE).bold(false).create();

        for(ProxiedPlayer receiver : main.getProxy().getPlayers()) {
            receiver.sendMessage(message);
        }
    }

    public void initPlayer(ProxiedPlayer player) {
        List<String> advancements = getAdvancements(player);
        setCount(player, advancements.size() - 1);

        ScheduledTask task = ProxyServer.getInstance().getScheduler().schedule(main, () -> {
            if(player.isConnected() && getCount(player) >= 0) {
                String adv = advancements.get(getCount(player));
                sendToBukkitAdvancement(player, adv);
                setCount(player, getCount(player) - 1);
            } else {
                stop(player);
                count.remove(player);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
        scheduler.put(player, task);
    }

    public void setCount(ProxiedPlayer player, Integer value) {
        count.remove(player);
        count.put(player, value);
    }

    public int getCount(ProxiedPlayer player) {
        return count.get(player);
    }

    public void stop(ProxiedPlayer player) {
        if(!scheduler.containsKey(player)) return;

        scheduler.get(player).cancel();
        scheduler.remove(player);
    }

    public void sendToBukkitAdvancement(ProxiedPlayer player, String advancement) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("grant-advancement");
        out.writeUTF(advancement);

        player.getServer().sendData(main.channel, out.toByteArray());
    }

    public String getNameFromKey(String key) {
        switch(key.toLowerCase()) {
            case "minecraft:story/mine_stone":
                return "L'âge de pierre";
            case "minecraft:story/upgrade_tools":
                return "Qualité supérieure";
            case "minecraft:story/smelt_iron":
                return "L'âge du fer";
            case "minecraft:story/obtain_armor":
                return "Un corp d'acier";
            case "minecraft:story/lava_bucket":
                return "Ça brûle !";
            case "minecraft:story/iron_tools":
                return "Bonne pioche !";
            case "minecraft:story/deflect_arrow":
                return "Pas aujourd'hui, merci";
            case "minecraft:story/form_obsidian":
                return "Ice Bucket Challenge";
            case "minecraft:story/mine_diamond":
                return "Des diamants !";
            case "minecraft:story/enter_the_nether":
                return "Aller au fond des choses";
            case "minecraft:story/shiny_gear":
                return "Couvrez-moi de diamants";
            case "minecraft:story/enchant_item":
                return "Enchanté !";
            case "minecraft:story/cure_zombie_villager":
                return "Zombiologue";
            case "minecraft:story/follow_ender_eye":
                return "À suivre…";
            case "minecraft:story/enter_the_end":
                return "Fin ?";
            case "minecraft:nether/return_to_sender":
                return "Retour à l'envoyeur";
            case "minecraft:nether/find_bastion":
                return "Le bon vieux temps";
            case "minecraft:nether/obtain_ancient_debris":
                return "Caché dans les profondeurs";
            case "minecraft:nether/fast_travel":
                return "Bulle du sous-espace";
            case "minecraft:nether/find_fortress":
                return "Une terrible forteresse";
            case "minecraft:nether/obtain_crying_obsidian":
                return "Qui a coupé un oignon ?";
            case "minecraft:nether/distract_piglin":
                return "Bling-bling";
            case "minecraft:nether/ride_strider":
                return "Bateau sur pattes";
            case "minecraft:nether/uneasy_alliance":
                return "Alliance instable";
            case "minecraft:nether/loot_bastion":
                return "De vraies têtes de cochon";
            case "minecraft:nether/use_lodestone":
                return "Le Petit Poucet";
            case "minecraft:nether/netherite_armor":
                return "Couvrez-moi de débris";
            case "minecraft:nether/get_wither_skull":
                return "Qu'on lui coupe la tête !";
            case "minecraft:nether/obtain_blaze_rod":
                return "Dans le feu de l'action";
            case "minecraft:nether/charge_respawn_anchor":
                return "Pas tout à fait 'neuf' vies";
            case "minecraft:nether/explore_nether":
                return "Voyage au bout de l'enfer";
            case "minecraft:nether/summon_wither":
                return "Les Witherables";
            case "minecraft:nether/brew_potion":
                return "Apprenti chimiste";
            case "minecraft:nether/create_beacon":
                return "Fais ta balise";
            case "minecraft:nether/all_potions":
                return "Mélanges dangereux";
            case "minecraft:nether/create_full_beacon":
                return "Phare à On";
            case "minecraft:nether/all_effects":
                return "Comment en est-on arrivé là ?";
            case "minecraft:end/kill_dragon":
                return "Libérez l'End !";
            case "minecraft:end/dragon_egg":
                return "La nouvelle génération";
            case "minecraft:end/enter_end_gateway":
                return "Escapade à distance";
            case "minecraft:end/respawn_dragon":
                return "Un air de déjà vu...";
            case "minecraft:end/dragon_breath":
                return "A bout de souffle";
            case "minecraft:end/find_end_city":
                return "La cité du bout du monde";
            case "minecraft:end/elytra":
                return "Vers l'infini et au-délà";
            case "minecraft:end/levitate":
                return "Superbe panorama";
        }
        return null;
    }
}
