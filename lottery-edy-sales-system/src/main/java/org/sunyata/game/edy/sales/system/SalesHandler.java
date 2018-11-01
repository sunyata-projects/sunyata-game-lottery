package org.sunyata.game.edy.sales.system;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.StartGameResponse;
import reactor.core.publisher.Mono;

@Component
public class SalesHandler {

    public Mono<ServerResponse> startGame(ServerRequest request) {
//        Mono<StartGameRequest> wagerRequestMono = request.bodyToMono(StartGameRequest.class);
//        StartGameRequest block = wagerRequestMono.block();
        StartGameResponse response = new StartGameResponse();
        response.setGameData(new GameData());
        response.setLastReturnCode(ReturnCode.SUCCESS.getCode());
        response.setGameCycleId("3223425439549834583945");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(response));
    }
}