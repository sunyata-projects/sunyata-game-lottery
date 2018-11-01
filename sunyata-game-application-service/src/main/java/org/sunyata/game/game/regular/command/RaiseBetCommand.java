package org.sunyata.game.game.regular.command;

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
import org.sunyata.game.game.GameCommonState;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.phase.BetPhaseModel;
import org.sunyata.game.game.regular.GameRegularModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.lottery.contract.protobuf.regular.GameRegular;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.enums.RegularWagerType;
import org.sunyata.lottery.edy.common.vo.WagerResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.RegularRaise, routeModel = ServerCommand.lbMode)
public class RaiseBetCommand extends AbstractCommandHandler implements ApplicationContextAware{

    Logger logger = LoggerFactory.getLogger(RaiseBetCommand.class);


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

//    @Autowired
//    AbstractWebRequest webRequest;


    @Autowired
    GameManager gameManager;
    private ApplicationContext applicationContext;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        //下注请求
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        GameRegular.RegularRaiseBetRequestMsg raiseBetRequestMsg = GameRegular.RegularRaiseBetRequestMsg
                .parseFrom(rawMessage.getBody());
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo == null) {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.BetRet), userInGatewayId,
                    gatewayServerId, Integer.parseInt
                            (ErrorCodes
                                    .userAndPasswordIncorrect));
            return;
        }
        GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                userLocationInfo.getAccountId());
        int gameId = userLocationInfo.getGameId();
        int times = raiseBetRequestMsg.getTimes();
//        GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
//                userLocationInfo.getAccountId());
        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameCommonState.Bet.getValue());
        int betAmt = phase.getPhaseData().getBetAmt();
        RegularWagerType regularWagerType = RegularWagerType.getByDesc(String.valueOf(betAmt));
        String gameCycleId = userLocationInfo.getGameCycleId();
        logger.info("times:{}", times);
        WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
        WagerResponse wagerResponse = webRequest.regularRaiseWager(userLocationInfo.getChannelId(), userLocationInfo
                .getAccountId(), gameId, gameCycleId, times,regularWagerType.getCode().toString());
        if (wagerResponse != null && wagerResponse.isSuccess()) {
            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();

            String cardId = wagerResponse.getSymbolDistributionStringValue("cardId");
            String centerId = wagerResponse.getSymbolDistributionStringValue("centerId");
            String left = wagerResponse.getSymbolDistributionStringValue("left");
            String center = wagerResponse.getSymbolDistributionStringValue("center");
            String right = wagerResponse.getSymbolDistributionStringValue("right");
            String under = wagerResponse.getSymbolDistributionStringValue("under");

            List<String> centerStringList = Arrays.asList(center.split(","));
            List<Integer> centerList = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());

            List<String> rightStringList = Arrays.asList(right.split(","));
            List<Integer> rightList = rightStringList.stream().map(Integer::valueOf).collect(Collectors.toList());

            List<String> leftStringList = Arrays.asList(left.split(","));
            List<Integer> leftList = leftStringList.stream().map(Integer::valueOf).collect(Collectors.toList());

            List<String> underStringList = Arrays.asList(under.split(","));
            List<Integer> underList = underStringList.stream().map(Integer::valueOf).collect(Collectors.toList());

//            Common.DealResponseMsg build = builder.build();
            builder.addAllDarkCard(underList).addAllCenterCard(centerList)
                    .addAllRightCard(rightList).addAllLeftCard(leftList);
//            GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
//                    userLocationInfo
                            //.getAccountId());
            gameModel.addDarkPhase(cardId, centerList, rightList, leftList, underList);
            anyClientManager.sendMessageToUser(request.getMessage().getDataId(), request.getMessage()
                    .getSourceServerId(), Commands.RegularRaiseRet, builder.build().toByteArray());
        } else {
            anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.RegularRaiseRet), userInGatewayId,
                    gatewayServerId, Integer.parseInt(ErrorCodes
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
