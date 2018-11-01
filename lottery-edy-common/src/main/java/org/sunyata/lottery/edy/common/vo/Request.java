/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 消息请求对象
 *
 * @author zhanglei
 */
public interface Request extends Serializable {
    /**
     * 获取消息ID
     * @return
     */
    String getMessageId();

    /**
     * 设置消息ID
     * @param messageId
     */
    void setMessageId(String messageId);

    /**
     * 获取消息时间戳，UNIX格式
     * @return
     */
    Long getTimeStamp();

    /**
     * 设置消息时间戳，UNIX格式
     * @param timeStamp
     */
    void setTimeStamp(Long timeStamp);

    /**
     * 获取Request对应状态机事件名称
     * @return statemachine event name
     */
    @JsonIgnore
    String getCommand();

    /**
     * 返回Request对应的Response ClassType
     *
     * @return Response Class
     */
    @JsonIgnore
    Class<? extends Response> getResponseType();
}
