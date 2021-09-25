package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;

public class spawn {

    Config DB;
    main main;

    public spawn(main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "spawn.yml", "Cinestia/spawn.yml");
        if(!get().exists("Cinestia/spawn.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "spawn.yml", "Cinestia/spawn.yml");
    }

    public void saveDB() {
        get().save();
    }
}
