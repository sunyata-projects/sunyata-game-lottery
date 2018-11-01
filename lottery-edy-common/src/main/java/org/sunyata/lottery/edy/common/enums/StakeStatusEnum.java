/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * Stake状态
 *
 * @author ZhangLei
 */
public enum StakeStatusEnum {
    INIT(0, "初始化"),
    TICKET_CREATE(10, "已出票"),
    JACKPOT_OK(20, "已贡献奖池"),
    PRIZE_DREW(20, "已开奖"),
    PRIZE_CALCULATED(30, "已计奖"),
    SYMBOL_OK(40, "已获取符号"),
    TICKET_COMPLETE(50, "已出票"),
    SCRAPE_OK(60, "已刮奖"),
    PLAY_CARD_OVER(70, "已打牌"),
    SUCCESS(90, "结束并处理成功"),
    FAILED(99, "结束但处理失败");

    private Integer code;
    private String desc;

    StakeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据Code查找StakeEnum对象
     *
     * @param code
     * @return
     */
    public static StakeStatusEnum get(Integer code) {
        for (StakeStatusEnum value : StakeStatusEnum.values()) {
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
        return "StakeStatusEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
