package org.sunyata.game.edy.interact.symbol.card;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.GameIds;
import org.sunyata.game.edy.interact.card.model.RegularCard17;
import org.sunyata.game.edy.interact.card.model.RegularCard37;
import org.sunyata.game.edy.interact.card.store.CardRegularStore;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.GetSymbolRequest;
import org.sunyata.lottery.edy.common.vo.GetSymbolResponse;

import java.util.HashMap;

/**
 * @author leo
 */
@Component("game_" + GameIds.Regular)
public class RegularSymbol implements GameSymbol {
    org.slf4j.Logger logger = LoggerFactory.getLogger(RegularSymbol.class);
    @Autowired
    CardRegularStore cardRegularStore;
    static HashMap<String, String> centerIdmap = new HashMap<>();

    @Override
    public GetSymbolResponse getSymbolResponseForCard(GetSymbolRequest request, String gameCycleId, int gameId,
                                                      String prizeLevel) {
        GetSymbolResponse response = new GetSymbolResponse();
        response.init(gameCycleId, request.getMessageId());
        response.setGameData(new GameData());

        try {
            String stageId = request.getStageId();
            if (stageId.equalsIgnoreCase(Stages.Game101.FIRST_WAGER)) {
                RegularCard17 card17WithRandom = cardRegularStore.getCard17WithRandom();
                response.setSymbolDistributionValue("center", card17WithRandom.getC_center());
                centerIdmap.put(request.getGameCycleId(), card17WithRandom.getId());
                response.setSymbolDistributionValue("centerId", card17WithRandom.getId());
            } else {
                //String centerId = request.getSymbolDistributionStringValue("centerId");
                String centerId = centerIdmap.getOrDefault(request.getGameCycleId(), "");
                int lastNum = Integer.parseInt(prizeLevel.substring(prizeLevel.length() - 1));
                int bombNums = lastNum;
                if (lastNum == 5 || lastNum == 9) {
                    bombNums = -1;
                }
                if (lastNum == 4) {
                    bombNums = 0;
                }
                RegularCard17 card17ById = cardRegularStore.getCard17ById(centerId);
                RegularCard37 card37 = cardRegularStore.getCard37(bombNums, centerId);
                response.setSymbolDistributionValue("cardId", card37.getId());
                response.setSymbolDistributionValue("centerId", card37.getCenter_id());
                response.setSymbolDistributionValue("center", card17ById.getC_center());
                response.setSymbolDistributionValue("left", card37.getC_left());
                response.setSymbolDistributionValue("right", card37.getC_right());
                response.setSymbolDistributionValue("under", card37.getC_under());
            }
            response.setReturnCode(ReturnCode.SUCCESS);
        } catch (Exception ex) {
            logger.error("常规赛获取牌库时出错:{}", ex);
            response.setReturnCode(ReturnCode.FAIL);
            response.setReturnMsg(ex.getMessage());
        }
        return response;
    }

    @Override
    public GetSymbolResponse getSymbolResponseForResult(String gameCycleId, int gameId) {
        return null;
    }
}
