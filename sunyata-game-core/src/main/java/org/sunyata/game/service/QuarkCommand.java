package org.sunyata.game.service;

import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;

/**
 * Created by leo on 17/7/27.
 */
public class QuarkCommand extends HystrixCommand {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(QuarkCommand.class);
    private Runnable runnable;


    public QuarkCommand(Runnable runnable) {
//        super(HystrixCommandGroupKey.Factory.asKey("RetryGroup"),100000);

        super(
                Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("AnyClientG"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("AnyClientCommandKey"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("AnyClient" + "P"))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                                        .withExecutionIsolationStrategy(
                                                HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                        .withCircuitBreakerEnabled(false)
                                .withCircuitBreakerRequestVolumeThreshold(200)
                                .withExecutionTimeoutEnabled(false)
                                //.withExecutionTimeoutInMilliseconds()
                                        .withFallbackEnabled(true)
                        )
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.defaultSetter()
                                .withCoreSize(100)
                                .withMaxQueueSize(10000)
                                .withQueueSizeRejectionThreshold(10000))
        );
        this.runnable = runnable;
    }

    /**
     * 这个方法里面封装了正常的逻辑，我们可以传入正常的业务逻辑
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object run() throws Exception {
        try {
            //long now = System.currentTimeMillis();
            runnable.run();
            return null;
        } catch (Exception ex) {
            logger.error("ERROR:{}", ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

    /**
     * 这个方法中定义了出现异常时, 默认返回的值(相当于服务的降级)。
     *
     * @return
     */
    @Override
    protected Object getFallback() {
        Throwable executionException = getExecutionException();
        if (executionException instanceof RejectedExecutionException) {

        } else if (executionException instanceof RuntimeException) {// short-circuited
            //messageQueueService.enQueue(3 * 60 * 1000, serialNo, true);//延时30秒钟最低
        } else if (executionException instanceof HystrixTimeoutException) {

        } else {
            logger.error("发生异常,未做处理");
        }
        logger.error("FallBack Exceptions:{}", ExceptionUtils.getStackTrace(getExecutionException()));
        return null;

    }

}
