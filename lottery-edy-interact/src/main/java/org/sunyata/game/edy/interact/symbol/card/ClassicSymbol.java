package org.sunyata.game.edy.interact.symbol.card;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.GameIds;
import org.sunyata.game.edy.interact.card.model.ClassicCard;
import org.sunyata.game.edy.interact.card.store.CardClassicStore;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.GetSymbolRequest;
import org.sunyata.lottery.edy.common.vo.GetSymbolResponse;

/**
 * @author leo
 */
@Component("game" + GameIds.Classic)
public class ClassicSymbol implements GameSymbol {
    @Autowired
    CardClassicStore cardClassicStore;

    @Override
    public GetSymbolResponse getSymbolResponseForCard(GetSymbolRequest request, String gameCycleId, int gameId,
                                                      String prizeLevel) {

        GetSymbolResponse response = new GetSymbolResponse();
        response.init(gameCycleId,request.getMessageId());
        response.setGameData(new GameData());
        ClassicCard cards = null;
        try {
            int lastNum = Integer.parseInt(prizeLevel.substring(prizeLevel.length() - 1));
            int lastThreeNum = Integer.parseInt(prizeLevel.substring(prizeLevel.length() - 3));
            if (lastThreeNum == 301) {
                cards = new ClassicCard()
                        .setId("-1")
                        .setC_center("53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42")
                        .setC_under("41,40,39")
                        .setC_left("0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16")
                        .setC_right("18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35");
            } else {
                cards = cardClassicStore.getCards(lastNum);
            }
            response.setSymbolDistributionValue("id", cards.getId());
            response.setSymbolDistributionValue("center", cards.getC_center());
            response.setSymbolDistributionValue("left", cards.getC_left());
            response.setSymbolDistributionValue("right", cards.getC_right());
            response.setSymbolDistributionValue("under", cards.getC_under());
            response.setReturnCode(ReturnCode.SUCCESS);
        } catch (Exception ex) {
            response.setReturnCode(ReturnCode.FAIL);
            response.setReturnMsg(ExceptionUtils.getStackTrace(ex));
        }
        return response;
    }
    @Override
    public GetSymbolResponse getSymbolResponseForResult(String gameCycleId, int gameId) {
        return null;
    }
}
