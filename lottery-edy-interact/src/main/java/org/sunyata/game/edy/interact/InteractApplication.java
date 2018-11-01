package org.sunyata.game.edy.interact; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InteractApplication {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(InteractApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
