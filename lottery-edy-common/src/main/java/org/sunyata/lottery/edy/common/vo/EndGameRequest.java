/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import org.sunyata.lottery.edy.common.constant.Commands;

/**
 * 结束游戏的Request
 *
 * @author Zhanglei
 */
public class EndGameRequest extends GamePlayRequest {

    private static final long serialVersionUID = -6124359532622395217L;

    @Override
    public String getCommand() {
        return Commands.END_GAME;
    }

    @Override
    public Class<? extends Response> getResponseType() {
        return EndGameResponse.class;
    }

    @Override
    public String toString() {
        return "EndGameRequest{" + super.toString() + '}';
    }
}
