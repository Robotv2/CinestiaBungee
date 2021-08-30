package fr.robotv2.cinestiabungee;

import fr.robotv2.cinestiabungee.commands.home.delhomeCommand;
import fr.robotv2.cinestiabungee.commands.home.homeCommand;
import fr.robotv2.cinestiabungee.commands.home.sethomeCommand;
import fr.robotv2.cinestiabungee.commands.tp.tphereCommand;
import fr.robotv2.cinestiabungee.commands.warps.setwarpCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpaCommand;
import fr.robotv2.cinestiabungee.commands.warps.warpCommand;
import fr.robotv2.cinestiabungee.configs.homes;
import fr.robotv2.cinestiabungee.configs.warps;
import fr.robotv2.cinestiabungee.listeners.pluginMessage;
import fr.robotv2.cinestiabungee.utility.utilManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class main extends Plugin {

    public String channel = "cinestia:channel";

    private LuckPerms luckperms;
    private utilManager utilManager;

    private homes homes;
    private warps warps;

    @Override
    public void onEnable() {
        registerCommands();
        registerConfigs();
        registerClasses();
        registerChannel();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerCommand(this, new tphereCommand(this));
        pm.registerCommand(this, new tpaCommand(this));
        pm.registerCommand(this, new tpCommand(this));

        pm.registerCommand(this, new warpCommand(this));
        pm.registerCommand(this, new setwarpCommand(this));

        pm.registerCommand(this, new homeCommand(this));
        pm.registerCommand(this, new sethomeCommand(this));
        pm.registerCommand(this, new delhomeCommand(this));
    }

    public void registerChannel() {

        this.getProxy().registerChannel(this.channel);
        this.getProxy().getPluginManager().registerListener(this, new pluginMessage(this));
    }

    public void registerConfigs() {
        homes = new homes(this);
        warps = new warps(this);
    }

    public void registerClasses() {
        luckperms = LuckPermsProvider.get();
        utilManager = new utilManager(this);
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
}
