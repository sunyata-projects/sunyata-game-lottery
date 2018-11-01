/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sunyata.lottery.edy.common.constant.Commands;

import java.math.BigDecimal;

import static org.sunyata.lottery.edy.common.constant.Constants.BD100;


/**
 * 游戏投注Request
 *
 * @author Zhanglei
 */
public class WagerRequest extends GamePlayRequest {

    private static final long serialVersionUID = -1459015209388154626L;

    public void setWagerId(String wagerId) {
        getGameData().set(GameDataKey.WAGER_ID, wagerId);
    }

    @JsonIgnore
    public String getWagerId() {
        return getGameData().getString(GameDataKey.WAGER_ID);
    }

    /**
     * 设置投注金额。消息中的投注金额单位为“元”，内部采用“分”来存储和计算
     *
     * @param wagerAmount
     */
    public void setWagerAmount(String wagerAmount) {
        getGameData().set(GameDataKey.WAGER_AMOUNT, new BigDecimal(wagerAmount).multiply(BD100).intValue());
    }

    /**
     * 获取投注金额
     *
     * @return
     */
    @JsonIgnore
    public Integer getWagerAmount() {
        return getGameData().getInteger(GameDataKey.WAGER_AMOUNT);
    }

    /**
     * 设置投注点数金额。注：仅用于消除赛
     *
     * @param wagerPointAmount
     */
    public void setWagerPointAmount(String wagerPointAmount) {
        getGameData().set(GameDataKey.WAGER_AMOUNT, Integer.valueOf(wagerPointAmount));
    }

    /**
     * 获取投注点数金额。注：仅用于消除赛
     *
     * @return
     */
    @JsonIgnore
    public Integer getWagerPointAmount() {
        return getGameData().getInteger(GameDataKey.WAGER_AMOUNT);
    }

    @JsonIgnore
    public Integer getWagerMultiple() {
        return getGameData().getInteger(GameDataKey.WAGER_MULTIPLE);
    }

    public void setWagerMultiple(Integer wagerMultiple) {
        getGameData().set(GameDataKey.WAGER_MULTIPLE, wagerMultiple);
    }

    @JsonIgnore
    public String getWagerContent() {
        return getGameData().getString(GameDataKey.WAGER_CONTENT);
    }

    public void setWagerContent(String wagerContent) {
        getGameData().set(GameDataKey.WAGER_CONTENT, wagerContent);
    }

    @Override
    public String getCommand() {
        return Commands.WAGER;
    }

    @Override
    public Class<? extends Response> getResponseType() {
        return WagerResponse.class;
    }

    @Override
    public String toString() {
        return "WagerRequest{" + super.toString() + '}';
    }
}
