package org.sunyata.game.edy.mock.engine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.sunyata.game.edy.mock.engine.ApplicationServerConfigProperties.PREFIX;


/**
 * @author leo
 */
@Component
@ConfigurationProperties(PREFIX)
public class ApplicationServerConfigProperties {
    public static final String PREFIX = "lottery";


    @Value("${lottery.interactUrl}")
    String lotteryInteractUrl;

    public String getLotteryInteractUrl() {
        return lotteryInteractUrl;
    }

}
