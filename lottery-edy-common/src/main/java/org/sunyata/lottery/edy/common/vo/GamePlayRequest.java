/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sunyata.core.util.DateUtils;
import org.sunyata.lottery.edy.common.vo.addition.ValidateBean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;


/**
 * 游戏互动过程Request的抽象类
 *
 * @author zhanglei
 */
@ValidateBean
public abstract class GamePlayRequest implements Request {
    private static final long serialVersionUID = -7890500762157250484L;
    /**
     * 消息ID
     */
    @NotBlank
    private String messageId;

    /**
     * 消息时间戳
     */
    @NotNull
    private Long timeStamp;

    public String getChannelId() {
        return channelId;
    }

    public GamePlayRequest setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * 玩家ID
     */
    @NotBlank
    private String channelId;


    @NotBlank
    private String accountId;

    /**
     * 游戏ID
     */
    @NotNull
    private Integer gameId;

    /**
     * 游戏周期ID，填写规则：
     * 1. 首次Request：gameCycleId为null，由系统分配唯一的GameCycleId，在Response中带给GameClient
     * 2. 后续Request：填写首次Response中的gameCycleId
     */
    //@NotNull
    private String gameCycleId;

    /**
     * 游戏阶段ID，填写规则：
     * 1. 游戏过程分成多个阶段，stageId用来区分不同阶段、用于过程校验、游戏恢复使用。
     * 2. stageId必须在GameClient、GameEngine之间统一标识
     */
    @NotBlank
    private String stageId;

    /**
     * 游戏交互数据，每个交互过程的结构不完全一致，暂时采用JSONObject表示
     */
    private GameData gameData;
    private String version;

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

    public Integer getGameId() {
        return gameId;
    }

    /**
     * 如果override了某个属性的set方法，必须通过注解为jackson指定一个明确的反序列化方法
     *
     * @param gameId
     */
    @JsonProperty("gameId")
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public boolean isStartGame() {
        return false;
    }

    public void init(String channelId, String accountId, String gameId, String gameCycleId, String stageId) {
        this.setTimeStamp(Long.valueOf(DateUtils.getNowTimeStamp()));
        this.setMessageId(DateUtils.getNowTimeStampWithMillSecond());
        this.setAccountId(accountId);
        this.setGameId(Integer.valueOf(gameId));
        this.setChannelId(channelId);
        this.setGameCycleId(gameCycleId);
        this.setStageId(stageId);
        this.setVersion("1.0");
        this.gameData = new GameData();
    }

    public void init(String channelId, String accountId, String gameId, String gameCycleId, String stageId, GameData
            gameData) {
        this.setTimeStamp(Long.valueOf(DateUtils.getNowTimeStamp()));
        this.setMessageId(DateUtils.getNowTimeStampWithMillSecond());
        this.setAccountId(accountId);
        this.setGameId(Integer.valueOf(gameId));
        this.setChannelId(channelId);
        this.setGameCycleId(gameCycleId);
        this.setStageId(stageId);
        this.setVersion("1.0");
        this.gameData = gameData;
    }

    public GameData put(String key, Object obj) {
        this.gameData.put(key, obj);
        return this.gameData;
    }
    public void setSymbolDistributionValue(String key, Object value) {
        if (this.gameData == null) {
            this.gameData = new GameData();
        }
        if (!this.gameData.containsKey("symbolDistribution")) {
            this.gameData.set("symbolDistribution", new GameData());
        }
        GameData symbolDistribution = (GameData) this.gameData.get("symbolDistribution");
        symbolDistribution.set(key, value);
    }

    @JsonIgnore
    public Object getSymbolDistributionValue(String key) {
        if (this.gameData == null) {
            return null;
        }
        if (this.gameData.containsKey("symbolDistribution")) {
            GameData symbolDistribution = (GameData) this.gameData.get("symbolDistribution");
            return symbolDistribution.get(key);
        }
        return null;
    }

    @JsonIgnore
    public String getSymbolDistributionStringValue(String key) {
        Object symbolDistributionObjectValue = getSymbolDistributionObjectValue(key);
        if(symbolDistributionObjectValue!=null){
            return (String) symbolDistributionObjectValue;
        }
        return null;
    }

    @JsonIgnore
    public Integer getSymbolDistributionIntegerValue(String key) {
        Object symbolDistributionObjectValue = getSymbolDistributionObjectValue(key);
        if(symbolDistributionObjectValue!=null){
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
            HashMap<String,Object> hashMap = (HashMap<String, Object>) this.gameData.get("symbolDistribution");
            return hashMap.getOrDefault(key,null);
        }
        return null;
    }
    @JsonIgnore
    public String getString(String key) {
        return this.gameData.getString(key);
    }

    @JsonIgnore
    public Object get(String key) {
        return this.gameData.get(key);
    }

    @JsonIgnore
    public Integer getInteger(String key) {
        return this.gameData.getInteger(key);
    }

    @Override
    public String toString() {
        return "GamePlayRequest{" +
                "messageId='" + messageId + '\'' +
                ", timeStamp=" + timeStamp +
                ", accountId='" + accountId + '\'' +
                ", gameId=" + gameId +
                ", gameCycleId='" + gameCycleId + '\'' +
                ", stageId='" + stageId + '\'' +
                ", gameData=" + gameData +
                '}';
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
