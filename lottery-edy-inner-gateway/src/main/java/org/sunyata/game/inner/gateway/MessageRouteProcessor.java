package org.sunyata.game.inner.gateway;

import org.sunyata.core.json.Json;
import org.sunyata.game.*;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.exceptions.MutipleRouteModeCommandException;
import org.sunyata.game.exceptions.NotImplementServerCommandException;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.command.ServerCommandInfo;
import org.sunyata.game.command.CommandHandler;
import org.sunyata.game.service.ServerLocation;
import org.sunyata.game.server.message.OctopusPacketMessage;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.ServiceNames;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by leo on 17/11/8.
 */
@Component
@Configuration
public class MessageRouteProcessor implements MessageProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    final String gatewayServiceName = ServiceNames.game_gateway_service;//
    Logger logger = LoggerFactory.getLogger(MessageRouteProcessor.class);
    Map<String, Object> beansWithAnnotation = null;
    @Autowired
    private AnyClientManager anyClientManager;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    CommandService commandService;

    @Autowired
    ServerLocation serverLocation;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    @Value("${spring.application.name}")
    String localServiceName;

    CommandHandler getLocalCommandHandler(String cmd) {
        if (beansWithAnnotation == null) {
            beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
        }
        Object o = beansWithAnnotation.values().stream().filter(p -> {
            ServerCommand annotation = p.getClass().getAnnotation(ServerCommand.class);
            return annotation.commandId().equals(cmd);
        })
                .findFirst().orElse(null);
        if (o != null) {//如果本机可以执行,本地执行
            return (CommandHandler) o;
        }
        return null;
    }


    @Override
    public void handler(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        OctopusPacketMessage packetMessage = request.getMessage();
        logger.info("收到需要路由消息:{},destServerId:{},commandId:{}", packetMessage.getMessageType(), packetMessage
                .getDestServerId(), packetMessage.getRawMessage().getCmd());
        OctopusRawMessage rawMessage = packetMessage.getRawMessage();

        int messageType = packetMessage.getMessageType();
        //如果是直接发给用户的消息，则直接发送到网关
        if (Objects.equals(messageType, OctopusPacketMessage.toUserMessageType)) {
//            int userId = packetMessage.getDataId();
            int gatewayServerId = packetMessage.getDestServerId();
            ClientServerInfo descClientServerInfo = serverLocation.getDescClientServerInfo(gatewayServerId,
                    gatewayServiceName);
            packetMessage.setSourceServerId(serverConfigProperties.getServerId());
            anyClientManager.forwardMessage(descClientServerInfo, packetMessage);
            return;
        }

        List<ServerCommandInfo> commandInfos = commandService.getCommandInfo(rawMessage.getCmd());
        if (commandInfos.size() == 0) {
            throw new NotImplementServerCommandException("没有实现此命令" + rawMessage.getCmd());
        }

        Map<String, List<ServerCommandInfo>> routeModeMap = commandInfos.stream().collect(groupingBy
                (ServerCommandInfo::getRouteMode));
        if (routeModeMap.size() > 1) {
            logger.error("多个service实现此命令，但定义的路由模式不一样,{}", Json.encode(routeModeMap));
            throw new MutipleRouteModeCommandException("多个service实现此命令，但定义的路由模式不一样");
        }

        ServerCommandInfo eventInfo = commandInfos.get(0);
        String routeMode = eventInfo.getRouteMode();
        List<ClientServerInfo> targetServerInfo = new ArrayList<>();
        if (Objects.equals(routeMode, ServerCommand.lbMode)) {
            Map<String, List<ServerCommandInfo>> collect = commandInfos.stream().collect(groupingBy
                    (ServerCommandInfo::getServiceName));
            for (String serviceName : collect.keySet()) {
                ClientServerInfo randomClientServerInfo = serverLocation.getRandomClientServerInfo(serviceName);
                if (randomClientServerInfo == null) {
                    logger.error("没有找到此服务的实例，有可能是因为服务还没有注册到discover,ServiceName:{}", serviceName);
//                    return;
                }
                randomClientServerInfo.setServerId(serverConfigProperties.getServerId());//设置本机执行此随机命令
                targetServerInfo.add(randomClientServerInfo);
            }
        } else if (routeMode.equalsIgnoreCase(ServerCommand.directMode)) {
            int destServerId = rawMessage.getDestServerId();
            destServerId = destServerId < 0 ? packetMessage.getDestServerId() : destServerId;
            if (destServerId <= 0) {
                throw new Exception("必须提供destServerId:" + rawMessage.getCmd());
            }
            ClientServerInfo descClientServerInfo = serverLocation.getDescClientServerInfo(destServerId,
                    eventInfo.getServiceName());
            if (descClientServerInfo == null) {
                logger.error("没有找到此服务的实例，有可能是因为服务还没有注册到discover,ServiceName:{}", eventInfo.getServiceName());
                return;
            }
            targetServerInfo.add(descClientServerInfo);

        } else {//广播模式发送
            Map<String, List<ServerCommandInfo>> serviceNamesGroupBy = commandInfos.stream().collect(Collectors
                    .groupingBy(ServerCommandInfo::getServiceName));
            Set<String> serviceNames = serviceNamesGroupBy.keySet();
            for (String serviceName : serviceNames) {
                List<ClientServerInfo> allClientServerInfo = serverLocation.getAllClientServerInfo(serviceName);
                targetServerInfo.addAll(allClientServerInfo);
            }
        }

        //执行发送动作

        for (ClientServerInfo clientServerInfo : targetServerInfo) {
            if (clientServerInfo.getServiceName().equals(localServiceName) &&
                    Objects.equals(clientServerInfo.getServerId(), serverConfigProperties.getServerId())) {
                CommandHandler localCommandHandler = getLocalCommandHandler(String.valueOf(rawMessage.getCmd()));
                if (localCommandHandler != null) {
                    localCommandHandler.run(request, response);
                }
            } else {
                anyClientManager.forwardMessage(clientServerInfo, request.getMessage());
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
