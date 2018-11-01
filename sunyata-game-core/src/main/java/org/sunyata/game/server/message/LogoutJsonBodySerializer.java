package org.sunyata.game.server.message;

public class LogoutJsonBodySerializer extends AbstractJsonBodySerializer {
    private int userInGatewayId;
    private int gatewayServerId;

    public int getUserInGatewayId() {
        return userInGatewayId;
    }

    public LogoutJsonBodySerializer setUserInGatewayId(int userInGatewayId) {
        this.userInGatewayId = userInGatewayId;
        return this;
    }

    public int getGatewayServerId() {
        return gatewayServerId;
    }

    public LogoutJsonBodySerializer setGatewayServerId(int gatewayServerId) {
        this.gatewayServerId = gatewayServerId;
        return this;
    }
}
