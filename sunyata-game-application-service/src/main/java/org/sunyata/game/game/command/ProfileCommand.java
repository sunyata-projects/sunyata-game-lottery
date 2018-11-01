package org.sunyata.game.game.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.Profile, routeModel = ServerCommand.lbMode)
public class ProfileCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(ProfileCommand.class);


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

//    @Autowired
//    AbstractWebRequest webRequest;
    private ApplicationContext applicationContext;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Autowired
    GameManager gameManager;
    @Autowired
    UserLocationService userLocationService;

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        //startGame 请求
        //下注请求
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        logger.info("amt:{}", request);
        Common.ProfileResponseMsg lcl = Common.ProfileResponseMsg.newBuilder().setDisplayName("恭喜我中五百万")
                .setMoney("10000").setUserId("lcl").setVc(1000).build();
        sendMessageToUser(request, lcl.toByteArray());
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getRetCommand() {
        return Commands.ProfileRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
