package org.sunyata.game.login.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.ErrorCodes;
import org.sunyata.game.exceptions.UserAndPasswordIsNotRightException;
import org.sunyata.game.login.UserLoginRetInfo;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheInfo;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.Login, routeModel = ServerCommand.lbMode)
public class LoginCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LoginCommand.class);


    @Autowired
    ServerConfigProperties serverConfigProperties;
    @Autowired
    AnyClientManager anyClientManager;

    @Autowired
    CommandService commandService;

    @Autowired
    UserService userService;
    @Autowired
    UserCacheService userCacheService;

    @Autowired
    UserLocationService userLocationService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        Common.LoginRequestMsg loginRequest = Common.LoginRequestMsg.parseFrom(rawMessage.getBody());
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        String channelId = loginRequest.getChannelId();
        String accountId = loginRequest.getAccountId();
        String timeStamp = loginRequest.getTimeStamp();
        String sign = loginRequest.getSign();
        UserLoginRetInfo loginRetInfo = null;
        try {
            loginRetInfo = userService.loginByToken(channelId, accountId, timeStamp, sign);
        } catch (UserAndPasswordIsNotRightException ex) {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.loginRet), userInGatewayId,
                    gatewayServerId, Integer.parseInt(ErrorCodes.userAndPasswordIncorrect));
            return;
        }
        if (loginRetInfo != null) {
            UserLocationInfo userLocationInfo = new UserLocationInfo()
                    .setAccountId(loginRetInfo.getAccountId()).setUserIdInGateway(request.getMessage().getDataId())
                    .setUserName(loginRetInfo.getName())
                    .setChannelId(loginRetInfo.getChannelId())
                    .setGatewayServerId(request.getMessage().getSourceServerId());
            UserCacheInfo userCacheInfo = new UserCacheInfo().setAccountId(accountId).setChannelId(channelId);
            userLocationService.cacheUserLocation(userLocationInfo);
            userCacheService.cacheUserInfo(userCacheInfo);
            //发送用户成功消息
            Common.LoginResponseMsg.Builder builder = Common.LoginResponseMsg.newBuilder();
            //todo 查询是否需要断线重连的比赛，通过登录消息直接返回
//            builder.setGameId(111);
//            builder.setGameCycleId("sdfsdfsdf");
            Common.LoginResponseMsg loginResponseMsg = builder.build();

            anyClientManager.sendMessageToUser(request.getMessage().getDataId(), request.getMessage()
                    .getSourceServerId(), Commands.loginRet, loginResponseMsg.toByteArray());
        } else {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.loginRet), userInGatewayId, gatewayServerId, Integer
                    .parseInt
                    (ErrorCodes
                    .userAndPasswordIncorrect));
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

    public boolean auth(String userName, String password) {
        return true;
    }
}
