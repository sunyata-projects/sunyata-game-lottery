package org.sunyata.game.command;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;

/**
 * Created by leo on 17/4/18.
 */
public interface CommandHandler {

    boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response);

    void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception;

    void run(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception;

    boolean isAsync();
}
