package org.sunyata.game;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;

/**
 * Created by leo on 17/11/8.
 */
public interface MessageProcessor {
    void handler(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception;
}
