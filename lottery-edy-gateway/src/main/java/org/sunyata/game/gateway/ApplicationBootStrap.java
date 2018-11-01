package org.sunyata.game.gateway;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.sunyata.game.gateway.server.ToUserListenHandler;
import org.sunyata.game.gateway.server.ToUserListenServer;
import org.sunyata.game.gateway.server.WebSocketServer;
import org.sunyata.game.gateway.server.WebSocketServerHandler;

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
    private ApplicationContext applicationContext;

    @Autowired
    WebSocketServer webSocketServer;

    @Autowired
    WebSocketServerHandler webSocketServerHandler;

    @Autowired
    ToUserListenServer toUserListenServer;

    @Autowired
    ToUserListenHandler toUserListenHandler;


    @Value("${server.port}")
    private Integer port;

    @PostConstruct
    public void start() throws Exception {
        toUserListenServer.setPort(port + 1);
        toUserListenServer.setMessageHandler(toUserListenHandler);
        toUserListenServer.start();

        webSocketServer.setPort(port + 2);
        webSocketServer.setMessageHandler(webSocketServerHandler);
        webSocketServer.start();
    }


    @PreDestroy
    public void stop() throws ExecutionException, InterruptedException {
        webSocketServer.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
