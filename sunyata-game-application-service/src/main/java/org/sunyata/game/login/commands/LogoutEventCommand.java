package org.sunyata.game.login.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.LogoutJsonBodySerializer;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.ServerLocation;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.Logout, routeModel = ServerCommand.fanoutMode)
public class LogoutEventCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LogoutEventCommand.class);


    @Autowired
    UserCacheService userCacheService;

    @Autowired
    UserLocationService userLocationService;

    @Autowired
    ServerLocation serverLocation;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }



    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        LogoutJsonBodySerializer jsonBodySerializer = (LogoutJsonBodySerializer) new LogoutJsonBodySerializer()
                .parseFrom(rawMessage.getBody());
        if (jsonBodySerializer != null) {
            //发送离线事件
            UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(jsonBodySerializer
                    .getGatewayServerId(), jsonBodySerializer
                    .getUserInGatewayId());
            if (userLocationInfo != null) {
                if (userLocationInfo.getSceneServerId() > 0) {
//                    anyClientManager.sendSysMessageByInnerGateway(Commands.userOfflineEvent, new
//                            UserOfflineJsonBodySerializer().setUserId(userLocationInfo.getUserId()), userLocationInfo
//                            .getSceneServerId());
                }
                userLocationService.removeCacheUserLocation(jsonBodySerializer.getGatewayServerId(),
                        jsonBodySerializer.getUserInGatewayId());
                userCacheService.removeCacheUserInfo(jsonBodySerializer.getGatewayServerId(), jsonBodySerializer
                        .getUserInGatewayId());
            }
        }
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getRetCommand() {
        return null;
    }
}
