/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 游戏周期（GameCycle）的状态
 *
 * @author Zhanglei
 */
public enum GameCycleStatus {
    INIT(0, "初始化"),
    RUNNING(10, "运行中"),
    USER_FINISHED(20, "用户自动结束"),
    SYSTEM_FINISHED(30, "系统自动结束"),
    ABORT(40, "异常结束"),
    HANGUP(50, "已挂起"),
    UNKNOWN(99, "未知状态");

    private Integer code;
    private String desc;

    GameCycleStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GameCycleStatus get(Integer code) {
        for (GameCycleStatus value : GameCycleStatus.values()) {
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
        return "GameCycleStatus{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
