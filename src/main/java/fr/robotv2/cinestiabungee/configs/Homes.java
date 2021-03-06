package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;

public class Homes {

    Config DB;
    Main main;

    public Homes(Main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "homes.yml", "Cinestia/homes.yml");
        if(!get().exists("Cinestia/homes.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "homes.yml", "Cinestia/homes.yml");
    }

    public void saveDB() {
        get().save();
    }
}
