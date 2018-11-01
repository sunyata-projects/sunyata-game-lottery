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

import java.util.Map;

/**
 * 开始游戏的Request
 *
 * @author Zhanglei
 */
public class StartGameRequest extends GamePlayRequest {

    private static final long serialVersionUID = 8963602917311882591L;

    private Map<String, Object> settings;

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    @Override
    @JsonIgnore
    public boolean isStartGame() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getCommand() {
        return Commands.START_GAME;
    }

    @Override
    @JsonIgnore
    public Class<? extends Response> getResponseType() {
        return StartGameResponse.class;
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "StartGameRequest{" +
                "settings=" + settings +
                ", " + super.toString() + '}';
    }
}
