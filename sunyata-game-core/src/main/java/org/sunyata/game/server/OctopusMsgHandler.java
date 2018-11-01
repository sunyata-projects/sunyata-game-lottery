package org.sunyata.game.server;

public interface OctopusMsgHandler<T> extends NetHandler<T> {
    void onMessage(OctopusRequest msg, OctopusResponse response) throws Exception;
}
