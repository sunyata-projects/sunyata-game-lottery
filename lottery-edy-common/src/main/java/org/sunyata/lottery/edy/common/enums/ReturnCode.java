/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 定义业务操作返回码
 *
 * @author lutong
 */
public enum ReturnCode {
    SUCCESS("000000", "操作成功"),
    FAIL("000001", "操作失败"),

    // 消息级别的错误码
    MESSAGE_INVALID_FORMAT("100001", "消息格式错误"),
    MESSAGE_INVALID_PARAM("100002", "消息参数不完整"),
    MESSAGE_INVALID_COMMAND("100003", "消息命令不符合要求"),
    MESSAGE_INVALID_CONTENT("100003", "消息参数值不符合要求"),
    MESSAGE_DISORDER("100004", "消息时序异常"),
    MESSAGE_UNKNOWN("100005", "消息无法识别"),

    // 用户级别的错误码
    USER_NOT_EXIST("200001", "用户不存在"),
    USER_INSUFFICIENT_POINT("200002", "用户游戏点数不足"),
    USER_IN_GAMECYCLE("200003", "用户已经在游戏中"),

    // 游戏级别的错误码
    GAME_NOT_AVAILABLE("300001", "游戏不可用"),
    GAME_NOT_EXIST("300002", "游戏不存在"),

    GAME_CYCLE_NOT_EXIST("300100", "游戏周期不存在"),
    GAME_CYCLE_UPDATE_FAILED("300101", "更新GameCycle失败"),

    GAME_TICKET_CREATE_FAILED("300200", "产生Ticket失败"),
    GAME_TICKET_UPDATE_FAILED("300201", "更新Ticket失败"),
    GAME_TICKET_NOT_EXIST("300202", "Ticket不存在"),
    GAME_TICKET_INVALID("300203", "Ticket不完整"),

    GAME_STAKE_CREATE_FAILED("300300", "产生Stake失败"),
    GAME_STAKE_UPDATE_FAILED("300303", "更新Stake失败"),
    GAME_STAKE_NOT_EXIST("300301", "Stake不存在"),
    GAME_STAKE_INVALID("300302", "Stake不完整"),
    GAME_STAKE_REACH_MAXIMUM("300303", "达到次数上限"),

    GAME_JACKPOT_INJECT_FAILED("300400", "奖池注入失败"),
    GAME_JACKPOT_WITHDRAW_FAILED("300401", "奖池提取失败"),

    GAME_PRIZE_DRAW_FAILED("300500", "开奖失败"),
    GAME_PRIZE_CALCULATE_FAILED("300501", "计奖失败"),
    GAME_PRIZE_LEVEL_NOT_EXIST("300502", "奖等档位不存在"),
    GAME_PRIZE_LEVEL_INVALID("3000503", "奖等档位无效"),

    GAME_SYMBOL_CREATE_FAILED("3000504", "产生游戏符号失败"),
    GAME_PRIZE_NOTIFY_FAILED("3000505", "通知运营商中奖通知失败"),
    GAME_LOGIC_PROCESS_FAILED("300900", "游戏逻辑处理失败"),

    // 系统级别的错误码
    SYS_NOT_AVAILABLE("400001", "系统服务不可用"),
    SYS_OVERLOAD("400002", "系统过负荷");

    private String code;
    private String desc;

    ReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ReturnCode get(String code) {
        for (ReturnCode value : ReturnCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ReturnCode{" +
            "code='" + code + '\'' +
            ", desc='" + desc + '\'' +
            '}';
    }
}
