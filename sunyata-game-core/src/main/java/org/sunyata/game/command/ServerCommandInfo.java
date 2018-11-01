package org.sunyata.game.command;

import java.io.Serializable;

public class ServerCommandInfo implements Serializable {
    String commandId;
    String description;
    String serviceName;
    String commandName;
    //Integer loadBalanceModel = 0;//0代表随机,1代表指定
    //boolean isEvent;
    //Integer eventModel;//0代表同一个服务,只有一个接收点即可,1代表多个服务多个接收点

    String routeMode;//direct fanout

    public String getRouteMode() {
        return routeMode;
    }

    public ServerCommandInfo setRouteMode(String routeMode) {
        this.routeMode = routeMode;
        return this;
    }


//    public boolean isEvent() {
//        return isEvent;
//    }
//
//    public ServerCommandInfo setEvent(boolean event) {
//        isEvent = event;
//        return this;
//    }
//
//    public Integer getEventModel() {
//        return eventModel;
//    }
//
//    public ServerCommandInfo setEventModel(Integer eventModel) {
//        this.eventModel = eventModel;
//        return this;
//    }


//    public Integer getLoadBalanceModel() {
//        return loadBalanceModel;
//    }
//
//    public ServerCommandInfo setLoadBalanceModel(Integer loadBalanceModel) {
//        this.loadBalanceModel = loadBalanceModel;
//        return this;
//    }


    public String getCommandId() {
        return commandId;
    }

    public ServerCommandInfo setCommandId(String commandId) {
        this.commandId = commandId;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public ServerCommandInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServerCommandInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getCommandName() {
        return commandName;
    }

    public ServerCommandInfo setCommandName(String commandName) {
        this.commandName = commandName;
        return this;
    }
}

