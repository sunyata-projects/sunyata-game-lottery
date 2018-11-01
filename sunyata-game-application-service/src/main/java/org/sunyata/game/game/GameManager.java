package org.sunyata.game.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.core.util.IdWorker;
import org.sunyata.game.game.eliminate.GameEliminateModel;
import org.sunyata.game.game.phase.BetPhaseData;
import org.sunyata.game.game.phase.BetPhaseModel;
import org.sunyata.game.game.phase.ExchangePhaseData;
import org.sunyata.game.game.phase.ExchangePhaseModel;
import org.sunyata.game.game.regular.GameRegularModel;
import org.sunyata.lottery.edy.common.enums.GameId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class GameManager {
    private final static ConcurrentMap<String, GameModel> games = new ConcurrentHashMap<>();
    static Logger logger = LoggerFactory.getLogger(GameManager.class);

    private static String generateKey(String channelId, String accountId) {
        return channelId + "_" + accountId;
    }

    public static void put(String channelId, String accoundId, GameModel gameController) throws Exception {
        String s = generateKey(channelId, accoundId);
//        GameModel orDefault = games.getOrDefault(s, null);
//        if (orDefault != null) {
//            throw new Exception("已经存在进行中的游戏");
//        }
        games.put(s, gameController);
    }

    public static void onUserLeft(String channelId, String accountId) {
//        EventBus.getLocalPubsubStore().publish(UserLeftEventMessage.EventType, new UserLeftEventMessage());
        games.remove(generateKey(channelId, accountId));
    }


    public static void onGameOver(String accountId) {
        games.remove(accountId);
    }

    public static GameModel getGameController(String accountId) {
        return games.getOrDefault(accountId, null);
    }


    public static boolean exist(String channelId, String accountId) {
        return games.containsKey(generateKey(channelId, accountId));
    }

//    public void kickPlayer(String userName) {
//        Session session = SessionManager.getSession(userName);
//        if (session == null) {
//            logger.info("目标用户不存在,{}无法被踢除", userName);
//            return;
//        }
//        new OctopusResponse(session.getHandlerContext(), Integer.parseInt(Commands.KickPlayer), 0).writeAndFlush();
//        session.getHandlerContext().channel().close();
//    }

    //    public static void notifyBalanceChange(String userName, BigDecimal balance) {
//        Session session = SessionManager.getSession(userName);
//        if (session == null) {
//            logger.info("目标用户不存在,{}无法被踢除", userName);
//            return;
//        }
//        OctopusResponse octopusResponse = new OctopusResponse(session.getHandlerContext(), Integer.parseInt(Commands
//                .NotifyBalanceChanged), 0);
//        Common.BalanceChangedResponseMsg.Builder builder = Common.BalanceChangedResponseMsg.newBuilder();
//        builder.setBalance(balance.toPlainString());
//        Common.BalanceChangedResponseMsg responseMsg = builder.build();
//        octopusResponse.setBody(responseMsg.toByteArray());
//        octopusResponse.writeAndFlush();
//    }
//
    @Autowired
    MessageClient messageClient;

    public void syncGameModel(GameModel gameModel) throws Exception {
//        EventBus.getDistributePubsubStore().publish(SyncGameModelMessage.EventType, new SyncGameModelMessage()
//                .setContext(Json.encode(gameModel)));
        messageClient.asyncSaveGameModel(gameModel);

    }

//    public void saveGameModelToCacheAndAsyncDb(GameModel gameModel) throws Exception {
//        storeManager.storeGameModel(gameModel.getUserName(), gameModel);
//        messageClient.asyncSaveGameModel(gameModel);
//    }

    public GameModel getGameModel(String channelId, String accountId) {
        GameModel orDefault = games.getOrDefault(generateKey(channelId, accountId), null);
        return orDefault;
    }

    public GameModel createGameModelAndBetPhase(int gameType, String userName, int betAmt) {
        GameModel gameModel = null;
        IdWorker worker = new IdWorker(0, 0);
        String gameInstanceId = String.valueOf(worker.nextId());
        if (gameType == Integer.valueOf(GameId.GAME_101.getCode())) {
            gameModel = new GameRegularModel(gameInstanceId);
            gameModel.setUserName(userName);
            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameCommonState.Bet.getValue(), 1)
                    .setPhaseData
                            (new BetPhaseData()
                                    .setBetAmt(betAmt));
            gameModel.addOrUpdatePhase(gamePhaseModel);
//        } else if (gameType == GameId.GAME_102.getCode()) {
//            gameModel = new GameMissionModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameCommonState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//        } else if (gameType == GameTypes.Puzzle.getValue()) {
//            gameModel = new GamePuzzleModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GamePuzzleState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//
//        } else if (gameType == GameTypes.Crazy.getValue()) {
//            gameModel = new GameCrazyModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GamePuzzleState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//
//
//        } else if (gameType == GameTypes.Rank.getValue()) {
//            gameModel = new GameRankModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameRankState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//        } else if (gameType == GameTypes.Classic.getValue()) {
//            gameModel = new GameClassicModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameClassicState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);

        } else {

        }
        return gameModel;
    }

    public GameModel createGameModelAndExchangePhase(int gameType, String userName, int betAmt) {
        GameModel gameModel = null;
        IdWorker worker = new IdWorker(0, 0);
        String gameInstanceId = String.valueOf(worker.nextId());
        if (gameType == Integer.valueOf(GameId.GAME_105.getCode())) {
            gameModel = new GameEliminateModel(gameInstanceId);
            gameModel.setUserName(userName);
            GamePhaseModel gamePhaseModel = new ExchangePhaseModel(gameInstanceId, GameCommonState.Exchange
                    .getValue(), 1)
                    .setPhaseData
                            (new ExchangePhaseData().setAmt(betAmt));
            gameModel.addOrUpdatePhase(gamePhaseModel);
//        } else if (gameType == GameTypes.Mission.getValue()) {

//        } else if (gameType == Integer.valueOf(GameId.GAME_104.getCode())) {
//            gameModel = new GamePuzzleModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GamePuzzleState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//
//        } else {

        }
        return gameModel;
    }


//    public GameModel getFromCacheOrCreate(int gameType, String userName, int betAmt) {
//        GameModel gameModel = storeManager.getGameModelFromCache(userName);
//        if (gameModel == null) {
//            gameModel = createGameModelAndBetPhase(gameType, userName, betAmt);
//            storeManager.storeGameModel(userName, gameModel);
//        }
//        return gameModel;
//    }

//    public void clearGameModelFromCache(String userName) {
//        storeManager.storeGameModel(userName, null);
//    }
}
