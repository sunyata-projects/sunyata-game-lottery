package org.sunyata.game.server.message;

import org.sunyata.core.json.Json;

/**
 * Created by leo on 17/11/15.
 */
public abstract class AbstractJsonBodySerializer implements JsonBodySerializer {

    @Override
    public byte[] toBytes() {
        return Json.encode(this).getBytes();
    }

    @Override
    public JsonBodySerializer parseFrom(byte[] body) {
        return Json.decodeValue(new String(body), this.getClass());
    }
}
