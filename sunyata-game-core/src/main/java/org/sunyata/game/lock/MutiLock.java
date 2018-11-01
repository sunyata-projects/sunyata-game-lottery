package org.sunyata.game.lock;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 16/8/10.
 */
@Service
public class MutiLock implements InterProcessLock {
    final Logger LOGGER = LoggerFactory.getLogger(MutiLock.class);

    private String lockId;

    private List<InterProcessLock> locks = new ArrayList<>();

    public void add(InterProcessLock interProcessLock) {
        locks.add(interProcessLock);
    }

    @Override
    public void acquire() throws Exception {
        for (InterProcessLock lock : locks) {
            synchronized ("lock_" + getLockId()){
                LOGGER.info("锁开始:"  + getLockId() + "," + lock.toString());
                lock.acquire();
                LOGGER.info("锁开始成功:"  + getLockId() + "," + lock.toString());
            }
        }
    }

    @Override
    public boolean acquire(long l, TimeUnit timeUnit) throws Exception {
        for (InterProcessLock lock : locks) {
            LOGGER.info("锁开始:" + getLockId() + "," + lock.toString());
            if (!lock.acquire(l, timeUnit)) {
                LOGGER.info("未能获取锁:" + getLockId() + "," + lock.toString());
                throw new IllegalStateException(lock.toString() + " could not acquire the lock");
            } else {
                LOGGER.info("锁开始成功:" + getLockId() + "," + lock.toString());
            }
        }
        return true;
    }

    @Override
    public void release(){
        if (null!=locks && locks.size()>0){
            for (int i=locks.size()-1; i>=0; i--) {
                synchronized ("unlock_" + getLockId()){
                    InterProcessLock lock = locks.get(i);
                    LOGGER.info("=锁释放:" + getLockId());
                    try{
                        lock.release();
                        LOGGER.info("=锁释放成功:" + getLockId());
                    }catch (Exception ex){
                        LOGGER.info("=锁释放失败:" + getLockId(), ex);
                    }
                }
            }
        }
    }

    @Override
    public boolean isAcquiredInThisProcess() {
        return false;
    }

    public String getLockId() {
        return lockId;
    }

    public MutiLock setLockId(String lockId) {
        this.lockId = lockId;
        return this;
    }
}
