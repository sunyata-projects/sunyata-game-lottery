package org.sunyata.game.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

/**
 * Created by leo on 17/11/9.
 */
@Component
public class MessageQueueService {
    Logger logger = LoggerFactory.getLogger(MessageQueueService.class);

    DelayQueue<DispatchItem> queue;

    public MessageQueueService() {
        queue = new DelayQueue<>();
    }

    public DispatchItem take() throws InterruptedException {
        return queue.take();
    }

    public void enQueue(Runnable runnable) {
        queue.offer(new DispatchItem(runnable, 0));
    }
}
