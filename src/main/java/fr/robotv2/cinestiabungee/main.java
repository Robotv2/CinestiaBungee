package fr.robotv2.cinestiabungee;

import fr.robotv2.cinestiabungee.commands.home.delhomeCommand;
import fr.robotv2.cinestiabungee.commands.home.homeCommand;
import fr.robotv2.cinestiabungee.commands.home.sethomeCommand;
import fr.robotv2.cinestiabungee.commands.spawn.setFirstSpawnCommand;
import fr.robotv2.cinestiabungee.commands.spawn.setspawnCommand;
import fr.robotv2.cinestiabungee.commands.spawn.spawnCommand;
import fr.robotv2.cinestiabungee.commands.tp.backCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpaCommand;
import fr.robotv2.cinestiabungee.commands.tp.tphereCommand;
import fr.robotv2.cinestiabungee.commands.warps.delwarpCommand;
import fr.robotv2.cinestiabungee.commands.warps.setwarpCommand;
import fr.robotv2.cinestiabungee.commands.warps.warpCommand;
import fr.robotv2.cinestiabungee.configs.*;
import fr.robotv2.cinestiabungee.discord.discordListener;
import fr.robotv2.cinestiabungee.listeners.preJoinEvent;
import fr.robotv2.cinestiabungee.listeners.quitEvent;
import fr.robotv2.cinestiabungee.pluginMessage.pluginMessage;
import fr.robotv2.cinestiabungee.rankup.rankupListener;
import fr.robotv2.cinestiabungee.utility.utilManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class main extends Plugin {

    public final String channel = "cinestia:channel";
    public final String rankup_channel = "rankup:channel";

    private LuckPerms luckperms;
    private utilManager utilManager;

    private homes homes;
    private warps warps;
    private advancements advancements;
    private back back;
    private spawn spawn;
    private rankup rankup;

    @Override
    public void onEnable() {
        Long current = System.currentTimeMillis();

        registerCommands();
        registerConfigs();
        registerClasses();
        registerChannel();
        registerListeners();

        getUtils().getMain().showBanner(current);
    }

    public void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerCommand(this, new tphereCommand(this));
        pm.registerCommand(this, new tpaCommand(this));
        pm.registerCommand(this, new tpCommand(this));
        pm.registerCommand(this, new backCommand(this));

        pm.registerCommand(this, new delwarpCommand(this));
        pm.registerCommand(this, new warpCommand(this));
        pm.registerCommand(this, new setwarpCommand(this));

        pm.registerCommand(this, new homeCommand(this));
        pm.registerCommand(this, new sethomeCommand(this));
        pm.registerCommand(this, new delhomeCommand(this));

        pm.registerCommand(this, new setFirstSpawnCommand(this));
        pm.registerCommand(this, new setspawnCommand(this));
        pm.registerCommand(this, new spawnCommand(this));
    }

    public void registerChannel() {
        //CINESTIA-CORE
        getProxy().registerChannel(channel);
        getProxy().registerChannel(rankup_channel);
        getProxy().getPluginManager().registerListener(this, new pluginMessage(this));
    }

    public void registerConfigs() {
        homes = new homes(this);
        warps = new warps(this);
        advancements = new advancements(this);
        back = new back(this);
        spawn = new spawn(this);
        rankup = new rankup(this);
    }

    public void registerClasses() {
        luckperms = LuckPermsProvider.get();
        utilManager = new utilManager(this);
    }

    public void registerListeners() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerListener(this, new quitEvent(this));
        pm.registerListener(this, new preJoinEvent());
        pm.registerListener(this, new discordListener(this));
        pm.registerListener(this, new rankupListener(this));
    }

    public LuckPerms getLuckperms() {
        return luckperms;
    }

    public utilManager getUtils() {
        return utilManager;
    }

    public homes getHomes() {
        return homes;
    }

    public warps getWarps() {
        return warps;
    }

    public back getBack() {
        return back;
    }

    public advancements getAdv() { return advancements; }

    public spawn getSpawn() { return spawn;}

    public rankup getRankUp() { return rankup; }

    public boolean hasPermission(ProxiedPlayer player, String permission) {
        return (player.hasPermission("cinestia.admin") || player.hasPermission(permission));
    }
}
