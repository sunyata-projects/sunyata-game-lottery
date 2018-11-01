/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * Process phase definition of each game
 *
 * @author Zhanglei
 * @date 2017/04/10
 */
public enum GamePhaseEnum {
    INIT("init", "初始阶段"),
    BET("bet", "投注阶段"),
    PLAY("play", "娱乐阶段"),
    GAMBLE("gamble", "博彩阶段"),
    SCRATCH("scratch", "刮奖阶段"),

    WAITING_SCRATCH("WAITING_SCRATCH", "等待刮奖阶段"),
    POKER_TYPE("P_POKER_TYPE", "牌型确定阶段"),
    FLOP("P_FLOP", "翻牌阶段"),

    //  RegularGame Phase
    REGULAR_GAME_BET_FIRSTBET("bet.firstBet", "常规赛.初始投注"),
    REGULAR_GAME_BET_TIMESBET("bet.timesBet", "常规赛.加倍投注"),
    REGULAR_GAME_BET_PLAYCARD("bet.playCard", "常规赛.加倍投注");

    private String code;
    private String desc;

    GamePhaseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据阶段来查询对应的Enum对象
     *
     * @param code
     * @return
     */
    public static GamePhaseEnum get(String code) {
        for (GamePhaseEnum value : GamePhaseEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
