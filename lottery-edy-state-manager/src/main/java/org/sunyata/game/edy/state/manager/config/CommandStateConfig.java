package org.sunyata.game.edy.state.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.command.CommandService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by leo on 17/11/1.
 */
@Configuration
@ComponentScan("org.sunyata.game.state")
@ConditionalOnProperty(value = "octopus.command.enabled", matchIfMissing = false)
public class CommandStateConfig {
    Logger logger = LoggerFactory.getLogger(CommandStateConfig.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Value("${spring.application.name}")
    public String serviceName;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    @Autowired
    CommandService commandService;

    @PreDestroy
    public void stop() {
        commandService.stop();
    }

    @PostConstruct
    public void start() throws Exception {
        commandService.start();
    }
}
