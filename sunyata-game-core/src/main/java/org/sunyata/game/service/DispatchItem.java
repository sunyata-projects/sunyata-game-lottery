package org.sunyata.game.service;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17/11/9.
 */
public class DispatchItem implements Delayed {
    private Runnable runnable;
    private long delay = 0; //延迟时间

    public QuarkCommand getCommand() {
        return new QuarkCommand(runnable);
    }

    private final long expire;  //到期时间
    private final long now; //创建时间

    public DispatchItem(Runnable runnable, long delay) {
        this.runnable = runnable;
        this.delay = delay;
        expire = System.currentTimeMillis() + delay;    //到期时间 = 当前时间+延迟时间
        now = System.currentTimeMillis();

    }


    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DelayedElement{");
        sb.append("delay=").append(delay);
        sb.append(", expire=").append(expire);
        sb.append(", now=").append(now);
        sb.append('}');
        return sb.toString();
    }
}
