package org.sunyata.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.sunyata.game.ApplicationServerConfigProperties.PREFIX;

/**
 * @author leo
 */
@Component
@ConfigurationProperties(PREFIX)
public class ApplicationServerConfigProperties {
    public static final String PREFIX = "lottery";


    @Value("${lottery.engineUrl}")
    String lotteryEngineUrl;

    public String getLotteryEngineUrl() {
        return lotteryEngineUrl;
    }


    public String getLotteryInteractUrl() {
        return lotteryInteractUrl;
    }

    public ApplicationServerConfigProperties setLotteryInteractUrl(String lotteryInteractUrl) {
        this.lotteryInteractUrl = lotteryInteractUrl;
        return this;
    }

    @Value("${lottery.interactUrl}")
    String lotteryInteractUrl;

}
