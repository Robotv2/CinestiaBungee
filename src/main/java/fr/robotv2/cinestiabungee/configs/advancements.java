package fr.robotv2.cinestiabungee.configs;

import fr.robotv2.cinestiabungee.main;
import me.devtec.bungeetheapi.configapi.Config;

public class advancements {

    Config DB;
    main main;

    public advancements(main main) {
        this.main = main;
        setup();
    }

    public void setup() {
        DB = Config.loadConfig(main, "advancements.yml", "Cinestia/advancements.yml");
        if(!get().exists("Cinestia/advancements.yml")) {
            saveDB();
        }
    }

    public Config get() {
        return DB;
    }

    public void reload() {
        DB = Config.loadConfig(main, "advancements.yml", "Cinestia/advancements.yml");
    }

    public void saveDB() {
        get().save();
    }
}
