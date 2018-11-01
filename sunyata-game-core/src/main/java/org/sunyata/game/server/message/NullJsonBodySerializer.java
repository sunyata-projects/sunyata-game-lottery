package org.sunyata.game.server.message;

/**
 * Created by leo on 17/11/22.
 */
public class NullJsonBodySerializer implements JsonBodySerializer {
    @Override
    public byte[] toBytes() {
        return null;
    }

    @Override
    public JsonBodySerializer parseFrom(byte[] body) {
        return new NullJsonBodySerializer();
    }
}
