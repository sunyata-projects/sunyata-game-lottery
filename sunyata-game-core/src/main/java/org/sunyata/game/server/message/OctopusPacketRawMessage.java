package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusPacketRawMessage implements OctopusPacketMessage {
    Logger logger = LoggerFactory.getLogger(OctopusPacketRawMessage.class);
    private int messageType;
    private int sourceServerId = -1;
    private int destServerId = -1;
    private int dataId;
    private byte[] bytes;

    @Override
    public int getDestServerId() {
        return destServerId;
    }

    @Override
    public OctopusPacketRawMessage setDestServerId(int destServerId) {
        this.destServerId = destServerId;
        return this;
    }

    @Override
    public int getDataId() {
        return dataId;
    }

    public OctopusPacketMessage setDataId(int dataId) {
        this.dataId = dataId;
        return this;
    }

    @Override
    public int getMessageType() {
        return messageType;
    }

    public OctopusPacketRawMessage setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }


    //public int getLength() {
//        return bytes.length;
//    }

    public OctopusPacketRawMessage() {
    }


    public OctopusPacketRawMessage(int sourceServerId, OctopusRawMessage rawMessage) {
        this.sourceServerId = sourceServerId;
        setRawMessage(rawMessage);
    }

    public OctopusPacketRawMessage setBody(byte[] bytes) {
        this.bytes = bytes;
//        this.length = bytes.length;
        return this;
    }

    public byte[] getBody() {
        return bytes;
    }

    @Override
    public OctopusPacketMessage setSourceServerId(int destServerId) {
        sourceServerId = destServerId;
        return this;
    }

    @Override
    public int getSourceServerId() {
        return sourceServerId;
    }

    @Override
    public OctopusRawMessage getRawMessage() {
        OctopusRawMessage message = MessageFactory.createRawMessage(this.getMessageType());
        message.fromBytes(bytes);
        return message;
    }

    @Override
    public OctopusPacketMessage setRawMessage(OctopusRawMessage msg) {
        ByteBuf out = Unpooled.buffer();
        out
                .writeInt(msg.getCmd())
                .writeInt(msg.getSerial());

        if (msg instanceof OctopusToUserRawMessage) {
            OctopusToUserRawMessage messag = (OctopusToUserRawMessage) msg;
            out.writeInt(messag.getCode());
        } else {
            //if (msg instanceof OctopusInRawMessage) {
            out.writeFloat(msg.getVersion())
                    .writeInt(msg.getDestServerId());
        }
        byte[] body = msg.getBody();
        if (body != null) {
            out.writeInt(body.length)
                    .writeBytes(body);
        } else {
            out.writeInt(0);
        }
        byte[] bytes = new byte[out.readableBytes()];
        out.readBytes(bytes);
        setBody(bytes);
        return this;
    }

    @Override
    public void encode(ByteBuf buf) {
        logger.info("ByteBuf.startIndex:{},WriterIndex:{}", buf.readerIndex(), buf.writerIndex());
        OctopusRawMessage rawMessage = getRawMessage();
        byte[] bytes = rawMessage.toBytes();
        int length = bytes == null ? 0 : bytes.length;

        if (length > 0) {
            buf
                    .writeInt(this.getMessageType())
                    .writeInt(this.getSourceServerId())
                    .writeInt(this.getDestServerId())
                    .writeInt(this.getDataId())
                    .writeInt(length)
                    .writeBytes(bytes);
        } else {
            buf
                    .writeInt(this.getMessageType())
                    .writeInt(this.getSourceServerId())
                    .writeInt(this.getDestServerId())
                    .writeInt(this.getDataId())
                    .writeInt(length);
//                    .writeBytes(bytes);
        }
    }

    @Override
    public OctopusPacketMessage fromByteBuf(ByteBuf buf) {
        int messageType = buf.readInt();//  1
        this.setMessageType(messageType);//4
        logger.info("消息解码messageType:{}", messageType);
        int sourceServerId = buf.readInt();//   2
        this.setSourceServerId(sourceServerId);//4
        logger.info("消息解码sourceServerId:{}", sourceServerId);
        int destServerId = buf.readInt();// 3
        this.setDestServerId(destServerId);
        logger.info("消息解码destServerId:{}", destServerId);
        int dataId = buf.readInt();//   4
        this.setDataId(dataId);//4
        logger.info("消息解码dataId:{}", dataId);
        int length = buf.readInt();//   5
        logger.info("消息解码length:{}", length);
        this.setBody(null);
        if (length != 0) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            this.setBody(bytes);
        }

        return this;
    }


}
