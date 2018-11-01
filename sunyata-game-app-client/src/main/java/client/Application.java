package client; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableScheduling
//@EnableQuarkClient
//@EnableThriftClient
public class Application {

//    @Autowired
//    BusinessManager businessManager;

    public static void main(String[] args) throws InterruptedException, IOException {
//        int[] aaa = new int[]{2,3,4,45,5};
//        String citynames[]={"Agra", "Mysore", "Chandigarh", "Bhopal"};
//
//        List<String> strings = Arrays.asList(citynames);
	  /*Array to ArrayList conversion*/
//        ArrayList<String> citylist= new ArrayList<String>(Arrays.asList(citynames));
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }


    //
//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }
    @PostConstruct
    public void initCoordination() throws InterruptedException, IOException, URISyntaxException {
//        WebSocketClassicClient.start();
    }

//    @Bean
//    BusinessManager businessManager() throws Exception {
//        BusinessManager c = new MultipleThreadBusinessManager();
//        c.setScanPackage("org.sunyata.quark.embed.demo.springcloud.components");
//        c.setServiceLocator(SpringServiceLocator.class);
//        c.setEventPublisher(SpringEventEventPublisher.class);
//        c.initialize();
////        c.register(ParallelBusinessComponent.class);
////        c.register(SingleBusinessComponent.class);
////        c.register(TwoBusinessComponent.class);
//        return c;
//    }
}
