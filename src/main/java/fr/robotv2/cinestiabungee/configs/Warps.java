package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;

public class Warps {

    Config DB;
    Main main;

    public Warps(Main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "warps.yml", "Cinestia/warps.yml");
        if(!get().exists("Cinestia/warps.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "warps.yml", "Cinestia/warps.yml");
    }

    public void saveDB() {
        get().save();
    }
}
