/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sunyata.core.util.DateUtils;
import org.sunyata.lottery.edy.common.enums.ReturnCode;

import java.util.HashMap;

/**
 * 游戏互动过程各个应答的抽象类
 * 抽象此类的目的是便于在GameCycleSerivice中提供统一的【游戏互动】消息处理接口
 *
 * @author zhanglei
 */
public abstract class GamePlayResponse implements Response {
    private static final long serialVersionUID = -2961696189682180728L;
    /**
     * Request处理结果码
     */
    private String returnCode;

    /**
     * Request处理结果描述
     */
    private String returnMsg;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息时间戳
     */
    private Long timeStamp;

    /**
     * 游戏周期ID
     */
    private String gameCycleId;

    /**
     * 游戏阶段ID
     */
    private String stageId;

    /**
     * 游戏交互数据，每个交互过程的结构不完全一致，暂时采用JSONObject表示
     */

    private GameData gameData;

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public Long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode.getCode();
    }

    @Override
    public String getReturnMsg() {
        return returnMsg;
    }

    @Override
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getGameCycleId() {
        return gameCycleId;
    }

    public void setGameCycleId(String gameCycleId) {
        this.gameCycleId = gameCycleId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public void setSymbolDistributionValue(String key, Object value) {
        if (this.gameData == null) {
            this.gameData = new GameData();
        }
        if (!this.gameData.containsKey("symbolDistribution")) {
            this.gameData.set("symbolDistribution", new HashMap<String, Object>());
        }
        HashMap<String, Object> symbolDistribution = (HashMap<String, Object>) this.gameData.get("symbolDistribution");
        symbolDistribution.put(key, value);
    }

    @JsonIgnore
    public String getSymbolDistributionStringValue(String key) {
        Object symbolDistributionObjectValue = getSymbolDistributionObjectValue(key);
        if (symbolDistributionObjectValue != null) {
            return (String) symbolDistributionObjectValue;
        }
        return null;
    }


    @JsonIgnore
    public boolean getSymbolDistributionBoolValue(String key, boolean defaultValue) {
        Object symbolDistributionObjectValue = getSymbolDistributionObjectValue(key);
        if (symbolDistributionObjectValue != null) {
            return (boolean) symbolDistributionObjectValue;
        }
        return defaultValue;
    }

    @JsonIgnore
    public Integer getSymbolDistributionIntegerValue(String key) {
        Object symbolDistributionObjectValue = getSymbolDistributionObjectValue(key);
        if (symbolDistributionObjectValue != null) {
            return (Integer) symbolDistributionObjectValue;
        }
        return null;
    }

    @JsonIgnore
    public Object getSymbolDistributionObjectValue(String key) {
        if (this.gameData == null) {
            return null;
        }
        if (this.gameData.containsKey("symbolDistribution")) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) this.gameData.get("symbolDistribution");
            return hashMap.getOrDefault(key, null);
        }
        return null;
    }

    @JsonIgnore
    public Integer getInteger(String key) {
        if (this.gameData != null) {
            return this.gameData.getInteger(key);
        }
        return null;
    }

    @JsonIgnore
    public String getString(String key) {
        if (this.gameData != null) {
            return this.gameData.getString(key);
        }
        return null;
    }

    @Override
    @JsonIgnore
    public Boolean isSuccess() {
        return ReturnCode.get(returnCode) == ReturnCode.SUCCESS;
    }


    public void init(String gameCycleId, String messageId) {
        this.setTimeStamp(Long.valueOf(DateUtils.getNowTimeStamp()));
        //DateUtils.getNowTimeStampWithMillSecond()
        this.setMessageId(messageId);
        this.setGameCycleId(gameCycleId);
    }

    @Override
    public String toString() {
        return "GamePlayResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", messageId='" + messageId + '\'' +
                ", timeStamp=" + timeStamp +
                ", gameCycleId='" + gameCycleId + '\'' +
                ", stageId='" + stageId + '\'' +
                ", gameData=" + gameData +
                '}';
    }
}
