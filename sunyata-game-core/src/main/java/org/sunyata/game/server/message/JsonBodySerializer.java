package org.sunyata.game.server.message;

import java.io.Serializable;

/**
 * Created by leo on 17/11/15.
 */
public interface JsonBodySerializer extends Serializable {
    byte[] toBytes();

    JsonBodySerializer parseFrom(byte[] body);
}
