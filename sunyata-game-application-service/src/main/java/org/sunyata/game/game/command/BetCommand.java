package org.sunyata.game.game.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.WebRequest;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.ErrorCodes;
import org.sunyata.game.game.Bet;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.GameModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.StartGameResponse;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.Bet, routeModel = ServerCommand.lbMode)
public class BetCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(BetCommand.class);


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
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        Common.BetRequestMsg betRequestMsg = Common.BetRequestMsg.parseFrom(rawMessage.getBody());
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        int gameId = betRequestMsg.getGameType();
        //int amt = betRequestMsg.getAmt();
        //RegularWagerType regularWagerType = RegularWagerType.getByDesc(String.valueOf(amt));
        logger.info("amt:{}", request);


        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);

        if (userLocationInfo == null) {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.BetRet), userInGatewayId,
                    gatewayServerId, Integer.parseInt
                            (ErrorCodes
                                    .userAndPasswordIncorrect));
            return;
        }


        GameModel gameModel = gameManager.createGameModelAndBetPhase(betRequestMsg.getGameType(),
                userLocationInfo.getAccountId(), betRequestMsg
                        .getAmt());

        GameManager.put(userLocationInfo.getChannelId(), userLocationInfo.getAccountId(), gameModel);
        WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
        StartGameResponse startGameResponse = webRequest.startGame(userLocationInfo.getChannelId(), userLocationInfo
                .getAccountId(), String.valueOf(gameId), new GameData());
        if (startGameResponse == null || !startGameResponse.isSuccess()) {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.BetRet), userInGatewayId,
                    gatewayServerId, Integer.parseInt(ErrorCodes
                            .userAndPasswordIncorrect));
            return;
        }
        String gameCycleId = startGameResponse.getGameCycleId();
        userLocationInfo.setGameCycleId(gameCycleId);
        userLocationInfo.setGameId(gameId);
        userLocationService.cacheUserLocation(userLocationInfo);

        //UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);
        Bet bean = applicationContext.getBean(gameId + "_bet", Bet.class);
        byte[] bytes = bean.doBet(gameCycleId, request, userLocationInfo);
//            anyClientManager.sendMessageToUser(request.getMessage().getDataId(), request.getMessage()
//                    .getSourceServerId(), Commands.BetRet, build.toByteArray());
        sendMessageToUser(request, bytes);
//        userLocationInfo.setGameCycleId(gameCycleId);
//        userLocationInfo.setGameId(gameId);
//        userLocationService.cacheUserLocation(userLocationInfo);
//
//        WagerResponse wagerResponse = webRequest.wager(userLocationInfo.getChannelId(), userLocationInfo
//                .getAccountId(), String.valueOf(gameId), gameCycleId, String.valueOf(regularWagerType.getCode()),
// String.valueOf(amt));
//        if (wagerResponse != null && wagerResponse.isSuccess()) {
//            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
//            String center = wagerResponse.getGameData().getString("center");
//            List<String> centerStringList = Arrays.asList(center.split(","));
//            List<Integer> centerList = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//            builder.addAllCenterCard(centerList);
//            Common.DealResponseMsg build = builder.build();
//            anyClientManager.sendMessageToUser(request.getMessage().getDataId(), request.getMessage()
//                    .getSourceServerId(), Commands.BetRet, build.toByteArray());
//        } else {
//            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.BetRet), userInGatewayId,
//                    gatewayServerId, Integer.parseInt(ErrorCodes
//                            .userAndPasswordIncorrect));
//        }
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getRetCommand() {
        return Commands.BetRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
