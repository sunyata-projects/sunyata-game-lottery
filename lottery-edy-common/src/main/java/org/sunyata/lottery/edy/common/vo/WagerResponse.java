/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */
package org.sunyata.lottery.edy.common.vo;

import java.math.BigDecimal;

import static org.sunyata.lottery.edy.common.constant.Constants.BD100;


/**
 * 游戏投注Response
 *
 * @author ZhangLei
 */
public class WagerResponse extends GamePlayResponse {

    private static final long serialVersionUID = -5491291748626276355L;

    public void setSymbolId(String symbolId) {
        getGameData().put(GameDataKey.SYMBOL_ID, symbolId);
    }

    public void setSymbolDistribution(String symbolDistribution) {
        getGameData().put(GameDataKey.SYMBOL_DISTRIBUTION, symbolDistribution);
    }

    public void setPrizeId(String prizeId) {
        getGameData().put(GameDataKey.PRIZE_ID, prizeId);
    }

    public void setPrizeName(String prizeName) {
        getGameData().put(GameDataKey.PRIZE_NAME, prizeName);
    }

    public void setPrizeAmount(Integer prizeAmount) {
        // 中奖金额内部用“分”为单位，在消息中使用“元”为单位
        getGameData().put(GameDataKey.PRIZE_AMOUNT,
            new BigDecimal(prizeAmount).divide(BD100, 2, BigDecimal.ROUND_HALF_UP).toString());
    }

    /**
     * 设置中奖点数金额。注：仅用于斗地主消除赛点数关
     *
     * @param prizePointAmount
     */
    public void setPrizePointAmount(Integer prizePointAmount) {
        getGameData().put(GameDataKey.PRIZE_AMOUNT, String.valueOf(prizePointAmount));
    }

    public void setPrizeContent(String prizeContent) {
        getGameData().put(GameDataKey.PRIZE_CONTENT, prizeContent);
    }

    @Override
    public String toString() {
        return "WagerResponse{" + super.toString() + '}';
    }
}
