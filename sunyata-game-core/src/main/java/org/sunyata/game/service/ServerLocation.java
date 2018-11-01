package org.sunyata.game.service;

import java.util.List;

public interface ServerLocation {
    ClientServerInfo getDescClientServerInfo(Integer serverId, String serviceName);

    ClientServerInfo getRandomClientServerInfo(String serviceName);

    List<ClientServerInfo> getAllClientServerInfo(String serviceName);

    ClientServerInfo getRandomInnerGatewayServerInfo();

}
