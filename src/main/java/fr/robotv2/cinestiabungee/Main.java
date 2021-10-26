package fr.robotv2.cinestiabungee;

import fr.robotv2.cinestiabungee.commands.BoutiqueCommand;
import fr.robotv2.cinestiabungee.commands.DiscordCommand;
import fr.robotv2.cinestiabungee.commands.BossBarCommand;
import fr.robotv2.cinestiabungee.configs.PlayersInfo;
import fr.robotv2.cinestiabungee.configs.Rankup;
import fr.robotv2.cinestiabungee.listeners.FirstTimeListeners;
import fr.robotv2.cinestiabungee.listeners.PreJoinEvent;
import fr.robotv2.cinestiabungee.pluginMessage.PluginMessage;
import fr.robotv2.cinestiabungee.rankup.RankupListener;
import fr.robotv2.cinestiabungee.ressourcePack.RessourcePackListener;
import fr.robotv2.cinestiabungee.utility.BossBarUtil;
import fr.robotv2.cinestiabungee.utility.UtilManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class Main extends Plugin {

    public final static String channel = "cinestia:channel";
    public final static String rankup_channel = "rankup:channel";

    private LuckPerms luckperms;
    private UtilManager utilManager;

    private Rankup rankup;
    private PlayersInfo playerInfo;

    private static Main INSTANCE;

    @Override
    public void onEnable() {
        Long current = System.currentTimeMillis();

        registerCommands();
        registerConfigs();
        registerClasses();
        registerChannel();
        registerListeners();

        getUtils().getMain().showBanner(current);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }

    public void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new BossBarCommand(this));
        pm.registerCommand(this, new DiscordCommand());
        pm.registerCommand(this, new BoutiqueCommand());
    }

    public void registerChannel() {
        //CINESTIA-CORE
        getProxy().registerChannel(channel);
        getProxy().registerChannel(rankup_channel);
        getProxy().getPluginManager().registerListener(this, new PluginMessage(this));
    }

    public void registerConfigs() {
        rankup = new Rankup(this);
        playerInfo = new PlayersInfo(this);
    }

    public void registerClasses() {
        luckperms = LuckPermsProvider.get();
        utilManager = new UtilManager(this);
    }

    public void registerListeners() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerListener(this, new PreJoinEvent());
        pm.registerListener(this, new FirstTimeListeners(this));
        pm.registerListener(this, new RankupListener(this));
        pm.registerListener(this, new BossBarUtil(this));

        pm.registerListener(this, new RessourcePackListener(this));
    }

    public static Main getInstance() {
        return INSTANCE;
    }

    public LuckPerms getLuckperms() {
        return luckperms;
    }

    public UtilManager getUtils() {
        return utilManager;
    }

    public PlayersInfo getPlayerInfo() {
        return playerInfo;
    }

    public Rankup getRankUp() {
        return rankup;
    }

    public boolean hasPermission(ProxiedPlayer player, String permission) {
        return player.hasPermission("cinestia.admin") || player.hasPermission(permission);
    }
}
