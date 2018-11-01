package org.sunyata.game.edy.interact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.sunyata.core.json.Json;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.RoundResultRequest;
import org.sunyata.lottery.edy.common.vo.RoundResultResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author leo
 */
@RestController
public class RoundResultHandler {
    @Autowired
    RoundResultStoreService roundResultStoreService;
    Logger logger = LoggerFactory.getLogger(SymbolHandler.class);

    @PostMapping("/game/play/sendResults")
    public Mono<RoundResultResponse> getSymbols(@Valid @RequestBody RoundResultRequest request) {
        logger.info("getSymbols:{}" + Json.encode(request));
        roundResultStoreService.store(request.getGameCycleId(), request.getIsWin() == 1);
        RoundResultResponse response = new RoundResultResponse();
        response.init(request.getGameCycleId(),request.getMessageId());
        response.setReturnCode(ReturnCode.SUCCESS);
        return Mono.just(response);
    }
}