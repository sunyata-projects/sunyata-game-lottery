package org.sunyata.game.command;

import java.util.List;

/**
 * Created by leo on 17/11/2.
 */

public interface CommandService {
    void start();
    void stop();
    ServerCommandInfo getSingleCommandInfo(Integer commandId) throws Exception;

    List<ServerCommandInfo> getCommandInfo(Integer commandId);

//    ClientServerInfo getDescClientServerInfo(Integer serverId, String serviceName);
//
//    ClientServerInfo getRandomClientServerInfo(String serviceName);
//
//    List<ClientServerInfo> getAllClientServerInfo(String serviceName);
//
//    ClientServerInfo getRandomInnerGatewayServerInfo();

    void registerCommand(String commandId,int serverId,ServerCommandInfo commandInfo);
    void unRegisterCommand(String commandId,int serverId);

}
