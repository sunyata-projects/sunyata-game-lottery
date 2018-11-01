/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import org.sunyata.core.json.JsonObject;

import java.math.BigDecimal;

import static org.sunyata.lottery.edy.common.constant.Constants.BD100;


/**
 * CheckPointResponse
 *
 * @author Zhanglei
 */
public class CheckPointResponse extends GamePlayResponse {

    private static final long serialVersionUID = 154755349880019994L;

    public void setSymbolId(String symbolId) {
        getGameData().put(GameDataKey.SYMBOL_ID, symbolId);
    }

    public void setSymbolDistribution(JsonObject symbolDistribution) {
        getGameData().put(GameDataKey.SYMBOL_DISTRIBUTION, symbolDistribution);
    }

    public void setPrizeId(String prizeId) {
        getGameData().put(GameDataKey.PRIZE_ID, prizeId);
    }

    public void setPrizeName(String prizeName) {
        getGameData().put(GameDataKey.PRIZE_NAME, prizeName);
    }

    public void setPrizeAmount(Integer prizeAmount) {
        getGameData().put(GameDataKey.PRIZE_AMOUNT,
            new BigDecimal(prizeAmount).divide(BD100, 2, BigDecimal.ROUND_HALF_UP).toString());
    }

    public void setPrizeContent(String prizeContent) {
        getGameData().put(GameDataKey.PRIZE_CONTENT, prizeContent);
    }

    @Override
    public String toString() {
        return "CheckPointResponse{" + super.toString() + '}';
    }
}
