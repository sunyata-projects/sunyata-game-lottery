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
import org.sunyata.game.contract.thrift.ai.AIService;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.phase.TicketResult;
import org.sunyata.game.game.regular.GameRegularModel;
import org.sunyata.game.game.regular.GameRegularState;
import org.sunyata.game.game.regular.phase.GuessSizePhaseModel;
import org.sunyata.game.login.UserService;
import org.sunyata.game.lottery.contract.protobuf.regular.GameRegular;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.math.BigDecimal;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.RegularGuessSize, routeModel = ServerCommand.lbMode)
public class RegularGuessSizeCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(RegularGuessSizeCommand.class);


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

    private WebRequest getWebRequest(int gameId){
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
            GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId());

            if (gameModel == null) {
                sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
                return;
            }
//            gameModel.addGuessSizePhase(gameModel.getGameInstanceId());
            //是否翻牌
            GuessSizePhaseModel phase = (GuessSizePhaseModel) gameModel.getPhase(GameRegularState.GuessSize.getValue());
            TicketResult ticketResult = phase.getPhaseData().getTicketResult();
            //中奖金额
            BigDecimal prizeCash = ticketResult.getPrizeCash();
            //奖等
            int prizeLevel = (int) ticketResult.getPrizeLevel();
            GameRegular.RegularGuessSizeResponseMsg.Builder builder = GameRegular.RegularGuessSizeResponseMsg
                    .newBuilder();
            builder.setTotalMoney(prizeCash.toPlainString()).setFlag(prizeLevel == 1);
            sendMessageToUser(request, builder.build().toByteArray());
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        } finally {
        }
    }

    @Override
    public String getCommand() {
        return Commands.RegularGuessSize;
    }

    @Override
    public String getRetCommand() {
        return Commands.RegularGuessSizeRet;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
