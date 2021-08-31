package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.main;

public class utilManager {

    private mainUtil mainUtil;
    private homeUtil homeUtil;
    private warpUtil warpUtil;
    private advUtil advUtil;

    public utilManager(main main) {
        mainUtil = new mainUtil(main);
        homeUtil = new homeUtil(main);
        warpUtil = new warpUtil(main);
        advUtil = new advUtil(main);
    }

    public mainUtil getMain() {
        return mainUtil;
    }

    public homeUtil getHome() { return homeUtil; }

    public warpUtil getWarp() { return warpUtil; }

    public advUtil getAdv() { return advUtil; }
}
