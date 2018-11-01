package org.sunyata.game.client;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.sunyata.game.service.ClientServerInfo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/11/26.
 */
@Component
public class AnyClientFactory extends BaseKeyedPooledObjectFactory<ClientServerInfo, AnyClient> {
    private static final Logger logger = LoggerFactory.getLogger(AnyClientFactory.class);

    public AnyClientFactory() {
        super();
    }

    @Autowired
    ApplicationContext applicationContext;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public AnyClient create(ClientServerInfo key) throws Exception {
        AnyClientHandler anyClientHandler = new AnyClientHandler(() -> runConnectCallback(key));
        applicationContext.getAutowireCapableBeanFactory().autowireBean(anyClientHandler);
        AnyClient client = new AnyClient(key, anyClientHandler);
        anyClientHandler.setClient(client);
        int i = atomicInteger.incrementAndGet();
        logger.info("anyClient实例数量:{}", i);
        return client;
    }

    private void runConnectCallback(ClientServerInfo key) {
    }

    @Override
    public void activateObject(ClientServerInfo key, PooledObject<AnyClient> p) throws Exception {
        AnyClient object = p.getObject();
        if (!object.isConnect()) {
            object.connect();
            object.setConnect(true);
        }
        if (!object.isActive()) {
            throw new Exception("连接已经断开");
        }
//        if (!object.isConnect()) {
//            object.connect();
//            object.setConnect(true);
//        }
    }

    @Override
    public PooledObject<AnyClient> wrap(AnyClient value) {
        return new AnyClientPooledObject(value);
    }
}
