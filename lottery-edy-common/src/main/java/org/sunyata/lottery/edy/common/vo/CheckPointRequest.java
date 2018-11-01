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
 * CheckPointRequest
 *
 * @author Zhanglei
 * @date 2017/05/10
 */
public class CheckPointRequest extends GamePlayRequest {

    private static final long serialVersionUID = 1993137743530469710L;

    @Override
    public Class<? extends Response> getResponseType() {
        return CheckPointResponse.class;
    }

    @Override
    public String getCommand() {
        return Commands.CHECKPOINT;
    }

    @Override
    public String toString() {
        return "CheckPointRequest{" + super.toString() + '}';
    }
}
