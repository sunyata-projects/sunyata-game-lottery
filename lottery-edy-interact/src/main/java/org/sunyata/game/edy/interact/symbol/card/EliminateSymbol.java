package org.sunyata.game.edy.interact.symbol.card;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.core.json.Json;
import org.sunyata.game.contract.GameIds;
import org.sunyata.game.edy.interact.card.model.EliminateCard;
import org.sunyata.game.edy.interact.card.store.CardEliminateStore;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.GetSymbolRequest;
import org.sunyata.lottery.edy.common.vo.GetSymbolResponse;

import java.util.List;

/**
 * @author leo
 */
@Component("game_" + GameIds.Eliminate)
public class EliminateSymbol implements GameSymbol {
    @Autowired
    CardEliminateStore cardRegularStore;
    org.slf4j.Logger logger = LoggerFactory.getLogger(RegularSymbol.class);


    public EliminateCard get99Prize() {
        return new EliminateCard().setId("-1").setBomb_numbers(0).setCards("[[53,52,51,38,25,12,50,37,24," +
                "49," +
                "48,47,46,45,44,43,42,41,40,39]]");
    }

    @Override
    public GetSymbolResponse getSymbolResponseForCard(GetSymbolRequest request, String gameCycleId, int gameId,
                                                      String prizeLevel) {
        GetSymbolResponse response = new GetSymbolResponse();
        response.init(gameCycleId,request.getMessageId());
        response.setGameData(new GameData());
        String prizeId = request.getGameData().getString(Constants.JsonKeyPrizeId);
        String isOpenBoxStr = request.getSymbolDistributionStringValue(Constants.EliminateIsOpenBox);
        EliminateCard cards = null;
        try {
            //累积大奖
            if (prizeId.equalsIgnoreCase("1050201")) {
                cards = get99Prize();
            } else {
                int lastTwoNum = Integer.parseInt(prizeLevel.substring(prizeLevel.length() - 2));
                cards = cardRegularStore.getCards(lastTwoNum, isOpenBoxStr.equalsIgnoreCase("true") ? 1 : 0);
            }
            List list = Json.decodeValue(cards.getCards(), List.class);
            response.setSymbolDistributionValue("cards", list);
            response.setReturnCode(ReturnCode.SUCCESS);
        } catch (Exception ex) {
            response.setReturnCode(ReturnCode.FAIL);
            logger.error("消除赛获取牌库时出错:{}", ExceptionUtils.getStackTrace(ex));
            response.setReturnMsg(ex.getMessage());
        }
        return response;
    }

    @Override
    public GetSymbolResponse getSymbolResponseForResult(String gameCycleId, int gameId) {
        return null;
    }
}
