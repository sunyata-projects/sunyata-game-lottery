package org.sunyata.game.game.regular;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.WebRequest;
import org.sunyata.game.contract.GameIds;
import org.sunyata.game.game.Bet;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.RegularWagerType;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.WagerResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leo
 */
@Component(GameIds.Regular + "_bet")
public class RegularBet implements Bet, ApplicationContextAware {
//    @Autowired
//    AbstractWebRequest webRequest;

    @Autowired
    GameManager gameManager;
    private ApplicationContext applicationContext;

    @Override
    public byte[] doBet(String gameCycleId, OctopusPacketRequest request, UserLocationInfo userLocationInfo) throws
            Exception {
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        Common.BetRequestMsg betRequestMsg = Common.BetRequestMsg.parseFrom(rawMessage.getBody());
        int gameId = betRequestMsg.getGameType();
        int amt = betRequestMsg.getAmt();
        RegularWagerType regularWagerType = RegularWagerType.getByDesc(String.valueOf(amt));
        WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
        GameData gameData = new GameData().set(Constants.WagerId, String.valueOf(regularWagerType.getCode())).set
                (Constants.WagerAmount, String.valueOf(amt));
        WagerResponse wagerResponse = webRequest.wager(userLocationInfo.getChannelId(), userLocationInfo.getAccountId
                (), String.valueOf(gameId), gameCycleId, Stages.Game101.FIRST_WAGER, gameData);
        if (wagerResponse == null || !wagerResponse.isSuccess()) {
            throw new Exception("下注失败");
        }
        Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
        String center = wagerResponse.getSymbolDistributionStringValue("center");
        String centerId = wagerResponse.getSymbolDistributionStringValue("centerId");
        List<String> centerStringList = Arrays.asList(center.split(","));
        List<Integer> centerList = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        builder.addAllCenterCard(centerList);
        Common.DealResponseMsg build = builder.build();

        GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                userLocationInfo.getAccountId());
//        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameCommonState.Bet.getValue());
        gameModel.addDealPhase(centerId, centerList);
        return build.toByteArray();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
