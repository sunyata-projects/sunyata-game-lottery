/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 奖级类型枚举
 *
 * @author lutong
 * @date 2017/6/5
 */
public enum PrizeLevelType {
    NONE(10, "不中奖"),
    FIXED(20, "固定奖"),
    FLOATING(30, "浮动奖");

    private Integer code;
    private String desc;

    PrizeLevelType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
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

    public static boolean isFloating(Integer code) {
        if (PrizeLevelType.FLOATING.getCode().equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFixed(Integer code) {
        if (PrizeLevelType.FIXED.getCode().equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValid(Integer code) {
        PrizeLevelType prizeLevelTypeEnum = PrizeLevelType.get(code);
        if (prizeLevelTypeEnum == null) {
            return false;
        }
        return true;
    }

    public static PrizeLevelType get(Integer code) {
        for (PrizeLevelType prizeLevelTypeEnum : PrizeLevelType.values()) {
            if (prizeLevelTypeEnum.getCode().equals(code)) { return prizeLevelTypeEnum; }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PrizeLevelTypeEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
