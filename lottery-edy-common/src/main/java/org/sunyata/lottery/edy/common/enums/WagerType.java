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
public enum WagerType {
    TRIAL(0, "试玩投注"),
    FORMAL(1, "正式投注");

    private Integer code;
    private String desc;

    WagerType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WagerType get(Integer code) {
        for (WagerType value : WagerType.values()) {
            if (value.getCode().equals(code)) {
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
