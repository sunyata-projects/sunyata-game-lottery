/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 彩票状态编码
 * https://lark.alipay.com/aone581288/design/specification
 *
 * @author Arielly
 */
public enum TicketStatus {
    INIT(0, "初始化"),
    CONFIRM(10, "已出票"),
    PRIZE_DREW(20, "已开奖"),
    PRIZE_CALCULATED(30, "已计奖"),
    SCRAPED(40, "已刮奖"),
    CASHED(50, "已兑奖"),
    ABANDONED(60, "已弃奖"),
    INVALID(99, "已废票");

    private Integer code;
    private String desc;

    TicketStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 依据枚举值获得枚举对象
     *
     * @param code
     * @return
     */
    public static TicketStatus get(Integer code) {
        for (TicketStatus value : TicketStatus.values()) {
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
        return "TicketStatusEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            "}";
    }
}
