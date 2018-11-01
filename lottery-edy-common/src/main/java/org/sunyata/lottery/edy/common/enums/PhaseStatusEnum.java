/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.enums;

/**
 * 游戏中各个阶段的处理结果/状态
 *
 * @author Zhanglei
 * @date 2017/05/10
 */
public enum PhaseStatusEnum {
    RUNNING(10, "运行中"),
    SUCCESS(20, "执行成功"),
    FAILED(30, "执行失败");

    private Integer code;
    private String desc;

    PhaseStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code值来查找对应的Enum对象
     *
     * @param code
     * @return
     */
    public static PhaseStatusEnum get(Integer code) {
        if (code == null) {
            return null;
        }

        for (PhaseStatusEnum value : PhaseStatusEnum.values()) {
            if (code.equals(value.getCode())) {
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
        return "PhaseStatusEnum{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
