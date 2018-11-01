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
 * 游戏ID
 *
 * @author lutong
 */
public enum GameId {
    GAME_101("101", "欢乐一打二.常规赛"),
    GAME_102("102", "欢乐一打二.闯关赛"),
    GAME_103("103", "欢乐一打二.排名赛"),
    GAME_104("104", "欢乐一打二.拼图赛"),
    GAME_105("105", "欢乐一打二.消除赛"),
    GAME_106("106", "欢乐一打二.疯狂赛"),
    GAME_107("107", "欢乐一打二.经典赛");

    private String code;
    private String type;
    private String desc;

    GameId(String code, String desc) {
        this.code = code;
        this.type = "On-Demand";
        this.desc = desc;
    }

    /**
     * 根据code值来查找对应的Enum对象
     *
     * @param code
     * @return
     */
    public static GameId get(Integer code) {
        for (GameId value : GameId.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static GameId get(String name) {
        for (GameId value : GameId.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GameId{" +
                "name=" + name() +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
