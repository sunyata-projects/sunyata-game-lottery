package org.sunyata.game.command;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.service.UserLocationService;

/**
 * Created by leo on 17/4/28.
 */
public abstract class AbstractCommandHandler implements CommandHandler {
    @Autowired
    protected AnyClientManager anyClientManager;
    @Autowired
    protected CommandService commandService;
    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected UserLocationService userLocationService;


    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return false;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {

    }

    @Override
    public final void run(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        if (onExecuteBefore(request, response)) {
            execute(request, response);
        }
    }

//    public abstract String getGatewayServiceName();

    public void sendErrorMessageToUser(int commandId, int userIdInGateway, int destServerId, int errorCode) throws
            Exception {
//        ClientServerInfo descClientServerInfo = commandService.getDescClientServerInfo(sourceServerId,
//                getGatewayServiceName());
//        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserErrorPacketMessage(userIdInGateway,
//                sourceServerId, destServerId, errorCode);
//        anyClientManager.forwardMessage(descClientServerInfo, toUserPacketMessage);
        anyClientManager.sendErrorMessageToUser(commandId, userIdInGateway, destServerId, errorCode);
    }

    public void sendMessageToUser(int userIdInGateway, int destServerId, byte[]
            bytes) throws
            Exception {
        anyClientManager.sendMessageToUser(userIdInGateway, destServerId, getRetCommand(), bytes);
    }

    public void sendMessageToUser(OctopusPacketRequest request, byte[] bytes) throws
            Exception {
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        anyClientManager.sendMessageToUser(userInGatewayId, gatewayServerId, getRetCommand(), bytes);
    }

    public void sendErrorMessageToUser(OctopusPacketRequest request, String errorCode) throws Exception {
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        anyClientManager.sendErrorMessageToUser(Integer.parseInt(getRetCommand()), userInGatewayId, gatewayServerId, Integer.valueOf
                (errorCode));
    }

    public abstract String getCommand();

    public abstract String getRetCommand();

    @Override
    public boolean isAsync() {
        return false;
    }


}
