package fr.robotv2.cinestiabungee.utility;

import fr.robotv2.cinestiabungee.Main;

public class UtilManager {

    private final MainUtil mainUtil;
    private final RankupUtil rankupUtil;
    private final BossBarUtil bossBarUtil;
    private final RewardUtil rewardUtil;

    public UtilManager(Main main) {
        mainUtil = new MainUtil(main);
        rankupUtil = new RankupUtil(main);
        bossBarUtil = new BossBarUtil(main);
        rewardUtil = new RewardUtil(main);
    }

    public MainUtil getMain() {
        return mainUtil;
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
