package org.sunyata.game.game;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.service.UserLocationInfo;

public interface Bet {
    byte[] doBet(String gameCycleId, OctopusPacketRequest request, UserLocationInfo userLocationInfo) throws
            Exception;
}
