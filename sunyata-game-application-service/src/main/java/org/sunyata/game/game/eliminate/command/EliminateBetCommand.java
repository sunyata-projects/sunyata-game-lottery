package org.sunyata.game.game.eliminate.command;

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
import org.sunyata.game.contract.thrift.ai.AIService;
import org.sunyata.game.game.Bet;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.eliminate.GameEliminateModel;
import org.sunyata.game.game.eliminate.GameEliminateState;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseData;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseDataItem;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.vo.CheckPointResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;


/**
 * @author leo
 * @date 17/11/7
 */
@ServerCommand(commandId = Commands.EliminateGamePointBet, routeModel = ServerCommand.lbMode)
public class EliminateBetCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(EliminateBetCommand.class);


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
    @ThriftClient(serviceId = "game-edy-ai-service", path = "/ai")
    AIService.Client aiService;
    private ApplicationContext applicationContext;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        //下注请求
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo == null) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
            return;
        }
        GameEliminateModel gameModel = (GameEliminateModel) gameManager.getGameModel(userLocationInfo
                        .getChannelId(),
                userLocationInfo.getAccountId());
        int gameId = gameModel.getGameType();
        try {
            OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
            Common.GamePointBetRequestMsg gamePointBetRequestMsg = Common.GamePointBetRequestMsg.parseFrom(rawMessage
                    .getBody());
            int gamePoint = gamePointBetRequestMsg.getGamePoint();
            gameModel.addOrUpdatePlayPhase(gamePoint);
            Bet bean = applicationContext.getBean(gameId + "_bet", Bet.class);
            byte[] bytes = bean.doBet(gameModel.getGameCycleId(), request, userLocationInfo);
//            EliminatePlayPhaseData playPhaseData = (EliminatePlayPhaseData) gameModel.getPhase(GameEliminateState
//                    .Play.getValue())
//                    .getPhaseData();
//
//            EliminatePlayPhaseDataItem lastPlayPhaseDataItem = null;
//            if (playPhaseData != null) {
//                lastPlayPhaseDataItem = playPhaseData.getLastPlayPhaseDataItem();
//            }

            sendMessageToUser(request, bytes);
            WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
            EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameModel.getPhase(GameEliminateState.Play
                    .getValue());
            int n = 1;
            if (phase != null) {
                EliminatePlayPhaseData phaseData = phase.getPhaseData();
                EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
                n = lastPlayPhaseDataItem.getOrderBy();
            }
            String stageId = String.format(Stages.Game105.PLAY_OVER, n);
            CheckPointResponse checkPointResponse = webRequest.checkPoint(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId(), String.valueOf(gameId), userLocationInfo.getGameCycleId(),
                    stageId);
            if (!checkPointResponse.isSuccess()) {
                logger.error("checkPoint接口调用失败{}", checkPointResponse);
//                throw new Exception("checkPoint接口调用失败" + Json.encode(userLocationInfo));
            }
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        } finally {
        }
    }

    @Override
    public String getCommand() {
        return Commands.EliminateGamePointBet;
    }

    @Override
    public String getRetCommand() {
        return Commands.EliminateGamePointBetRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
