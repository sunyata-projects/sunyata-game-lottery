//package org.sunyata.game.edy.mock.engine;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
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
//    public RouterFunction<ServerResponse> routerFunction(final HomeHandler handler) {
//        RouterFunction<ServerResponse> route = route(POST("/startGame"), handler::startGame);
//        return route;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//    }
//}