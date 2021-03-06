package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;

public class Rankup {

    Config DB;
    Main main;

    public Rankup(Main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "rankup.yml", "Cinestia/rankup.yml");
        if(!get().exists("Cinestia/rankup.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "rankup.yml", "Cinestia/rankup.yml");
    }

    public void saveDB() {
        get().save();
    }
}
