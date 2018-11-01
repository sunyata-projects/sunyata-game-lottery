package org.sunyata.game.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/11/9.
 */
public class DispatchService {

    Logger logger = LoggerFactory.getLogger(DispatchService.class);
    protected MessageQueueService queueService;


    AtomicInteger atomicInteger = new AtomicInteger();

    public void doDispatch() {
        Thread thread = new Thread(() -> {
            while (true) {
                DispatchItem element = null;
                try {
                    element = queueService.take();
                    element.getCommand().queue();
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtils.getStackTrace(e));
                } catch (Exception e) {
                    logger.error(ExceptionUtils.getStackTrace(e));
                }
                //System.out.println(System.currentTimeMillis() + "---" + element);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
