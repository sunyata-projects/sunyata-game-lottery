package org.sunyata.game.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.sunyata.game.edy.state.manager.config.DefaultServerConfigProperties.PREFIX;

/**
 * Created by leo on 17/11/10.
 */
@Component
@ConfigurationProperties(PREFIX)
public class ConfigService {
    public static final String PREFIX = "zookeeper";

    public String getZookeeperConnectionString() {
        return zkConnectionString;
    }

    @Value("${zookeeper.connectionString}")
    String zkConnectionString;
}
