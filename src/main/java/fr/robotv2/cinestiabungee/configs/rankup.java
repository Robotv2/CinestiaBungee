package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;

public class rankup {

    Config DB;
    main main;

    public rankup(main main) {
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
