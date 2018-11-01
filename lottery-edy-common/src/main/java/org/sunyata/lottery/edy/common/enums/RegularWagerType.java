/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 游戏投注类型
 *
 * @author Zhanglei
 */
public enum RegularWagerType {
    Wager10101(10101, "1"),
    Wager10102(10102, "2"),
    Wager10103(10103, "3"),
    Wager10104(10104, "5"),
    Wager10105(10105,"10");

    private Integer code;
    private String desc;

    RegularWagerType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RegularWagerType get(Integer code) {
        for (RegularWagerType value : RegularWagerType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static RegularWagerType getByDesc(String desc) {
        for (RegularWagerType value : RegularWagerType.values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
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
        return "BetTypeEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
