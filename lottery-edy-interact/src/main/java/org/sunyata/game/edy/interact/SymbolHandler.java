package org.sunyata.game.edy.interact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.sunyata.core.json.Json;
import org.sunyata.game.edy.interact.symbol.card.GameSymbol;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.GetSymbolRequest;
import org.sunyata.lottery.edy.common.vo.GetSymbolResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author leo
 */
@RestController
public class SymbolHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(SymbolHandler.class);
    private ApplicationContext applicationContext;

    @Autowired
    RoundResultStoreService roundResultStoreService;

    @PostMapping(Constants.SymbolsUrl)
    public Mono<GetSymbolResponse> getSymbols(@Valid @RequestBody GetSymbolRequest request) {
        logger.info("getSymbols:{}" + Json.encode(request));
        Integer gameId = Integer.valueOf(request.getGameId());
        String gameCycleId = request.getGameCycleId();
        String stageId = request.getStageId();
        GameSymbol bean = this.applicationContext.getBean("game_" + gameId, GameSymbol.class);
        GetSymbolResponse response = new GetSymbolResponse();
        response.init(gameCycleId, request.getMessageId());
        response.setGameData(new GameData());

        if (stageId.contains(Constants.STAGE_PLAY_CARDS_OVER)) {
            //玩牌结束
            RoundResult result = roundResultStoreService.getResult(request.getGameCycleId());
            if (result == null) {
                response.setReturnCode(ReturnCode.FAIL);
            } else {
                response.getGameData().set("prizeId", request.getPrizeId());
                response.setSymbolDistributionValue(Constants.JsonKeyRoundResultIsWin, result.getResult());
                response.setSymbolDistributionValue(Constants.JsonKeyRoundBombNumbers, result.getBombNumbers());
                response.setReturnCode(ReturnCode.SUCCESS);
            }
        } else {
            response = bean.getSymbolResponseForCard(request, gameCycleId, gameId, request.getPrizeId());
        }
        response.setStageId(stageId);
        printJson("wager", request);
        printJson("wager", response);
        return Mono.just(response);
    }

    public void printJson(String title, Object obj) {
        System.out.println(title + ":");
        System.out.println(Json.encodePrettily(obj));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}