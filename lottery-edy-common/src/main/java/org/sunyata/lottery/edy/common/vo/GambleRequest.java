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
 * Gamble投注Request
 *
 * @author Zhanglei
 */
public class GambleRequest extends WagerRequest {

    private static final long serialVersionUID = -6999923451546405160L;

    @Override
    public String getCommand() {
        return Commands.GAMBLE;
    }

    @Override
    public Class<? extends Response> getResponseType() {
        return GambleResponse.class;
    }

    @Override
    public String toString() {
        return "GambleRequest{" + super.toString() + '}';
    }
}
