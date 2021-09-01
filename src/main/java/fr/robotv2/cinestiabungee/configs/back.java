package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;

public class back {

    Config DB;
    main main;

    public back(main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "back.yml", "Cinestia/back.yml");
        if(!get().exists("Cinestia/back.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "back.yml", "Cinestia/back.yml");
    }

    public void saveDB() {
        get().save();
    }
}
