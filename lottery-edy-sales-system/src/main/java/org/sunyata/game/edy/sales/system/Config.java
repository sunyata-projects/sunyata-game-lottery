package org.sunyata.game.edy.sales.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Config {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunction(final SalesHandler calculatorHandler) {
//        return RouterFunctions.route(RequestPredicates.path("/startgame"), request ->
//                request.queryParam("operator").map(operator ->
//                        Mono.justOrEmpty(ReflectionUtils.findMethod(GreetingHandler.class, operator, ServerRequest
// .class))
//                                .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method,
// calculatorHandler, request))
//                                .switchIfEmpty(ServerResponse.badRequest().build())
//                                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))
//                        .orElse(ServerResponse.badRequest().build()));
        RouterFunction<ServerResponse> route = route(GET("/startgame"), calculatorHandler::startGame);
        return route;
    }
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}