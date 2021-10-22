package fr.robotv2.cinestiabungee.bossbar;

import fr.robotv2.cinestiabungee.Main;
import fr.robotv2.cinestiabungee.utility.BossBarUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static fr.robotv2.cinestiabungee.utility.MainUtil.color;

public class BossBarRunnable implements Runnable {

    private final ProxiedPlayer player;
    private final BossBar bar;
    private final float TOTAL_SECONDS;
    private final String text;
    private int remaining;

    private int taskID;

    public BossBarRunnable(ProxiedPlayer player, int seconds, String text) {
        this.player = player;
        this.remaining = seconds;
        this.TOTAL_SECONDS = seconds;
        this.text = text;

        this.bar = new BossBar(color(formatted(remaining) + " &cRestant &8- " + text), BarColor.BLUE, BarStyle.SOLID);
        this.bar.setVisible(true);
        this.bar.setProgress(1);
        this.bar.addPlayer(player);

        BossBarUtil.setBossBar(player, bar);
    }

    public void setTaskId(int value) {
        taskID = value;
    }

    @Override
    public void run() {
        try {

            if(remaining != 0) {
                this.bar.setProgress(remaining / TOTAL_SECONDS);
                this.bar.setTitle(color(formatted(remaining) + " &cRestant &8- " + text));
                this.remaining -= 1;
            } else {
                this.bar.removeAll();
                BossBarUtil.stopTask(player.getUniqueId());
            }

        } catch(NullPointerException e) {
            stop();
        }
    }

    public String formatted(int seconds) {
        int h = seconds / 3600;
        seconds %= 3600;
        int m = seconds/60;
        seconds %= 60;
        return "&c" + h + "h" + m + "m" + seconds + "s";
    }

    public void stop() {
        Main.getInstance().getProxy().getScheduler().cancel(taskID);
    }
}
