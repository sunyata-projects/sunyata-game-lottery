//package com.isnowfox.game.proxy.message;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
///**
// * Created by leo on 17/4/17.
// */
//public class OctopusToUserPacketRawMessage implements OctopusPacketMessage {
//    private int messageType;
//    private int sourceServerId;
//    private int dataId;
//
//
//    //    private int length;//3 4
//    private byte[] bytes;
//
//    @Override
//    public int getDataId() {
//        return dataId;
//    }
//
//    public OctopusToUserPacketRawMessage setDataId(int dataId) {
//        this.dataId = dataId;
//        return this;
//    }
//
//    @Override
//    public int getMessageType() {
//        return messageType;
//    }
//
//    public OctopusToUserPacketRawMessage setMessageType(int messageType) {
//        this.messageType = messageType;
//        return this;
//    }
//
//
//    public int getLength() {
//        return bytes.length;
//    }
//
//    public OctopusToUserPacketRawMessage() {
//    }
//
//
//    public OctopusToUserPacketRawMessage(int sourceServerId, OctopusRawMessage rawMessage) {
//        this.sourceServerId = sourceServerId;
//        setRawMessage(rawMessage);
//    }
//
//    public OctopusToUserPacketRawMessage setBody(byte[] bytes) {
//        this.bytes = bytes;
////        this.length = bytes.length;
//        return this;
//    }
//
//    public byte[] getBody() {
//        return bytes;
//    }
//
//    @Override
//    public OctopusPacketMessage setSourceServerId(int destServerId) {
//        sourceServerId = destServerId;
//        return this;
//    }
//
//    @Override
//    public int getSourceServerId() {
//        return sourceServerId;
//    }
//
//    @Override
//    public OctopusRawMessage getRawMessage() {
//        OctopusToUserRawMessage message = new OctopusToUserRawMessage();
//        message.fromBytes(bytes);
//        return message;
//    }
//
//    @Override
//    public OctopusPacketMessage setRawMessage(OctopusRawMessage message) {
//        OctopusToUserRawMessage msg = (OctopusToUserRawMessage) message;
//        ByteBuf out = Unpooled.buffer();
//        out
//                .writeInt(msg.getCmd())
//                .writeInt(msg.getSerial())
//                //.writeFloat(msg.getVersion())
//                //.writeInt(msg.getDestServerId())
//                .writeInt(msg.getCode())
//
//                .writeInt(msg.getBody().length)
//                .writeBytes(msg.getBody());
//        byte[] bytes = new byte[out.readableBytes()];
//        out.readBytes(bytes);
//        setBody(bytes);
//        return this;
//    }
//
//    @Override
//    public void encode(ByteBuf buf) {
//        byte[] bytes = getRawMessage().toBytes();
//        buf
//                .writeInt(this.getMessageType())
//                .writeInt(this.getSourceServerId())
//                .writeInt(this.getDataId())
//                .writeInt(bytes.length)
//                .writeBytes(bytes);
//    }
//
//    @Override
//    public OctopusPacketMessage fromByteBuf(ByteBuf buf) {
//        this.setMessageType(buf.readInt());//4
//        this.setSourceServerId(buf.readInt());//4
//        this.setDataId(buf.readInt());//4
//        int length = buf.readInt();
//        byte[] bytes = new byte[length];
//        if (length != 0) {
//            buf.readBytes(bytes, 0, length);
//        }
//        this.setBody(bytes);
//        return this;
//    }
//
//
//}
