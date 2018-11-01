package org.sunyata.game.edy.mock.engine;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.sunyata.core.json.Json;
import org.sunyata.core.util.RandomUtils;
import org.sunyata.game.contract.GameIds;
import org.sunyata.lottery.edy.common.constant.Constants;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.enums.GameId;
import org.sunyata.lottery.edy.common.enums.ReturnCode;
import org.sunyata.lottery.edy.common.vo.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Random;

/**
 * @author leo
 */
@RestController()
public class HomeHandler {

    @PostMapping("/game/play/startGame")
    public Mono<StartGameResponse> startGame(@Valid @RequestBody StartGameRequest request) {
        String gameId = String.valueOf(request.getGameId());
        StartGameResponse response = new StartGameResponse();
        response.setGameData(new GameData());
        //这一行是为了消除赛的问题
        if (String.valueOf(request.getGameId()).equalsIgnoreCase(GameId.GAME_105.getCode())) {
            response.getGameData().set(Constants.EliminateAccountCashPoints, 100000);
        }
        response.setReturnCode(ReturnCode.SUCCESS.getCode());
        String randomString = RandomUtils.createRandomString(20);
        response.setGameCycleId(randomString);
        printJson("startGame", request);
        printJson("startGame", response);
        logger.info("startGame");
        logger.info("request:{}", Json.encode(request));
        logger.info("response:{}", Json.encode(response));
        return Mono.just(response);
        //return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).
    }

    Logger logger = LoggerFactory.getLogger(HomeHandler.class);
    Random random = new Random();

    static HashMap<String, String> centerIdmap = new HashMap<>();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    static HashMap<String, Long> points = new HashMap<>();
    static HashMap<String, Long> wangzha = new HashMap<>();

    @PostMapping("/game/play/wager")
    public Mono<WagerResponse> wager(@Valid @RequestBody WagerRequest request) {
        try {
            String gameCycleId = request.getGameCycleId();
            String gameId = String.valueOf(request.getGameId());
            GameData gameData = request.getGameData() == null ? new GameData() : (GameData) request.getGameData()
                    .clone();
            WagerResponse response = new WagerResponse();
            response.init(gameCycleId, request.getMessageId());
            response.setGameData(new GameData());
            if (gameId.equalsIgnoreCase(GameIds.Eliminate)) {
                String prizeId = "10501";
                int i = nextInt(1, 20);
                if (i == 1) {
                    //累积大奖
                    prizeId = "1050201";
                    response.setSymbolDistributionValue(Constants.EliminateIsHitJackpot, true);
                    response.getGameData().set(Constants.JsonKeyPrizeAmount, 1000000);
                } else {
                    int prizeIdRandom = nextInt(10, 20);
                    prizeId = prizeId + "16";
                }
                //
//                prizeId = "1050201";
//                response.setSymbolDistributionValue(Constants.EliminateIsHitJackpot, true);
//                response.getGameData().set(Constants.JsonKeyPrizeAmount, 1000000);
                //

                boolean isOpenBox = true;
                gameData.set(Constants.JsonKeyPrizeId, prizeId);
                gameData.set(Constants.EliminateIsOpenBox, isOpenBox);
                long accountCashPoints = points.containsKey(gameCycleId) ? points.get(gameCycleId) : 10000L;

                response.getGameData().set(Constants.EliminateAccountCashPoints, accountCashPoints);
                response.getGameData().set(Constants.EliminateAccountTotalPoints, 0);
                response.getGameData().set(Constants.EliminateAccountPrizePoints, 600);

                points.put(gameCycleId, accountCashPoints + 600);

            } else if (gameId.equalsIgnoreCase(GameIds.Regular)) {
                String centerId = centerIdmap.getOrDefault(request.getGameCycleId(), null);
                String prizeId = "1010102";
                gameData.set("prizeId", prizeId).set("centerId", centerId);
            }
            GetSymbolResponse symbols = getSymbols(request.getChannelId(), request.getAccountId(),
                    request.getGameId(), request.getGameCycleId(), request.getStageId(), gameData);
            if (symbols.isSuccess()) {
                String saveCenerId = symbols.getSymbolDistributionStringValue("centerId");
                if (!StringUtils.isEmpty(saveCenerId)) {
                    centerIdmap.put(request.getGameCycleId(), saveCenerId);
                }
            }
            GameData symbolsGameData = symbols.getGameData();
            if (symbolsGameData != null) {
                for (String key : symbolsGameData.keySet()) {
                    if (key.equalsIgnoreCase("symbolDistribution")) {
                        if (!response.getGameData().containsKey(key)) {
                            response.getGameData().put(key, new GameData());
                        }
                        mergeHashMap((HashMap<String, Object>) symbolsGameData.get(key), (HashMap<String, Object>)
                                response.getGameData().get(key));
                    } else {
                        response.getGameData().set(key, symbolsGameData.get(key));
                    }
                }
            }

            if (gameId.equalsIgnoreCase(GameIds.Eliminate)) {
                long wangZhaNums = wangzha.containsKey(gameCycleId) ? wangzha.get(gameCycleId) : 0L;
                response.setSymbolDistributionValue(Constants.EliminateopendBoxCount, String.valueOf(wangZhaNums));
                wangzha.put(gameCycleId, wangZhaNums + 1);
            }
            response.setReturnCode(ReturnCode.SUCCESS.getCode());
            printJson("wager", request);
            printJson("wager", response);
            logger.info("request:{}", Json.encode(request));
            logger.info("response:{}", Json.encode(response));
            return Mono.just(response);
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void mergeHashMap(HashMap<String, Object> source, HashMap<String, Object> target) {
        for (String key : source.keySet()) {
            if (!target.containsKey(key)) {
                target.put(key, source.get(key));
            }
        }
    }

    public void printJson(String title, Object obj) {
        System.out.println(title + ":");
        System.out.println(Json.encodePrettily(obj));
    }

    @PostMapping("/game/play/checkPoint")
    public Mono<CheckPointResponse> checkPoint(@Valid @RequestBody CheckPointRequest request) {
        CheckPointResponse response = new CheckPointResponse();
        String gameCycleId = request.getGameCycleId();
        String gameId = String.valueOf(request.getGameId());
        String centerId = centerIdmap.getOrDefault(request.getGameCycleId(), null);
        String prizeId = "1010102";
        response.init(gameCycleId, request.getMessageId());
        response.setGameData(new GameData());
        try {

            if (gameId.equalsIgnoreCase(GameId.GAME_101.getCode())) {
                if (request.getStageId().contains(Constants.STAGE_PLAY_CARDS_OVER)) {
                    GetSymbolResponse symbols = getRoundResult(request.getChannelId(), request.getAccountId(),
                            request.getGameId(), request.getGameCycleId());
                    if (symbols.isSuccess()) {
//                        Integer isWin = symbols.getGameData().getInteger(Constants.JsonKeyRoundResultIsWin);
//                        Integer bombNumbers = symbols.getGameData().getInteger(Constants.JsonKeyRoundBombNumbers);
                        response.getGameData().set(Constants.JsonKeyDrawFlag, false).set("prizeId", "1010105").set
                                ("prizeName", "四等奖B").set("prizeAmount", 0.0);
                        response.setSymbolDistributionValue(Constants.JsonKeyDrawFlag, false);
                        response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    } else {
                        response.setReturnCode(ReturnCode.FAIL);
                    }
                } else if (request.getStageId().contains(Constants.STAGE_LUCK_DRAW)) {
                    response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    response.getGameData().set("prizeId", "1010201").set
                            ("prizeName", "幸运大奖").set("prizeAmount", 300000);
                } else {
                    response.setReturnCode(ReturnCode.SUCCESS);
                }
            } else if (gameId.equalsIgnoreCase(GameId.GAME_105.getCode())) {
                if (request.getStageId().contains(Constants.STAGE_PLAY_CARDS_OVER)) {
                    GetSymbolResponse symbols = getRoundResult(request.getChannelId(), request.getAccountId(),
                            request.getGameId(), request.getGameCycleId());
                    if (symbols.isSuccess()) {
                        response.getGameData().set(Constants.JsonKeyDrawFlag, true).set("prizeId", "1010105").set
                                ("prizeName", "四等奖B").set("prizeAmount", 1);
                        response.setSymbolDistributionValue(Constants.JsonKeyDrawFlag, true);
                        response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    } else {
                        response.setReturnCode(ReturnCode.FAIL);
                    }
                } else if (Stages.Game105.CASH_DRAW.contains(request.getStageId())) {
                    response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    response.getGameData().set("prizeId", "1050302").set
                            ("prizeName", "奖金关固定奖").set("prizeAmount", 56.7);
                } else {
                    response.setReturnCode(ReturnCode.SUCCESS);
                }
            } else if (gameId.equalsIgnoreCase(GameId.GAME_107.getCode())) {
                if (request.getStageId().contains(Constants.STAGE_PLAY_CARDS_OVER)) {
                    GetSymbolResponse symbols = getRoundResult(request.getChannelId(), request.getAccountId(),
                            request.getGameId(), request.getGameCycleId());
                    if (symbols.isSuccess()) {
//                        Integer isWin = symbols.getGameData().getInteger(Constants.JsonKeyRoundResultIsWin);
//                        Integer bombNumbers = symbols.getGameData().getInteger(Constants.JsonKeyRoundBombNumbers);
                        response.getGameData().set("prizeId", "1010105").set("prizeName", "四等奖B").set("prizeAmount",
                                0.0);
                        response.setSymbolDistributionValue(Constants.JsonKeyDrawFlag, false);
                        response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    } else {
                        response.setReturnCode(ReturnCode.FAIL);
                    }
                } else if (request.getStageId().contains(Constants.STAGE_LUCK_DRAW)) {
                    response.setReturnCode(ReturnCode.SUCCESS.getCode());
                    response.getGameData().set("prizeId", "1010201").set
                            ("prizeName", "幸运大奖").set("prizeAmount", 300000);
                } else {
                    response.setReturnCode(ReturnCode.SUCCESS);
                }
            }
            printJson("checkPoint", request);
            printJson("checkPoint", response);
            logger.info("request:{}", Json.encode(request));
            logger.info("response:{}", Json.encode(response));
            return Mono.just(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public GetSymbolResponse getRoundResult(String channelId, String accountId, int gameId, String gameCycleId) {
        GetSymbolRequest wagerGameRequest = new GetSymbolRequest();
//        Stages.Game101.PLAY_CARDS_OVER
        wagerGameRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, "WAGER.PlayCardsOver");
        String baseUrl = getBaseUrl(gameId);
        WebClient webClient = getWebClient(baseUrl);
        ClientResponse clientResponse = webClient.post()
                .uri("/symbol")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(wagerGameRequest), GetSymbolRequest.class).exchange().block();
        Mono<GetSymbolResponse> wagerResponseMono = clientResponse.bodyToMono(GetSymbolResponse.class);
        GetSymbolResponse block = wagerResponseMono.block();
//        printJson("getRoundResult", wagerGameRequest);
//        printJson("getRoundResult", block);
//        logger.info("request:{},response:{}", Json.encode(wagerGameRequest), Json.encode(block));
        return block;
    }

    public GetSymbolResponse getSymbols(String channelId, String accountId, int gameId, String gameCycleId, String
            stageId, GameData gameData) {
        GetSymbolRequest wagerGameRequest = new GetSymbolRequest();
        wagerGameRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, stageId);
        for (String key : gameData.keySet()) {
            if (key.equalsIgnoreCase("centerId")) {
                wagerGameRequest.setSymbolDistributionValue("centerId", gameData.getString(key));
            } else if (key.equalsIgnoreCase(Constants.EliminateIsOpenBox)) {
                wagerGameRequest.setSymbolDistributionValue(Constants.EliminateIsOpenBox, gameData.getString(key));
            } else {
                wagerGameRequest.getGameData().set(key, gameData.getString(key));
            }
        }
        String baseUrl = getBaseUrl(gameId);
        WebClient webClient = getWebClient(baseUrl);
        ClientResponse clientResponse = webClient.post()
                .uri("/symbol")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(wagerGameRequest), GetSymbolRequest.class).exchange().block();
        Mono<GetSymbolResponse> wagerResponseMono = clientResponse.bodyToMono(GetSymbolResponse.class);
        GetSymbolResponse block = wagerResponseMono.block();
//        printJson("getSymbols", wagerGameRequest);
//        printJson("getSymbols", block);
//        logger.info("getSymbols");
//        logger.info("request:{},response:{}", Json.encode(wagerGameRequest), Json.encode(block));
        return block;
    }

    @PostMapping("/game/play/endGame")
    public Mono<EndGameResponse> endGame(EndGameRequest request) {
        EndGameResponse response = new EndGameResponse();
        response.init(request.getGameCycleId(), request.getMessageId());
        response.setGameData(new GameData());
        response.setReturnCode(ReturnCode.SUCCESS.getCode());
        printJson("endGame", request);
        printJson("endGame", response);
        logger.info("request:{},response:{}", Json.encode(request), Json.encode(response));
        return Mono.just(response);
    }

    public static WebClient webClient;

    public WebClient getWebClient(String baseUrl) {
        if (webClient == null) {
            webClient = WebClient.builder().baseUrl(baseUrl).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType
                    .APPLICATION_JSON_VALUE)
                    .build();
        }
        return webClient;
    }

    @Autowired
    ApplicationServerConfigProperties serverConfigProperties;

    public String getBaseUrl(int gameId) {
        String lotteryEngineUrl = serverConfigProperties.getLotteryInteractUrl();
        String url = String.format(lotteryEngineUrl, gameId);
        return url;
    }
}