//package org.sunyata.game.common;
//
//import org.sunyata.game.lock.MutiLock;
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.imps.CuratorFrameworkState;
//import org.apache.curator.framework.recipes.locks.InterProcessLock;
//import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.sunyata.game.currency.models.FundAccountType;
//
//import java.util.List;
//
///**
// * Created by leo on 16/8/10.
// */
//@Component
//public class ZKLockServiceBak {
//    final Logger LOGGER = LoggerFactory.getLogger(ZKLockServiceBak.class);
//
//    //    private static final String hostString = "172.21.120.223:2181";
//    private static final String baseLockPath = "/lock";
////    private static final int timeout = 5 * 1000;
////    private static final ExecutorService testService = Executors.newFixedThreadPool(2);
//
//    @Autowired
//    ConfigService configService;
//
//    private static CuratorFramework curatorClient;
//
//    public synchronized void initCuratorFramework() {
//        if (curatorClient == null) {
//            // 启动 上面的namespace会作为一个最根的节点在使用时自动创建
////            curatorClient = CuratorFrameworkFactory.newClient(configService.getZookeeperConnectionString(),
////                    new ExponentialBackoffRetry(1000, 3));
////            curatorClient.start();
//
//            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//            curatorClient = CuratorFrameworkFactory.builder()
//                    .connectString(configService.getZookeeperConnectionString())
//                    .connectionTimeoutMs(15000)
//                    .sessionTimeoutMs(30000)
////                    .maxCloseWaitMs(5000)
//                    .retryPolicy(retryPolicy)
//                    .build();
//            curatorClient.start();
//            ZookeeperConnectionStateListener stateListener = new ZookeeperConnectionStateListener(baseLockPath,
//                    configService.getZookeeperConnectionString());
//            curatorClient.getConnectionStateListenable().addListener(stateListener);
//        }
//    }
//
//    public synchronized void checkCuratorState() {
//        CuratorFrameworkState states = curatorClient.getState();
//        if (states == CuratorFrameworkState.STOPPED) {
//            curatorClient.start();
//        }
//    }
//
//    public CuratorFramework getCuratorClient() {
//        if (curatorClient == null) {
//            initCuratorFramework();
//        }
//        checkCuratorState();
//
//        return curatorClient;
//    }
//
//    public MutiLock getlock(String lockId) {
//        if (StringUtils.isEmpty(lockId)) {
//            LOGGER.error("锁ID不能为空");
//        }
//
//        MutiLock mutiLock = new MutiLock();
//        mutiLock.setLockId(lockId);
//        mutiLock.add(new InterProcessMutex(getCuratorClient(), baseLockPath + "/" + lockId));
//
//        return mutiLock;
//    }
//
//    /**
//     * 获取资金锁的 key
//     *
//     * @param account     资金帐号
//     * @param accountType 帐号类型
//     * @return
//     */
//    public String getFundLockKey(String account, FundAccountType accountType) {
//        return "fund_" + account + "_" + accountType.getValue();
//    }
//
//
//    public String getCreateRoomLockKey(String account) {
//        return "create_room_" + account;
//    }
//
//    public MutiLock getFundLock(String account) {
//        //return new ReentrantZkLock2(baseLockPath + "/" + "fund_" + account, getZkSessionManager());
//        return getlock("fund_" + account);
//    }
//
//    public MutiLock getCreateRoomCheckIdLock() {
//        return getlock("fund_" + "create_room_check_id");
//    }
//
//    /**
//     * 获取资金锁
//     *
//     * @param account     资金帐号
//     * @param accountType 帐号类型
//     * @return
//     */
//    public MutiLock getFundLock(String account, FundAccountType accountType) {
//        return getlock(getFundLockKey(account, accountType));
//    }
//
//    public MutiLock getCreateRoomLock(String account) {
//        return getlock(getCreateRoomLockKey(account));
//    }
//
//    public MutiLock getlock(List<String> lockIds) {
//        MutiLock lock = new MutiLock();
//        for (String lockId : lockIds) {
//            MutiLock getlock = getlock(lockId);
//            lock.add(getlock);
//        }
//        return lock;
//    }
//
//    public InterProcessLock getMultiLock(List<String> lockKeys) {
//        for (int i = 0; i < lockKeys.size(); i++) {
//            lockKeys.set(i, baseLockPath + "/" + lockKeys.get(i));
//        }
//        InterProcessMultiLock multiLock = new InterProcessMultiLock(getCuratorClient(), lockKeys);
//        return multiLock;
//    }
//}
