package org.sunyata.game.edy.state.manager.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.command.ServerCommandInfo;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.service.ServerLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/11/2.
 */
@Component("defaultCommandService")
//@Primary
public class DefaultCommandService implements CommandService, ServerLocation, ApplicationContextAware {
    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    public String serviceName;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    public static final String eventKeyPrefix = "octopus_command_";

    @Autowired
    RedisTemplate<String, Object> template;
    private ApplicationContext applicationContext;

    //    public ServerCommandInfo getCommandInfo(Integer commandId) {
//        Object o = template.opsForHash().get(CommandStateConfig.commandCacheKey, String.valueOf(commandId));
//        if (o != null) {
//            ServerCommandInfo info = (ServerCommandInfo) o;
//            return info;
//        }
//        return null;
//    }
//
    @Override
    public ServerCommandInfo getSingleCommandInfo(Integer commandId) throws Exception {
        List<ServerCommandInfo> result = new ArrayList<>();
        Map<Object, Object> entries = template.opsForHash().entries(eventKeyPrefix + commandId);
        //Set<Object> members = template.opsForSet().members(CommandStateConfig.eventKeyPrefix + commandId);
        if (entries != null) {

            for (Object obj : entries.values()) {
                //Object commandId1 = map.getOrDefault("commandId", null);
                result.add((ServerCommandInfo) obj);

                //return members.stream().map(p -> (ServerCommandInfo) p).collect(Collectors.toList());
            }
        }
        if (result.size() > 1) {
            throw new Exception("不允许在多对啊Server上实现Command:" + commandId);
        }
        return result.get(0);
    }

    @Override
    public List<ServerCommandInfo> getCommandInfo(Integer commandId) {
        List<ServerCommandInfo> result = new ArrayList<>();
        Map<Object, Object> entries = template.opsForHash().entries(eventKeyPrefix + commandId);
        if (entries != null) {
            for (Object obj : entries.values()) {
                result.add((ServerCommandInfo) obj);
            }
        }
        return result;
    }

    @Override
    public ClientServerInfo getDescClientServerInfo(Integer serverId, String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        ServiceInstance serviceInstance = instances.stream().filter(p -> p.getMetadata().get("serverId").equals
                (String.valueOf(serverId))).findFirst().orElse(null);
        if (serviceInstance != null) {
            return new ClientServerInfo().setServerId(serverId).setServerAddress(serviceInstance
                    .getHost()).setServerPort(serviceInstance.getPort() + 1).setServiceName(serviceName);
        }
        return null;

    }

    @Override
    public ClientServerInfo getRandomClientServerInfo(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.size() > 0) {
            ServiceInstance serviceInstance = instances.get(RandomUtils.nextInt(instances.size()));
            String serverId = serviceInstance.getMetadata().getOrDefault("serverId", null);
            return new ClientServerInfo().setServiceName(serviceName).setServerId(Integer.parseInt(serverId))
                    .setServerAddress
                            (serviceInstance
                                    .getHost()).setServerPort(serviceInstance.getPort() + 1);
        }
        return null;
    }

    @Override
    public List<ClientServerInfo> getAllClientServerInfo(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        //Object[] objects = sceneList.toArray();
        List<ClientServerInfo> results = new ArrayList<>();
        for (ServiceInstance serviceInstance : instances) {
            String serverId = serviceInstance.getMetadata().getOrDefault("serverId", null);
            results.add(new ClientServerInfo().setServiceName(serviceName).setServerId(Integer.parseInt(serverId))
                    .setServerAddress
                            (serviceInstance
                                    .getHost()).setServerPort(serviceInstance.getPort() + 1));
        }
        return results;
    }

    @Override
    public ClientServerInfo getRandomInnerGatewayServerInfo() {
        List<ServiceInstance> instances = discoveryClient.getInstances("game-inner-gateway-service");
        ServiceInstance serviceInstance = instances.get(RandomUtils.nextInt(instances.size()));
        String serverId = serviceInstance.getMetadata().getOrDefault("serverId", null);
        return new ClientServerInfo().setServiceName(serviceInstance.getServiceId()).setServerId(Integer.parseInt
                (serverId)).setServerAddress(serviceInstance.getHost()).setServerPort(serviceInstance.getPort() + 1);
    }

    @Override
    public void registerCommand(String commandId, int serverId, ServerCommandInfo commandInfo) {
        template.opsForHash().put(eventKeyPrefix + commandId, serverId, commandInfo);
    }

    @Override
    public void unRegisterCommand(String commandId, int serverId) {
        template.opsForHash().delete(eventKeyPrefix + commandId, serverId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void start() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
        for (Object obj : beansWithAnnotation.values()) {
            ServerCommand annotation = obj.getClass().getAnnotation(ServerCommand.class);
            ServerCommandInfo info = new ServerCommandInfo()
                    .setCommandId(annotation.commandId())
                    .setCommandName(annotation.commandName())
                    .setServiceName(serviceName)
                    .setRouteMode(annotation.routeModel())
                    //.setLoadBalanceModel(annotation.loadBalanceModel())
                    .setDescription(annotation.description());
//                    .setEvent(annotation.isEvent())
//                    .setEventModel(annotation.eventModel());
            //if (annotation.isEvent()) {
//            template.opsForHash().put(eventKeyPrefix + annotation.commandId(), serverConfigProperties.getGatewayServerId(),
//                    info);
            registerCommand(annotation.commandId(), serverConfigProperties.getServerId(), info);
        }
    }

    @Override
    public void stop() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
        for (Object obj : beansWithAnnotation.values()) {
            ServerCommand annotation = obj.getClass().getAnnotation(ServerCommand.class);
            ServerCommandInfo info = new ServerCommandInfo()
                    .setCommandId(annotation.commandId())
                    .setCommandName(annotation.commandName())
                    .setServiceName(serviceName)
                    .setRouteMode(annotation.routeModel())
                    //.setLoadBalanceModel(annotation.loadBalanceModel())
                    .setDescription(annotation.description());
//                    .setEvent(annotation.isEvent())
//                    .setEventModel(annotation.eventModel());
            //if (annotation.isEvent()) {
            //template.opsForHash().delete(eventKeyPrefix + annotation.commandId(), serverConfigProperties
            //.getGatewayServerId());
            unRegisterCommand(annotation.commandId(), serverConfigProperties.getServerId());
        }
    }
}

