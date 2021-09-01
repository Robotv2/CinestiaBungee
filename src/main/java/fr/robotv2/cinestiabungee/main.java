package fr.robotv2.cinestiabungee;

import fr.robotv2.cinestiabungee.commands.tp.backCommand;
import fr.robotv2.cinestiabungee.commands.warps.delwarpCommand;
import fr.robotv2.cinestiabungee.commands.home.delhomeCommand;
import fr.robotv2.cinestiabungee.commands.home.homeCommand;
import fr.robotv2.cinestiabungee.commands.home.sethomeCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpCommand;
import fr.robotv2.cinestiabungee.commands.tp.tpaCommand;
import fr.robotv2.cinestiabungee.commands.tp.tphereCommand;
import fr.robotv2.cinestiabungee.commands.warps.setwarpCommand;
import fr.robotv2.cinestiabungee.commands.warps.warpCommand;
import fr.robotv2.cinestiabungee.configs.advancements;
import fr.robotv2.cinestiabungee.configs.back;
import fr.robotv2.cinestiabungee.configs.homes;
import fr.robotv2.cinestiabungee.configs.warps;
import fr.robotv2.cinestiabungee.listeners.quitEvent;
import fr.robotv2.cinestiabungee.pluginMessage.pluginMessage;
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
    private advancements advancements;
    private back back;

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
    }

    public void registerChannel() {
        getProxy().registerChannel(channel);
        getProxy().getPluginManager().registerListener(this, new pluginMessage(this));
    }

    public void registerConfigs() {
        homes = new homes(this);
        warps = new warps(this);
        advancements = new advancements(this);
        back = new back(this);
    }

    public void registerClasses() {
        luckperms = LuckPermsProvider.get();
        utilManager = new utilManager(this);
    }

    public void registerListeners() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new quitEvent(this));
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
}
