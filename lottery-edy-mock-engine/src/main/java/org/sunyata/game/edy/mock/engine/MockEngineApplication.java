package org.sunyata.game.edy.mock.engine; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MockEngineApplication {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(MockEngineApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
