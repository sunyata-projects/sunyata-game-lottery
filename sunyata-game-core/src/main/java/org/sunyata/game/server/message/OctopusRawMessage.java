package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;

/**
 * Created by leo on 17/4/17.
 */
public interface OctopusRawMessage {
    int getCmd();

    OctopusRawMessage setCmd(int cmd);

    int getSerial();

    OctopusRawMessage setSerial(int serial);

    float getVersion();

    OctopusRawMessage setVersion(float version);

    int getDestServerId();

    OctopusRawMessage setDestServerId(int destServerId);

//    int getLength();

//    OctopusRawMessage setLength(int length);

    OctopusRawMessage setBody(byte[] bytes);

    byte[] getBody();

    OctopusRawMessage fromBytes(byte[] byteBuf);

    OctopusRawMessage fromByteBuf(ByteBuf buf);

    byte[] toBytes();

    void encode(ByteBuf out);
}
