/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;

/**
 * （到互动引擎）获取符号的Request
 *
 * @author Zhanglei
 */
public class RoundResultRequest extends GamePlayRequest {

    public int getIsWin() {
        return isWin;
    }

    public RoundResultRequest setIsWin(int isWin) {
        this.isWin = isWin;
        return this;
    }

    private int isWin;

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public Class<? extends Response> getResponseType() {
        return GetSymbolResponse.class;
    }

    @Override
    public String toString() {
        return "GetSymbolRequest{" + super.toString() + '}';
    }

    public String getPrizeId() {
        return getGameData().getString("prizeId");
    }
}
