/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusPacketMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class OctopusPacketMsgEncoder extends MessageToByteEncoder<OctopusPacketMessage> {
    Logger logger = LoggerFactory.getLogger(OctopusPacketMsgEncoder.class);

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext ctx, OctopusPacketMessage msg, ByteBuf out) throws Exception {
        logger.info("消息发送编码开始->sourceServerId:{},destServerId:{},dataId:{}, commandId:{}", msg.getSourceServerId(), msg
                .getDestServerId(), msg.getDataId(), msg.getRawMessage().getCmd());
        msg.encode(out);
        logger.info("消息发送编码完成->sourceServerId:{},destServerId:{},dataId:{}, commandId:{},msgCount:{}", msg
                .getSourceServerId(), msg
                .getDestServerId(), msg.getDataId(), msg.getRawMessage().getCmd(), atomicInteger.incrementAndGet());

    }
}
