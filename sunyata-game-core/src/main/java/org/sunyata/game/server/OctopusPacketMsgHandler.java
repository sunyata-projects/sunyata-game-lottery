package org.sunyata.game.server;

public interface OctopusPacketMsgHandler<T> extends NetHandler<T> {
    void onMessage(OctopusPacketRequest msg, OctopusPacketResponse response) throws Exception;
}
