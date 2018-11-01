/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

/**
 *
 */
package org.sunyata.lottery.edy.common.enums;

/**
 * 中奖结果类型
 *
 * @author Zhanglei
 * @date 2017/05/10
 */
public enum PrizeType {
    NO_PRIZE(10, "未中奖"),
    SMALL_PRIZE(20, "小奖"),
    LARGE_PRIZE(30, "大奖"),
    UNKNOWN_PRIZE(99, "未开奖");

    private Integer code;
    private String desc;

    PrizeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code值来查找对应的Enum对象
     *
     * @param code 中奖类型编码
     * @return
     */
    public static PrizeType get(Integer code) {
        for (PrizeType value : PrizeType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

//    public static PrizeType decide(Integer prizeAmount) {
//        if (prizeAmount == null) {
//            return UNKNOWN_PRIZE;
//        }
//        if (prizeAmount.compareTo(AppConfig.getLargePrizeBoundary()) > 0) {
//            return PrizeType.LARGE_PRIZE;
//        }
//        if (prizeAmount.compareTo(0) > 0) {
//            return PrizeType.SMALL_PRIZE;
//        }
//        if (prizeAmount.compareTo(0) == 0) {
//            return PrizeType.NO_PRIZE;
//        }
//        return UNKNOWN_PRIZE;
//    }

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
        return "PrizeTypeEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
