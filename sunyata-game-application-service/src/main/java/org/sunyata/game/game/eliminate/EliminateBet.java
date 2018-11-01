package org.sunyata.game.game.eliminate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.WebRequest;
import org.sunyata.game.contract.GameIds;
import org.sunyata.game.game.Bet;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.PhaseState;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseData;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseDataItem;
import org.sunyata.game.game.eliminate.phase.EliminatePlayPhaseModel;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.lottery.contract.protobuf.eliminate.Eliminate;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.EliminateWagerType;
import org.sunyata.lottery.edy.common.vo.GameData;
import org.sunyata.lottery.edy.common.vo.WagerResponse;

import java.util.List;

/**
 * @author leo
 */
@Component(GameIds.Eliminate + "_bet")
public class EliminateBet implements Bet, ApplicationContextAware {
//    @Autowired
//    AbstractWebRequest webRequest;

    @Autowired
    GameManager gameManager;
    private ApplicationContext applicationContext;

    @Override
    public byte[] doBet(String gameCycleId, OctopusPacketRequest request, UserLocationInfo userLocationInfo) throws
            Exception {
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        Common.GamePointBetRequestMsg gamePointBetRequestMsg = Common.GamePointBetRequestMsg.parseFrom(rawMessage
                .getBody());
        int gamePoint = gamePointBetRequestMsg.getGamePoint();
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        GameEliminateModel gameModel = (GameEliminateModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                userLocationInfo.getAccountId());
        int gameId = gameModel.getGameType();
        gameModel.addOrUpdatePlayPhase(gamePoint);

        EliminateWagerType eliminateWagerType = EliminateWagerType.getByDesc(String.valueOf(gamePoint));
        WebRequest webRequest = applicationContext.getBean("WebRequest_" + gameId, WebRequest.class);
        GameData gameData = new GameData().set(Constants.WagerId, String.valueOf(eliminateWagerType.getCode())).set
                (Constants.WagerAmount, String.valueOf(gameCycleId));
        EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameModel.getPhase(GameEliminateState.Play
                .getValue());
        int n = 1;
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = null;
        if (phase != null) {
            EliminatePlayPhaseData phaseData = phase.getPhaseData();
            lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
            n = lastPlayPhaseDataItem.getOrderBy();
        }
        WagerResponse wagerResponse = webRequest.wager(userLocationInfo.getChannelId(), userLocationInfo
                        .getAccountId(), String.valueOf(gameId), gameCycleId, String.format(Stages
                        .Game105.FIRST_WAGER, n),
                gameData);
        if (wagerResponse == null || !wagerResponse.isSuccess()) {
            throw new Exception("下注失败");
        }
        if (lastPlayPhaseDataItem != null) {
            lastPlayPhaseDataItem.setOver(true);

            //如果达到7个宝箱
            if (lastPlayPhaseDataItem.getTotalDoubleKingCount() == 7) {
                phase.setPhaseState(PhaseState.Success);
            }
        }
        Eliminate.EliminateDealResponseMsg.Builder builder = Eliminate.EliminateDealResponseMsg.newBuilder();

        Object o = wagerResponse.getSymbolDistributionObjectValue(Constants.EliminateCards);
        boolean isHitJackpot = wagerResponse.getSymbolDistributionBoolValue(Constants.EliminateIsHitJackpot, false);

        String accountPoints = wagerResponse.getGameData().getString(Constants.EliminateAccountTotalPoints);
        String cashPoint = wagerResponse.getGameData().getString(Constants.EliminateAccountCashPoints);
        String prizePoints = wagerResponse.getGameData().getString(Constants.EliminateAccountPrizePoints);
        String opendBoxCount = wagerResponse.getSymbolDistributionStringValue(Constants.EliminateopendBoxCount);
        opendBoxCount = opendBoxCount == null ? "0" : opendBoxCount;

        if (o != null) {
            List cardList = (List) o;
            for (Object obj : cardList) {
                List<Integer> cardRow = (List<Integer>) obj;
                Eliminate.CardRow.Builder builderCardRow = Eliminate.CardRow.newBuilder();
                builderCardRow.addAllCards(cardRow);
                builder.addCards(builderCardRow.build());
            }
        }
        //builder.addAllCards(cardList);

        builder.setZhiZun(isHitJackpot);
        lastPlayPhaseDataItem.setHitJackpot(isHitJackpot);
        lastPlayPhaseDataItem.setHitJackpotMoney(wagerResponse.getGameData().getString(Constants.JsonKeyPrizeAmount));
        builder
                .setTotalAwardGamePoint(Integer.parseInt(accountPoints))
                .setGameProgress(Integer.parseInt(opendBoxCount))
                .setExchangeGamePointBalance(Integer.parseInt(cashPoint));
        Eliminate.EliminateDealResponseMsg build = builder.build();

        return build.toByteArray();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
