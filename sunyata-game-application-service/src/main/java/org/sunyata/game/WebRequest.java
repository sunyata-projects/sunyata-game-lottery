package org.sunyata.game;

import org.sunyata.lottery.edy.common.vo.*;

/**
 * @author leo
 */

public interface WebRequest {
    /**
     * @param channelId 渠道号
     * @param accountId 帐号
     * @param gameId    游戏标识
     * @param gameData  数据
     * @return 请求开始游戏
     * 实现开始游戏的功能
     */
    StartGameResponse startGame(String channelId, String accountId, String gameId, GameData gameData) throws Exception;

    //    WagerResponse wager(String channelId, String accountId, String gameId, String gameCycleId, String wagerId,
//                        String wagerAmount);
    WagerResponse wager(String channelId, String accountId, String gameId, String gameCycleId, String stageId,
                        GameData gameData);

    WagerResponse regularRaiseWager(String channelId, String accountId, int gameId, String gameCycleId, int
            times, String s);

    CheckPointResponse checkPoint(String channelId, String accountId, String gameId, String gameCycleId,
                                  String stageId);

    RoundResultResponse sendRoundResult(String channelId, String accountId, String gameId, String gameCycleId,
                                        boolean result);

    EndGameResponse endGame(String channelId, String accountId, String gameId, String gameCycleId, String
            stageId);
}
