package org.sunyata.game.gateway;

import org.springframework.stereotype.Component;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/10/30.
 */
@Component
public class IdPool {


    private final TreeSet<Short> idPool = new TreeSet<>();
    private short max;

    private final int maxConnect;
    private final int freeWaitNums;

    public IdPool() {
        maxConnect = (int) (1024 * 1.3 + 1);
        freeWaitNums = 1024 * 1 / 3;
    }

    public short generateId() throws Exception {
        Short id;
        if (idPool.size() > freeWaitNums) {
            id = idPool.pollFirst();
        } else {
            if (max >= maxConnect) {
                throw new Exception("池子已满，当前连接数量:" + max + ", 最大值:" + maxConnect);
            }
            id = max;
            max++;
        }
        return id;
    }

    public void add(short id) {
        idPool.add(id);
    }

    public static void main(String[] args) throws Exception {
        IdPool pool = new IdPool();
        //List<Short> ids = new ArrayList<>();
        AtomicInteger c = new AtomicInteger();
        for (int i = 0; i < 13; i++) {
            Short i1 = pool.generateId();
            System.out.println(i1);
            c.incrementAndGet();
        }
        for (int i = 0; i < 13; i++) {
            pool.add((short) i);
            c.decrementAndGet();
        }
        for (int i = 0; i < 11; i++) {
            Short i1 = pool.generateId();
            System.out.println(c.incrementAndGet());
            //ids.add(i1);
        }
        for (int i = 0; i < 11; i++) {
            pool.add((short) i);
            c.decrementAndGet();
        }
        for (int i = 0; i < 11; i++) {
            Short i1 = pool.generateId();
            System.out.println(c.incrementAndGet());
            //ids.add(i1);
        }
    }
}
