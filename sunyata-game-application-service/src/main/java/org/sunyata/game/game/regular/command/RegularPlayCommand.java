package org.sunyata.game.game.regular.command;

import org.apache.thrift.TException;
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
import org.sunyata.game.contract.thrift.ai.CheckCards;
import org.sunyata.game.contract.thrift.ai.ShowCards;
import org.sunyata.game.game.GameManager;
import org.sunyata.game.game.Utility;
import org.sunyata.game.game.exception.InvalidPlayException;
import org.sunyata.game.game.exception.InvalidRoleException;
import org.sunyata.game.game.phase.DealPhaseData;
import org.sunyata.game.game.phase.DealPhaseModel;
import org.sunyata.game.game.phase.TicketResult;
import org.sunyata.game.game.regular.GameRegularModel;
import org.sunyata.game.game.regular.GameRegularState;
import org.sunyata.game.game.regular.phase.*;
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
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.GameId;
import org.sunyata.lottery.edy.common.vo.CheckPointResponse;
import org.sunyata.lottery.edy.common.vo.RoundResultResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.RegularPlay, routeModel = ServerCommand.lbMode)
public class RegularPlayCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(RegularPlayCommand.class);


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
        Common.PlayResponseMsg.Builder builder = Common.PlayResponseMsg.newBuilder();
        try {
            GameRegularModel gameModel = (GameRegularModel) gameManager.getGameModel(userLocationInfo.getChannelId(),
                    userLocationInfo.getAccountId());

            if (gameModel == null) {
                sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
                return;
            }

            Common.PlayRequestMsg playRequestMsg = Common.PlayRequestMsg.parseFrom(rawMessage.getBody());
            boolean isAuto = playRequestMsg.getIsAuto();//是否托管
            Integer position = playRequestMsg.getRolePosition();//玩家位置,1地主,2 左边农民,3右边农民
            List<Integer> playCards = playRequestMsg.getCardsList();//出的牌,如果是机器人或托管,则忽略此值

            RegularPlayPhaseModel playPhaseModel = gameModel.addOrUpdatePlayPhase();
            RegularPlayPhaseData phaseData = playPhaseModel.getPhaseData();
            RegularPlayPhaseDataItem lastItem = phaseData.getLastDataItem();
            RegularPlayPhaseDataItem item = new RegularPlayPhaseDataItem();

            if (lastItem == null) {//此次为第一次出牌
                DealPhaseModel dealPhaseModel = (DealPhaseModel) gameModel.getPhase(GameRegularState.Dark.getValue());
                DealPhaseData dealPhaseData = dealPhaseModel.getPhaseData();
                item.setCenterCards(new ArrayList<>(dealPhaseData.getCenterCard()))
                        .setRightCards(new ArrayList<>(dealPhaseData.getRightCard()))
                        .setLeftCards(new ArrayList<>(dealPhaseData.getLeftCard()));
                item.getCenterCards().addAll(dealPhaseData.getDarkCard());

            } else {
                item
                        .setCenterCards(new ArrayList<>(lastItem.getCenterCards()))
                        .setRightCards(new ArrayList<>(lastItem.getRightCards()))
                        .setLeftCards(new ArrayList<>(lastItem.getLeftCards()));
//                        .setLastPlace(lastItem.getNowPlace())
//                        .setLastCards(lastItem.getShowCards())
//                        .setNowPlace(lastItem.getNextPosition())
//                        .setCurrentBombNumbers(lastItem.getCurrentBombNumbers());
            }
            item.setAuto(isAuto);
            if (!isAuto) {
                item.setShowCards(playCards);
            }
            phaseData.setNowPlace(phaseData.getNextPosition());//设置当前出牌位置为下一个
            play(item, phaseData);
            phaseData.addPhaseDataItem(item);


//            logger.info("position:{},cards:{}", phaseData.getNextPosition(), phaseData.getPlayCards());
            if (phaseData.isIfEnd()) {
                //如果打牌完成，则调用交互系统，打牌结果，以便存储
                logger.info("打牌完成，远程存储打牌结果,userLocationInfo:{},phaseData:{}", userLocationInfo, phaseData);

                RoundResultResponse roundResultResponse = getWebRequest(gameModel.getGameType()).sendRoundResult
                        (userLocationInfo
                                        .getChannelId(),
                                userLocationInfo.getAccountId(),
                                GameId.GAME_101.getCode(), userLocationInfo.getGameCycleId(), phaseData.isWin());
                logger.info("打牌完成，远程存储打牌结束");
                if (!roundResultResponse.isSuccess()) {
                    logger.info("打牌完成，远程存储打牌结果失败");
                    throw new Exception("checkPoint接口调用失败" + Json.encode(userLocationInfo));
                }
                CheckPointResponse checkPointResponse = getWebRequest(gameModel.getGameType()).checkPoint
                        (userLocationInfo.getChannelId(), userLocationInfo.getAccountId(), GameId.GAME_101.getCode(),
                                userLocationInfo.getGameCycleId(), Stages.Game101.PLAY_CARDS_OVER);
                if (!checkPointResponse.isSuccess()) {
                    logger.error("checkPoint接口调用失败{}", checkPointResponse);
                    throw new Exception("checkPoint接口调用失败" + Json.encode(userLocationInfo));
                }
//                String prizeId = checkPointResponse.getGameData().getString(Constants.JsonKeyPrizeId);
//                String prizeName = checkPointResponse.getGameData().getString(Constants.JsonKeyPrizeName);
                String prizeAmount = checkPointResponse.getGameData().getString(Constants.JsonKeyPrizeAmount);
                boolean drawFlag = checkPointResponse.getSymbolDistributionBoolValue(Constants.JsonKeyDrawFlag, false);
                gameModel.addGuessSizePhase(gameModel.getGameInstanceId());
                GuessSizePhaseModel phase = (GuessSizePhaseModel) gameModel.getPhase(GameRegularState.GuessSize
                        .getValue());
                GuessSizePhaseData guessSizePhaseData = phase.getPhaseData();
                BigDecimal guessSizeAmount = new BigDecimal(prizeAmount);
                TicketResult betResult = new TicketResult().setPrizeCash(new BigDecimal(prizeAmount)).setPrizeLevel
                        (drawFlag ? 1 : 0);
                betResult.setPrizeCash(guessSizeAmount);
                guessSizePhaseData.setTicketResult(betResult);


            }
            builder
                    .addAllCenter(item.getCenterCards())
                    .addAllRight(item.getRightCards())
                    .addAllLeft(item.getLeftCards())
                    .setIfEnd(phaseData.isIfEnd())
                    .setNextPerson(phaseData.getNextPosition())
                    .setBomNums(phaseData.getCurrentBombNumbers())
                    .addAllCards(phaseData.getPlayCards())
                    .setRolePosition(phaseData.getNowPlace());
            sendMessageToUser(request, builder.build().toByteArray());
        } catch (Exception ex) {
            sendErrorMessageToUser(request, ErrorCodes.userAndPasswordIncorrect);
        }


    }

    @Override
    public String getCommand() {
        return Commands.RegularPlay;
    }

    @Override
    public String getRetCommand() {
        return Commands.RegularPlayRet;
    }

    public void play(RegularPlayPhaseDataItem item, RegularPlayPhaseData phaseData) throws TException,
            InvalidPlayException,
            InvalidRoleException {
        boolean isAuto = item.isAuto();
        int placeRole = phaseData.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        List<Integer> cardsList = null;//
        if (item.getShowCards() != null) {
            cardsList = new ArrayList<>(item.getShowCards());
        }
        ;
        if (isAuto && placeRole == 1) {
            ShowCards showCards = new ShowCards();
            showCards.setNowPlace(placeRole);
            showCards.setCenterCards(item.getCenterCards());
            showCards.setLeftCards(item.getLeftCards());
            showCards.setRightCards(item.getRightCards());
            showCards.setLastCards(phaseData.getLastCards());
            showCards.setLastPlace(phaseData.getLastPlace());
            showCards.setCurrentBombNumber(phaseData.getCurrentBombNumbers());
            showCards.setAiVersionFlag(0);
//            if(player.getCurrentAwardType() == 0){
//                showCards.setTotalBombNumber(-1); //接口要求 当奖为0的时候，总炸弹数传-1
//            }else{
//                int bombs=this.useLevelForBombs(player.getCurrentAwardType());
//                showCards.setTotalBombNumber(bombs);
//            }

            List<Integer> putList = new ArrayList<Integer>();
            long ts = System.currentTimeMillis();
            putList = aiService.playCards(showCards);
            logger.debug("Common Auto Ai playCards cost time :" + (System.currentTimeMillis() - ts) + "ms .");
            cardsList = putList;
        }
        boolean isWin = false;
        if (placeRole == 1) {//地主出牌
            CheckCards checkCards = new CheckCards();
            checkCards.setLastCards(phaseData.getLastCards());
            checkCards.setLastPlace(phaseData.getLastPlace());
            checkCards.setNowPlace(placeRole);
            checkCards.setPlayCards(cardsList);
            int flag;
            long ts = System.currentTimeMillis();
            flag = aiService.checkCards(checkCards);
            logger.debug("Common Ai checkCards cost time :" + (System.currentTimeMillis() - ts) + "ms .");

            //出牌判定 是否符合规则
            if (flag == 1) {
                logger.debug("card is right");
            } else {
                //logger.error("地主出牌非法"+player.getPaiId()+"所验证牌型"+checkCards.toString());
                //return false;
                throw new InvalidPlayException("地方出牌非法");
            }

//            int bomNums = Utility.decideBomNums(cardsList);//所出的牌是否包含炸弹
//            item.setCurrentBombNumbers(item.getCurrentBombNumbers() + bomNums);

//            for (Integer cardValue : cardsList) {
//                if (item.getCenterCards().contains(cardValue)) {
//                    item.getCenterCards().remove(cardValue);
//                }
//            }
//
//            if (item.getCenterCards().size() == 0) {
//                item.setIfEnd(true);
//                isWin = true;
//            }
//            item.setNextPosition(2);
            isWin = syncCards(phaseData, item, cardsList, placeRole);
        } else if (placeRole == 2 || placeRole == 3) {
            //牌库取牌
            ShowCards showCards = new ShowCards();
            showCards.setNowPlace(placeRole);
            showCards.setCenterCards(item.getCenterCards());
            showCards.setLeftCards(item.getLeftCards());
            showCards.setRightCards(item.getRightCards());
            showCards.setLastCards(phaseData.getLastCards());
            showCards.setLastPlace(phaseData.getLastPlace());
            showCards.setCurrentBombNumber(phaseData.getCurrentBombNumbers());
            showCards.setTotalBombNumber(0);
//            if (player.getCurrentAwardType() == 0) {
//                showCards.setTotalBombNumber(-1);
//                showCards.setAiVersionFlag(0);
//            } else {
//                int bombs = this.useLevelForBombs(player.getCurrentAwardType());
//                showCards.setTotalBombNumber(bombs);
//                int flag = player.getPaiId().substring(0, 2) == "C-L" ? 0 : 1;
//                showCards.setAiVersionFlag(flag);
//            }

            List<Integer> putList = new ArrayList<Integer>();

            long start = System.currentTimeMillis();
            putList = aiService.playCards(showCards);
            long end = System.currentTimeMillis();
            logger.debug(" Common Ai get cards time is :" + (end - start));
            cardsList = putList;
            logger.info("placeRole:" + placeRole + " ,  cardsList = " + cardsList);
            syncCards(phaseData, item, cardsList, placeRole);
        } else {
            //错误
            logger.error("出牌过程 ，出现错误！出现非法角色！");
            throw new InvalidRoleException("出牌过程 ，出现错误！出现非法角色！");
        }
        //重置上一出牌人
        if (cardsList.size() > 0) {
            phaseData.setLastPlace(placeRole);
            phaseData.setLastCards(cardsList);
        } else {
        }
        phaseData.setWin(isWin);
    }

    public boolean syncCards(RegularPlayPhaseData phaseData, RegularPlayPhaseDataItem item, List<Integer> cardList,
                             Integer placeRole) {
        List<Integer> willSyncCards = null;
        if (placeRole == 3) {
            willSyncCards = item.getLeftCards();
            phaseData.setNextPosition(1);
        } else if (placeRole == 2) {
            willSyncCards = item.getRightCards();
            phaseData.setNextPosition(3);
        } else {
            willSyncCards = item.getCenterCards();
            phaseData.setNextPosition(2);
        }

        for (Integer cardValue : cardList) {
            if (willSyncCards.contains(cardValue)) {
                willSyncCards.remove(cardValue);
            }
        }
        boolean result = false;
        if (willSyncCards.size() == 0) {
            phaseData.setIfEnd(true);
            if (placeRole == 1) {
                result = true;
            }
        }
        phaseData.setPlayCards(cardList);
        int bomNums = Utility.decideBomNums(cardList);//所出的牌是否包含炸弹
        phaseData.setCurrentBombNumbers(phaseData.getCurrentBombNumbers() + bomNums);
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
