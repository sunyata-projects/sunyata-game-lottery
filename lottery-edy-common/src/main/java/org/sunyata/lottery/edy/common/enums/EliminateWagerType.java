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
public enum EliminateWagerType {
    Wager10501(10501, "10"),
    Wager10502(10502, "20"),
    Wager10503(10503, "30"),
    Wager10504(10504, "40"),
    Wager10505(10505, "50"),
    Wager10506(10506, "60"),
    Wager10507(10507, "70"),
    Wager10508(10508, "80"),
    Wager10509(10509, "90"),
    Wager10510(10510, "100"),
    Wager10511(10511, "150"),
    Wager10512(10512, "200"),
    Wager10513(10513, "300"),
    Wager10514(10514, "400"),
    Wager10515(10515, "500"),
    Wager10516(10516, "600"),
    Wager10517(10517, "700"),
    Wager10518(10518, "800"),
    Wager10519(10519, "900"),
    Wager10520(10520, "1000");


    private Integer code;
    private String desc;

    EliminateWagerType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EliminateWagerType get(Integer code) {
        for (EliminateWagerType value : EliminateWagerType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static EliminateWagerType getByDesc(String desc) {
        for (EliminateWagerType value : EliminateWagerType.values()) {
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
