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

import org.sunyata.game.server.message.OctopusRawMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OctopusMsgEncoder extends MessageToByteEncoder<OctopusRawMessage> {
    Logger logger = LoggerFactory.getLogger(OctopusMsgEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, OctopusRawMessage msg, ByteBuf out) throws Exception {
        logger.info("消息发送编码开始->cmd:{}", msg.getCmd());
//        OctopusPacketRawMessage msg = new OctopusPacketRawMessage();
//        byte[] bytes = "lcl".getBytes();
//        msg.setCmd(15001);
//        msg.setSerial(2323232);
//        msg.setVersion(1.0f);
//        msg.setLength(bytes.length);
//        msg.setBody(bytes);
//        ByteBuf buffer = Unpooled.buffer();
//        byte[] bytes = msg.getRawMessage().toBytes();
//        out
//                .writeInt(msg.getSourceServerId())
//                .writeInt(bytes.length)
//                .writeBytes(bytes);
        msg.encode(out);
        logger.info("消息发送编码完成->sourceServerId:{}", msg.getCmd());

    }
}
