package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.Main;
import me.devtec.bungeetheapi.configapi.Config;

public class PlayersInfo {

    Config DB;
    Main main;

    public PlayersInfo(Main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "time.yml", "Cinestia/time.yml");
        if (!get().exists("Cinestia/time.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "time.yml", "Cinestia/time.yml");
    }

    public void saveDB() {
        get().save();
    }
}
