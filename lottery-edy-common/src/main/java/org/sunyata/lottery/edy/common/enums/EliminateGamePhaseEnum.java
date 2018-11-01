/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 消除赛阶段
 *
 * @author lutong
 */
public enum EliminateGamePhaseEnum {
    /**
     * 消除赛循环消除阶段投注
     */
    ELIMINATE_LOOP("P11#%d", "消除赛循环消除阶段投注"),
    /**
     * 消除赛循环消除阶段互动通知
     */
    ELIMINATE_NOTIFY("P17#%d", "消除赛循环消除阶段互动通知"),
    /**
     * 消除赛牌型定义，用户在消除赛环节的牌型是否包含有双王
     */
    DEFINE_POKER_TYPE("P16#%d", "消除赛牌型定义"),
    /**
     * 消除赛奖金关抽奖阶段，用户点击确定按钮进入奖金关时开奖
     */
    FINAL_PRIZE_DRAW("P22", "消除赛奖金关抽奖阶段"),
    /**
     * 消除赛奖金关刮奖阶段，用户通过摸牌来展示最终中奖结果
     */
    FINAL_PRIZE_SCRATCH("P31", "消除赛奖金关刮奖阶段");

    private String code;

    private String desc;

    EliminateGamePhaseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
