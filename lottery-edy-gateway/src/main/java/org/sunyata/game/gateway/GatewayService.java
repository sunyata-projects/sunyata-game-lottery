package org.sunyata.game.gateway;

import org.sunyata.core.json.Json;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.exceptions.MutipleRouteModeCommandException;
import org.sunyata.game.exceptions.NotImplementServerCommandException;
import org.sunyata.game.server.OctopusRequest;
import org.sunyata.game.server.OctopusResponse;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.command.ServerCommandInfo;
import org.sunyata.game.service.ServerLocation;
import org.sunyata.game.server.message.MessageFactory;
import org.sunyata.game.server.message.OctopusPacketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by leo on 17/10/30.
 */
@Component
public class GatewayService {
    Logger logger = LoggerFactory.getLogger(GatewayService.class);
    @Autowired
    CommandService commandService;

    @Autowired
    ServerLocation serverLocation;

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private AnyClientManager anyClientManager;
    @Autowired
    ServerConfigProperties serverConfigProperties;

    public void handlerClient(OctopusRequest request, OctopusResponse response) throws Exception {
        forwardFormUser(request, response);
    }

    private void forwardFormUser(OctopusRequest request, OctopusResponse response) throws Exception {
        logger.info("用户->网关,CommandId:{}", request.getMessage().getCmd());
        int cmdId = request.getMessage().getCmd();
        List<ServerCommandInfo> commandInfos = commandService.getCommandInfo(cmdId);
        if (commandInfos.size() == 0) {
            throw new NotImplementServerCommandException("没有实现此命令" + cmdId);
        }
        Map<String, List<ServerCommandInfo>> routeModeMap = commandInfos.stream().collect(groupingBy
                (ServerCommandInfo::getRouteMode));
        if (routeModeMap.size() > 1) {
            logger.error("多个service实现此命令，但定义的路由模式不一样,{}", Json.encode(routeModeMap));
            throw new MutipleRouteModeCommandException("多个service实现此命令，但定义的路由模式不一样");
        }
        String routeMode = commandInfos.get(0).getRouteMode();

        User u = request.<User>getSession().get();
        int destServerId = request.getMessage().getDestServerId();
        if (Objects.equals(routeMode, ServerCommand.lbMode)) {//客户端发来的消息都是直接的
            ClientServerInfo targetClientId;
            Map<String, List<ServerCommandInfo>> collect = commandInfos.stream().collect(groupingBy
                    (ServerCommandInfo::getServiceName));
            for (String serviceName : collect.keySet()) {
                targetClientId = serverLocation.getRandomClientServerInfo(serviceName);
                if (targetClientId == null) {
                    logger.error("没有找到此服务的实例，有可能是因为服务还没有注册到discover,ServiceName:{}", serviceName);
                    return;
                }
                OctopusPacketMessage fromUserMessage = MessageFactory.createFromUserMessage(u.getId(),
                        serverConfigProperties
                                .getServerId(), request.getMessage());
                anyClientManager.forwardMessage(targetClientId, fromUserMessage);
                logger.info("用户->网关,消息完成CommandId:{}", request.getMessage().getCmd());
            }
        } else if (Objects.equals(routeMode, ServerCommand.directMode)) {//客户端发来的消息都是直接的
            ClientServerInfo randomClientId;
            if (destServerId < 0) {
                throw new Exception("必须提供destServerId:" + cmdId);
            }
            String serviceName = commandInfos.get(0).getServiceName();
            randomClientId = serverLocation.getDescClientServerInfo(destServerId, serviceName);

            if (randomClientId == null) {
                logger.error("没有找到此服务的实例，有可能是因为服务还没有注册到discover,ServiceName:{}", serviceName);
                return;
            }
            OctopusPacketMessage fromUserMessage = MessageFactory.createFromUserMessage(u.getId(),
                    serverConfigProperties
                            .getServerId(), request.getMessage());
            anyClientManager.forwardMessage(randomClientId, fromUserMessage);
            logger.info("用户->网关,消息完成CommandId:{}", request.getMessage().getCmd());
        } else {
            throw new Exception("不允许客户端发送事件型消息:" + cmdId);
        }
    }
}
