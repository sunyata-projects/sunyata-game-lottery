package org.sunyata.game.inner.gateway;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 17/4/19.
 */
@Component
@Configuration
@ComponentScan("org.sunyata.game")
public class ApplicationBootStrap implements ApplicationContextAware {
    @Autowired
    InnerGatewayServerHandler handler;

    @Autowired
    InnerGatewayServer innerGatewayServer;


    private ApplicationContext applicationContext;

    @Value("${server.port}")
    private Integer port;

    @PostConstruct
    public void start() throws Exception {
        innerGatewayServer.setPort(port + 1);
        innerGatewayServer.setMessageHandler(handler);
        innerGatewayServer.start();
    }


    @PreDestroy
    public void stop() throws ExecutionException, InterruptedException {
        innerGatewayServer.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
