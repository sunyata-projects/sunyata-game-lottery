//package org.sunyata.game.edy.interact;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
///**
// * @author leo
// */
//@Configuration
//public class Config {
//
//    @Bean
//    @Autowired
//    public RouterFunction<ServerResponse> routerFunction(final CardHandler cardHandler) {
//        RouterFunction<ServerResponse> route = route(GET("/card/getcards"), cardHandler::getSymbols);
//        return route;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//    }
//}