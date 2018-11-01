package org.sunyata.game.game.regular.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sunyata.core.json.Json;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.WebRequest;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.ErrorCodes;
import org.sunyata.game.contract.thrift.ai.AIService;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.GamePhaseModel;
import org.sunyata.game.game.regular.GameRegularModel;
import org.sunyata.game.game.regular.GameRegularState;
import org.sunyata.game.game.regular.phase.RegularClearPhaseData;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.regular.GameRegular;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.GameId;
import org.sunyata.lottery.edy.common.vo.CheckPointResponse;
import org.sunyata.lottery.edy.common.vo.EndGameResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.math.BigDecimal;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.RegularClear, routeModel = ServerCommand.lbMode)
public class RegularClearCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(RegularClearCommand.class);


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

    //    @Autowired
//    AbstractWebRequest webRequest;
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
        GameRegular.RegularClearGameResponseMsg.Builder builder = GameRegular.RegularClearGameResponseMsg
                .newBuilder();
        try {
            GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId());

            if (gameModel == null) {
                sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
                return;
            }
            gameModel.addClearPhase();
            GamePhaseModel gamePhaseModel = gameModel.getPhase(GameRegularState.GameOver.getValue());
            RegularClearPhaseData phaseData = (RegularClearPhaseData) gamePhaseModel.getPhaseData();
            WebRequest webRequest = getWebRequest(gameModel.getGameType());
            CheckPointResponse checkPointResponse = webRequest.checkPoint(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId(), GameId.GAME_101.getCode(), userLocationInfo.getGameCycleId(),
                    Stages.Game101.LUCKY_DRAW);
            if (!checkPointResponse.isSuccess()) {
                logger.error("checkPoint接口调用失败{}", checkPointResponse);
                throw new Exception("checkPoint接口调用失败" + Json.encode(userLocationInfo));
            }
            String prizeAmount = checkPointResponse.getGameData().getString(Constants.JsonKeyPrizeAmount);
            phaseData.setTotalMoney(new BigDecimal(prizeAmount));
            BigDecimal totalMoney = phaseData.getTotalMoney();
            builder.setTotalMoney(totalMoney.toPlainString());
            EndGameResponse endGame = webRequest.endGame(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId(), GameId.GAME_101.getCode(), userLocationInfo.getGameCycleId(),
                    "EndGame");
            sendMessageToUser(request, builder.build().toByteArray());
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        } finally {
        }
    }

    @Override
    public String getCommand() {
        return Commands.RegularClear;
    }

    @Override
    public String getRetCommand() {
        return Commands.RegularClearRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
