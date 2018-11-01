package ai;

/**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AIApplication {


    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(AIApplication.class).web(true).run(args);
    }

    @PostConstruct
    public void initCoordination() {

    }

}
