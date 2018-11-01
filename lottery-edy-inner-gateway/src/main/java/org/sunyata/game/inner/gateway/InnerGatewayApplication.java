package org.sunyata.game.inner.gateway; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.sunyata.game.edy.state.manager.config.EnableGameServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGameServer
public class InnerGatewayApplication {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(InnerGatewayApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
