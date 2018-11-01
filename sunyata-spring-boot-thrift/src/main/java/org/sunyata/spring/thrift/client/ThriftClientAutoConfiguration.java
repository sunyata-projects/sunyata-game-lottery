package org.sunyata.spring.thrift.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.cloud.sleuth.SpanInjector;

/**
 * Created by aleksandr on 27.06.16.
 */
@Configuration
public class ThriftClientAutoConfiguration {
    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
//    @Bean
//    @ConditionalOnMissingBean(name = "thriftTransportSpanInjector")
//    SpanInjector<TTransport> thriftTransportSpanInjector() {
//        return new ThriftHttpTransportSpanInjector();
//    }
}
