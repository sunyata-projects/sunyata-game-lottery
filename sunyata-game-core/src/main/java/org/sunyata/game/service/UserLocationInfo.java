package org.sunyata.game.service;

import java.io.Serializable;

/**
 * Created by leo on 17/11/14.
 */
public class UserLocationInfo implements Serializable {

    String userName;
    int gatewayServerId;
    int userIdInGateway;
    String accountId;
    String channelId;
    int roomId;
    int sceneServerId;

    String gameCycleId;
    int gameId;

    public int getGameId() {
        return gameId;
    }

    public UserLocationInfo setGameId(int gameId) {
        this.gameId = gameId;
        return this;
    }



    public String getGameCycleId() {
        return gameCycleId;
    }

    public UserLocationInfo setGameCycleId(String gameCycleId) {
        this.gameCycleId = gameCycleId;
        return this;
    }




    public int getSceneServerId() {
        return sceneServerId;
    }

    public UserLocationInfo setSceneServerId(int sceneServerId) {
        this.sceneServerId = sceneServerId;
        return this;
    }



    public int getRoomId() {
        return roomId;
    }

    public UserLocationInfo setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }
    public String getChannelId() {
        return channelId;
    }

    public UserLocationInfo setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    public String getUserName() {
        return userName;
    }

    public UserLocationInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int getGatewayServerId() {
        return gatewayServerId;
    }

    public UserLocationInfo setGatewayServerId(int gatewayServerId) {
        this.gatewayServerId = gatewayServerId;
        return this;
    }

    public int getUserIdInGateway() {
        return userIdInGateway;
    }

    public UserLocationInfo setUserIdInGateway(int userId) {
        this.userIdInGateway = userId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public UserLocationInfo setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }



}
