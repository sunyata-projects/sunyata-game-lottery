package org.sunyata.game.common;

import org.sunyata.game.lock.MutiLock;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 16/8/10.
 */
public class MockLock extends MutiLock {
    @Override
    public boolean acquire(long l, TimeUnit timeUnit) throws Exception {
        return true;
    }

    @Override
    public void acquire() throws Exception {
        return;
    }

    @Override
    public void release() {
        return;
    }
}
