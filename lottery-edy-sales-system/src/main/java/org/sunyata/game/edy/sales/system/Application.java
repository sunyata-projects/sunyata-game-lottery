package org.sunyata.game.edy.sales.system; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
