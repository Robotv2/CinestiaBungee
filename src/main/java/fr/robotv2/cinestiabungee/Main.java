package fr.robotv2.cinestiabungee;

import fr.robotv2.cinestiabungee.commands.bossbar.BossBarCommand;
import fr.robotv2.cinestiabungee.commands.home.DelhomeCommand;
import fr.robotv2.cinestiabungee.commands.home.HomeCommand;
import fr.robotv2.cinestiabungee.commands.home.SethomeCommand;
import fr.robotv2.cinestiabungee.commands.spawn.SetFirstSpawnCommand;
import fr.robotv2.cinestiabungee.commands.spawn.SetspawnCommand;
import fr.robotv2.cinestiabungee.commands.spawn.SpawnCommand;
import fr.robotv2.cinestiabungee.commands.tp.BackCommand;
import fr.robotv2.cinestiabungee.commands.tp.TpCommand;
import fr.robotv2.cinestiabungee.commands.tp.TpaCommand;
import fr.robotv2.cinestiabungee.commands.tp.TphereCommand;
import fr.robotv2.cinestiabungee.commands.warps.DelwarpCommand;
import fr.robotv2.cinestiabungee.commands.warps.SetwarpCommand;
import fr.robotv2.cinestiabungee.commands.warps.WarpCommand;
import fr.robotv2.cinestiabungee.configs.*;
import fr.robotv2.cinestiabungee.listeners.FirstTimeListeners;
import fr.robotv2.cinestiabungee.listeners.PreJoinEvent;
import fr.robotv2.cinestiabungee.listeners.QuitEvent;
import fr.robotv2.cinestiabungee.ressourcePack.RessourcePackListener;
import fr.robotv2.cinestiabungee.pluginMessage.PluginMessage;
import fr.robotv2.cinestiabungee.rankup.RankupListener;
import fr.robotv2.cinestiabungee.utility.BossBarUtil;
import fr.robotv2.cinestiabungee.utility.UtilManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class Main extends Plugin {

    public final static String channel = "cinestia:channel";
    public final String rankup_channel = "rankup:channel";

    private LuckPerms luckperms;
    private UtilManager utilManager;

    private Homes homes;
    private Warps warps;
    private Advancements advancements;
    private Back back;
    private Spawn spawn;
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

        pm.registerCommand(this, new TphereCommand(this));
        pm.registerCommand(this, new TpaCommand(this));
        pm.registerCommand(this, new TpCommand(this));
        pm.registerCommand(this, new BackCommand(this));

        pm.registerCommand(this, new DelwarpCommand(this));
        pm.registerCommand(this, new WarpCommand(this));
        pm.registerCommand(this, new SetwarpCommand(this));

        pm.registerCommand(this, new HomeCommand(this));
        pm.registerCommand(this, new SethomeCommand(this));
        pm.registerCommand(this, new DelhomeCommand(this));

        pm.registerCommand(this, new SetFirstSpawnCommand(this));
        pm.registerCommand(this, new SetspawnCommand(this));
        pm.registerCommand(this, new SpawnCommand(this));

        pm.registerCommand(this, new BossBarCommand(this));
    }

    public void registerChannel() {
        //CINESTIA-CORE
        getProxy().registerChannel(channel);
        getProxy().registerChannel(rankup_channel);
        getProxy().getPluginManager().registerListener(this, new PluginMessage(this));
    }

    public void registerConfigs() {
        homes = new Homes(this);
        warps = new Warps(this);
        advancements = new Advancements(this);
        back = new Back(this);
        spawn = new Spawn(this);
        rankup = new Rankup(this);
        playerInfo = new PlayersInfo(this);
    }

    public void registerClasses() {
        luckperms = LuckPermsProvider.get();
        utilManager = new UtilManager(this);
    }

    public void registerListeners() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerListener(this, new QuitEvent(this));
        pm.registerListener(this, new PreJoinEvent());
        pm.registerListener(this, new FirstTimeListeners(this));
        //pm.registerListener(this, new discordListener(this));
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

    public Homes getHomes() {
        return homes;
    }

    public Warps getWarps() {
        return warps;
    }

    public Back getBack() {
        return back;
    }

    public Advancements getAdv() {
        return advancements;
    }

    public PlayersInfo getPlayerInfo() {
        return playerInfo;
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public Rankup getRankUp() {
        return rankup;
    }

    public boolean hasPermission(ProxiedPlayer player, String permission) {
        return player.hasPermission("cinestia.admin") || player.hasPermission(permission);
    }
}
