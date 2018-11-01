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
@Deprecated
public enum StageId {
    // Common Stage Id
    START_GAME("StartGame", "开始游戏"),
    END_GAME("EndGame", "结束游戏"),

    // Regular Game Stage Id
    REGULAR_GAME_WAGER_FIRSTWAGER("Wager.FirstWager", "常规赛.初始投注"),
    REGULAR_GAME_WAGER_TIMESWAGER("Wager.TimesWager", "常规赛.加倍投注"),
    REGULAR_GAME_WAGER_PLAYCARDSOVER("Wager.PlayCardsOver", "常规赛.打牌结束"),
    REGULAR_GAME_WAGER_LUCKYDRAW("Wager.LuckyDraw", "常规赛.拉杆抽奖"),

    // Eliminate Game Stage Id
    // The full stageId of PointWager should be: Wager.PointWager-01,02...
    ELIMINATE_GAME_WAGER_POINTWAGER("Wager.PointWager-", "消除赛.点数投注"),
    ELIMINATE_GAME_WAGER_CASHDRAW("Wager.CashDraw", "消除赛.奖金关开奖"),

    // Classic Game Stage Id
    // The full stageId of Gamble should be: Gamble.Gamble-01,02...
    CLASSIC_GAME_WAGER_CASHWAGER("Wager.CashWager", "经典赛.现金投注"),
    CLASSIC_GAME_WAGER_PLAYCARDSOVER("Wager.PlayCardsOver", "经典赛.打牌结束"),
    CLASSIC_GAME_GAMBLE("Gamble.Gamble-", "经典赛.翻牌投注");

    private String code;
    private String desc;

    StageId(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据阶段来查询对应的Enum对象
     *
     * @param code
     * @return
     */
    public static StageId get(String code) {
        for (StageId value : StageId.values()) {
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

    @Override
    public String toString() {
        return "GameStageEnum{" +
            "code='" + code + '\'' +
            ", desc='" + desc + '\'' +
            ", " + super.toString() + '}';
    }
}
