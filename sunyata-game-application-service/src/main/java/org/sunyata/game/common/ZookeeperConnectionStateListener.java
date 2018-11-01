package org.sunyata.game.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

/**
 * Created by DELL on 2017/3/24.
 */
public class ZookeeperConnectionStateListener implements ConnectionStateListener {
    Logger logger = Logger.getLogger(ZookeeperConnectionStateListener.class);
    private String zkRegPathPrefix;
    private String regContent;
    public ZookeeperConnectionStateListener(String zkRegPathPrefix, String regContent) {
        this.zkRegPathPrefix = zkRegPathPrefix;
        this.regContent = regContent;
    }
    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.LOST) {
            while (true) {
                try {
                    if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                                .forPath(zkRegPathPrefix, regContent.getBytes("UTF-8"));
                        logger.info("监听获取锁");
                        break;
                    }
                } catch (InterruptedException e) {
                    logger.info("监听获取锁异常", e);
                    break;
                } catch (Exception e) {
                    logger.info("监听获取锁异常", e);
                }
            }
        }
    }
}
