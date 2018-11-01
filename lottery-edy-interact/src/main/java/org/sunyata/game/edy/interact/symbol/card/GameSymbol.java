package org.sunyata.game.edy.interact.symbol.card;

import org.sunyata.lottery.edy.common.vo.GetSymbolRequest;
import org.sunyata.lottery.edy.common.vo.GetSymbolResponse;

public interface GameSymbol {
    GetSymbolResponse getSymbolResponseForCard(GetSymbolRequest request, String gameCycleId, int gameId, String
            prizeLevel);

    GetSymbolResponse getSymbolResponseForResult(String gameCycleId, int gameId);


}
