/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 游戏状态类型
 *
 * @author lutong
 */
public enum GameStatus {
    ENABLE(10, "已启用"),
    DISABLE(20, "已停用"),
    DELIST(30, "已退市");

    private Integer code;
    private String desc;

    GameStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @param code
     * @return GameStatusEnum
     * @throws
     * @Title: get
     * @Description: 根据code值来查找对应的Enum对象
     */
    public static GameStatus get(Integer code) {
        if (code != null) {
            for (GameStatus value : GameStatus.values()) {
                if (code.equals(value.getCode())) {
                    return value;
                }
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
}
