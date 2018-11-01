package org.sunyata.game.lock;

/**
 * Created by leo on 16/8/10.
 */

public interface LockService {
    org.sunyata.game.lock.MutiLock getlock(String lockId);
}
