package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;

/**
 * Created by leo on 17/11/15.
 */
public class DefaultOctopusJsonBodyMessage<T extends JsonBodySerializer> extends AbstractOctopusRawMessage {
    private T propertyInfo;

    public T getPropertyInfo() {
        return propertyInfo;
    }

    public DefaultOctopusJsonBodyMessage setPropertyInfo(T propertyInfo) {
        this.propertyInfo = propertyInfo;
        byte[] bytes = propertyInfo.toBytes();
        //this.setLength(bytes.length);
        this.setBody(bytes);
        return this;
    }


    @Override
    public void encodeBody(ByteBuf buf) {
        byte[] bytes = propertyInfo.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
}
