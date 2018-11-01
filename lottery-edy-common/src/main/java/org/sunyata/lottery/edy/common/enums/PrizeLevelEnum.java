/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 奖级枚举，主要用于可能出现在程序中的一些判断逻辑
 *
 * @author lutong
 * @date 2017/6/2
 */
public enum PrizeLevelEnum {

    REGULAR_LUCY_PRIZE(10001201, "常规赛幸运奖"),
    ELIMINATE_CUMULATIVE(10005100, "消除赛累积奖"),
    ELIMINATE_JOKER(10005200, "消除赛王炸"),
    CRAZY_CUMULATIVE(10006100, "疯狂赛累积奖"),
    CRAZY_FLOP_WIN(10006200, "疯狂赛翻牌赢"),
    CLASSIC_CUMULATIVE(10007100, "经典赛累积奖"),
    CLASSIC_FLOP_WIN(10007200, "经典赛翻牌赢"),
    NO_PRIZE(0, "未中奖");

    private Integer code;
    private String desc;

    PrizeLevelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public PrizeLevelEnum get(Integer code) {
        if (code == null) { return null; }

        for (PrizeLevelEnum prizeLevel : PrizeLevelEnum.values()) {
            if (prizeLevel.getCode().equals(code)) { return prizeLevel; }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
        return "PrizeLevelEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
