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
import org.sunyata.game.contract.thrift.ai.AIService;
import org.sunyata.game.game.GameCommonState;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.GameModel;
import org.sunyata.game.game.phase.ExchangePhaseData;
import org.sunyata.game.game.phase.ExchangePhaseModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.StartGameResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.Exchange, routeModel = ServerCommand.lbMode)
public class ExchangeCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(ExchangeCommand.class);


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
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();

        Common.ExchangeGamePointRequestMsg exchangeGamePointRequestMsg = Common.ExchangeGamePointRequestMsg
                .parseFrom(rawMessage.getBody());

        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo == null) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
            return;
        }
        int amt = exchangeGamePointRequestMsg.getAmt();
        int gameId = exchangeGamePointRequestMsg.getGameType();
        Common.ExchangeGamePointResponseMsg.Builder builder = Common.ExchangeGamePointResponseMsg.newBuilder();


        try {
            WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
            StartGameResponse startGameResponse = webRequest.startGame(userLocationInfo.getChannelId(), userLocationInfo
                    .getAccountId(), String.valueOf(gameId), new GameData().set(Constants.EliminateInitCash,
                    amt).set(Constants.EliminateInitCashPoint, amt * 100));
            if (startGameResponse == null || !startGameResponse.isSuccess()) {
                anyClientManager.sendErrorMessageToUser(Integer.parseInt(Commands.BetRet), userInGatewayId,
                        gatewayServerId, Integer.parseInt(ErrorCodes
                                .userAndPasswordIncorrect));
                return;
            }
            GameModel gameModel = gameManager.createGameModelAndExchangePhase(gameId, userLocationInfo.getAccountId()
                    , exchangeGamePointRequestMsg.getAmt());
            //获取兑换的游戏点数,返回给前端
            ExchangePhaseModel exchangePhase = (ExchangePhaseModel) gameModel.getPhase(GameCommonState.Exchange
                    .getValue());
            ExchangePhaseData exchangePhaseData = exchangePhase.getPhaseData();
            gameModel.setGameCycleId(startGameResponse.getGameCycleId());
            GameManager.put(userLocationInfo.getChannelId(), userLocationInfo.getAccountId(), gameModel);

            Integer accountCashPoints = startGameResponse.getInteger(Constants.EliminateAccountCashPoints);

            exchangePhaseData.setGamePoint(accountCashPoints);
            builder.setGamePoint(exchangePhaseData.getGamePoint());
            sendMessageToUser(request, builder.build().toByteArray());
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        } finally {
        }
    }

    @Override
    public String getCommand() {
        return Commands.Exchange;
    }

    @Override
    public String getRetCommand() {
        return Commands.ExchangeRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
