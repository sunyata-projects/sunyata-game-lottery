/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import org.sunyata.lottery.edy.common.enums.ReturnCode;

import java.io.Serializable;

/**
 * 消息响应对象
 *
 * @author lutong
 */
public interface Response extends Serializable {
    /**
     * 获取消息ID
     *
     * @return
     */
    String getMessageId();

    /**
     * 设置消息ID
     *
     * @param messageId
     */
    void setMessageId(String messageId);

    /**
     * 获取消息时间戳，UNIX格式
     *
     * @return
     */
    Long getTimeStamp();

    /**
     * 设置消息时间戳，UNIX格式
     *
     * @param timeStamp
     */
    void setTimeStamp(Long timeStamp);

    /**
     * 获取操作应答码
     *
     * @return
     */
    String getReturnCode();

    /**
     * 设置操作应答码
     *
     * @param returnCode
     */
    void setReturnCode(String returnCode);
    void setReturnCode(ReturnCode returnCode);

    /**
     * 获取操作描述
     *
     * @return
     */
    String getReturnMsg();

    /**
     * 设置操作描述
     *
     * @param returnMsg
     */
    void setReturnMsg(String returnMsg);

    /**
     * 是否处理成功
     * @return
     */
    Boolean isSuccess();

}
