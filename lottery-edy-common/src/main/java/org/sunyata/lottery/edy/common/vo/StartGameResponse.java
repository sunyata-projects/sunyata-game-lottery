/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */
package org.sunyata.lottery.edy.common.vo;

/**
 * 开始游戏的Response
 *
 * @author ZhangLei
 */
public class StartGameResponse extends GamePlayResponse {

    private static final long serialVersionUID = -8687702783550822501L;

    /**
     * 是否有未结束的GameCycle
     */
    private boolean hasUnfinishedGameCycle;

    public boolean getHasUnfinishedGameCycle() {
        return hasUnfinishedGameCycle;
    }

    public void setHasUnfinishedGameCycle(boolean hasUnfinishedGameCycle) {
        this.hasUnfinishedGameCycle = hasUnfinishedGameCycle;
    }

    public void setCreateTimestamp(long timestamp) {
        this.getGameData().set(GameDataKey.CREATE_TIMESTAMP, timestamp);
    }

    public void setUpdateTimestamp(long timestamp) {
        this.getGameData().set(GameDataKey.UPDATE_TIMESTAMP, timestamp);
    }

    public void setLastStageId(String stageId) {
        this.getGameData().set(GameDataKey.LAST_STAGE_ID, stageId);
    }

    public void setLastReturnCode(String code) {
        this.getGameData().set(GameDataKey.LAST_RETURN_CODE, code);
    }

    public void setLastReturnMsg(String message) {
        this.getGameData().set(GameDataKey.LAST_RETURN_MESSAGE, message);
    }

    public void setLastGameData(GameData gameData) {
        this.getGameData().set(GameDataKey.LAST_GAME_DATA, gameData);
    }

    @Override
    public String toString() {
        return "StartGameResponse{" +
            "hasUnfinishedGameCycle=" + hasUnfinishedGameCycle +
            ", " + super.toString() + '}';
    }
}
