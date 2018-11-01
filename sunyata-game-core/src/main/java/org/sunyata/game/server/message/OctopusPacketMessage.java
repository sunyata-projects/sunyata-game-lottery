package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;

/**
 * Created by leo on 17/4/17.
 */
public interface OctopusPacketMessage {
    public static final int toUserMessageType = -1;
    public static final int fromUserMessageType = 1;
    public static final int toSysMessageType = 0;

    OctopusPacketRawMessage setMessageType(int messageType);
    OctopusPacketMessage setSourceServerId(int sourceServerId);
    int getSourceServerId();

    int getDestServerId();
    OctopusPacketMessage setDestServerId(int destServerId);

    int getMessageType();

    int getDataId();

    OctopusRawMessage getRawMessage();

    OctopusPacketMessage setRawMessage(OctopusRawMessage message);

    OctopusPacketMessage setBody(byte[] bytes);

    void encode(ByteBuf buf);

    OctopusPacketMessage fromByteBuf(ByteBuf buf);

    OctopusPacketMessage setDataId(int dataId);
}
