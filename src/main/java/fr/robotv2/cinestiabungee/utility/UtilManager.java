package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.Main;

public class UtilManager {

    private final MainUtil mainUtil;
    private final HomeUtil homeUtil;
    private final WarpUtil warpUtil;
    private final AdvUtil advUtil;
    private final BackUtil backUtil;
    private final RtpUtil rtpUtil;
    private final SpawnUtil spawnUtil;
    private final RankupUtil rankupUtil;
    private final BossBarUtil bossBarUtil;
    private final RewardUtil rewardUtil;

    public UtilManager(Main main) {
        mainUtil = new MainUtil(main);
        homeUtil = new HomeUtil(main);
        warpUtil = new WarpUtil(main);
        advUtil = new AdvUtil(main);
        backUtil = new BackUtil(main);
        rtpUtil = new RtpUtil(main);
        spawnUtil = new SpawnUtil(main);
        rankupUtil = new RankupUtil(main);
        bossBarUtil = new BossBarUtil(main);
        rewardUtil = new RewardUtil(main);
    }

    public MainUtil getMain() {
        return mainUtil;
    }

    public HomeUtil getHome() { return homeUtil; }

    public WarpUtil getWarp() { return warpUtil; }

    public AdvUtil getAdv() { return advUtil; }

    public BackUtil getBack() { return backUtil; }

    public RtpUtil getRtp() { return  rtpUtil; }

    public SpawnUtil getSpawn() {
        return spawnUtil;
    }

    public RankupUtil getRankUp() {
        return rankupUtil;
    }

    public RewardUtil getReward() {
        return rewardUtil;
    }

    public BossBarUtil getBossBarUtil() {
        return bossBarUtil;
    }
}
