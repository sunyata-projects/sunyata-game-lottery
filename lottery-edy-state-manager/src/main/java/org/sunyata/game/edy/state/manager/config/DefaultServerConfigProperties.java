package org.sunyata.game.edy.state.manager.config;

import org.sunyata.game.ServerConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.sunyata.game.edy.state.manager.config.DefaultServerConfigProperties.PREFIX;
/**
 * Created by leo on 17/11/10.
 */
@Component
@ConfigurationProperties(PREFIX)
//@Primary
public class DefaultServerConfigProperties implements ServerConfigProperties {
    public static final String PREFIX = "eureka";

    @Override
    public Integer getServerId() {
        return serverId;
    }

    @Value("${eureka.instance.metadataMap.serverId}")
    Integer serverId;



}
