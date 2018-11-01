package org.sunyata.game.game.eliminate.command;

import org.apache.commons.lang3.StringUtils;
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
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.eliminate.GameEliminateModel;
import org.sunyata.game.game.eliminate.GameEliminateState;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseData;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseDataItem;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.eliminate.Eliminate;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.vo.CheckPointResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;


/**
 * @author leo
 * @date 17/11/7
 */
@ServerCommand(commandId = Commands.EliminateClear, routeModel = ServerCommand.lbMode)
public class EliminateClearCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(EliminateClearCommand.class);


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
    private ApplicationContext applicationContext;

    private WebRequest getWebRequest(int gameId) {
        WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
        return webRequest;
    }

    @Autowired
    GameManager gameManager;
    @ThriftClient(serviceId = "game-edy-ai-service", path = "/ai")
    AIService.Client aiService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        //下注请求
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo == null) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
            return;
        }

        try {
            GameEliminateModel gameModel = (GameEliminateModel) gameManager.getGameModel(userLocationInfo
                            .getChannelId(),
                    userLocationInfo.getAccountId());

            if (gameModel == null) {
                sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
                return;
            }
            gameModel.addClearPhase();
//            EliminateClearPhaseData phaseData = (EliminateClearPhaseData) getPhaseData(GameEliminateState.GameOver.getValue
//                    ());
            Eliminate.EliminateClearGameResponseMsg.Builder builder = Eliminate.EliminateClearGameResponseMsg
                    .newBuilder();
            WebRequest webRequest = getWebRequest(gameModel.getGameType());
            CheckPointResponse checkPointResponse = webRequest.checkPoint(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId(), String.valueOf(gameModel.getGameType()), userLocationInfo
                            .getGameCycleId(), Stages.Game105.CASH_DRAW);
            if (!checkPointResponse.isSuccess()) {
                logger.error("游戏结束时出错{}", checkPointResponse);
                sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
                return;
            }
            EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameModel.getPhase(GameEliminateState.Play
                    .getValue());
            EliminatePlayPhaseDataItem lastPlayPhaseDataItem = null;
            String totalMoney = "";
            if (phase != null) {
                EliminatePlayPhaseData phaseData = phase.getPhaseData();
                lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
                if (lastPlayPhaseDataItem.isHitJackpot()) {
                    totalMoney = lastPlayPhaseDataItem.getHitJackpotMoney();
                }
            }
            if(StringUtils.isEmpty(totalMoney)){
                totalMoney = checkPointResponse.getGameData().getString(Constants.JsonKeyPrizeAmount);
            }
            builder.setTotalMoney(totalMoney);
            sendMessageToUser(request, builder.build().toByteArray());
            webRequest.endGame(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId(), String.valueOf(gameModel.getGameType()), userLocationInfo
                            .getGameCycleId(), Stages.Game105.End_GAME);
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        } finally {
        }
    }

    @Override
    public String getCommand() {
        return Commands.EliminateClear;
    }

    @Override
    public String getRetCommand() {
        return Commands.EliminateClearRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
