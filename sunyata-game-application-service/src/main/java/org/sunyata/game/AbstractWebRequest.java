package org.sunyata.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.sunyata.core.json.Json;
import org.sunyata.lottery.edy.common.constant.Stages;
import org.sunyata.lottery.edy.common.vo.*;
import reactor.core.publisher.Mono;

/**
 * @author leo
 */

public abstract class AbstractWebRequest implements WebRequest {
    Logger logger = LoggerFactory.getLogger(AbstractWebRequest.class);

    @Autowired
    ApplicationServerConfigProperties serverConfigProperties;

    @Override
    public StartGameResponse startGame(String channelId, String accountId, String gameId, GameData gameData) throws
            Exception {
        StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.init(channelId, accountId, gameId, null, Stages.START_GAME, gameData);
        String baseUrl = getLotteryEngineBaseUrl(gameId);
        WebClient webClient = getWebClient(baseUrl);
        ClientResponse clientResponse = webClient.post()
                .uri("/startGame")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(startGameRequest), StartGameRequest.class).exchange().block();
        webClient.post()
                .uri("/startGame")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).exchange().flatMap(p -> p.toEntity(StartGameResponse.class));
        if (clientResponse.statusCode().is2xxSuccessful()) {
            Mono<StartGameResponse> startGameResponseMono = clientResponse.bodyToMono(StartGameResponse.class);
            StartGameResponse block1 = startGameResponseMono.block();
            printJson("startGame", startGameRequest);
            printJson("startGame", block1);
            logger.info("request:{},response:{}", startGameRequest, block1);
            return block1;
        }
        String errorMessage = clientResponse.bodyToMono(String.class).block();
        throw new Exception(errorMessage);
    }

    public void printJson(String title, Object obj) {
        System.out.println(title + ":");
        System.out.println(Json.encodePrettily(obj));
    }

    @Override
    public WagerResponse wager(String channelId, String accountId, String gameId, String gameCycleId, String stageId,
                               GameData gameData) {
        WagerRequest wagerGameRequest = new WagerRequest();
        wagerGameRequest.init(channelId, accountId, gameId, gameCycleId, stageId, gameData);
        String baseUrl = getLotteryEngineBaseUrl(gameId);
        WebClient webClient = getWebClient(baseUrl);
        ClientResponse clientResponse = webClient.post()
                .uri("/wager")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(wagerGameRequest), WagerRequest.class).exchange().block();
        Mono<WagerResponse> wagerResponseMono = clientResponse.bodyToMono(WagerResponse.class);
        WagerResponse block = wagerResponseMono.block();
        printJson("wager", wagerGameRequest);
        printJson("wager", block);
        logger.info("request:{},response:{}", wagerGameRequest, block);
        return block;
    }

    @Override
    public WagerResponse regularRaiseWager(String channelId, String accountId, int gameId, String gameCycleId, int
            times, String wagerId) {
        WagerRequest wagerGameRequest = new WagerRequest();
        wagerGameRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, Stages.Game101.TIMES_WAGER);
        wagerGameRequest.getGameData().set("wagerMultiple", times);
        wagerGameRequest.getGameData().set("wagerId", wagerId);
        String baseUrl = getLotteryEngineBaseUrl(String.valueOf(gameId));
        WebClient webClient = getWebClient(baseUrl);

        ClientResponse clientResponse = webClient.post()
                .uri("/wager")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(wagerGameRequest), WagerRequest.class).exchange().block();
        Mono<WagerResponse> wagerResponseMono = clientResponse.bodyToMono(WagerResponse.class);
        WagerResponse block = wagerResponseMono.block();
        printJson("raiseWager", wagerGameRequest);
        printJson("raiseWager", block);
        logger.info("request:{},response:{}", wagerGameRequest, block);
        return block;
    }

    @Override
    public CheckPointResponse checkPoint(String channelId, String accountId, String gameId, String gameCycleId,
                                         String stageId) {
        CheckPointRequest checkPointRequest = new CheckPointRequest();
        checkPointRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, stageId);
        ClientResponse clientResponse = null;
        String baseUrl = getLotteryEngineBaseUrl(String.valueOf(gameId));
        WebClient webClient = getWebClient(baseUrl);

        clientResponse = webClient.post()
                .uri("/checkPoint")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(checkPointRequest), CheckPointRequest.class).exchange().block();
//
//        Mono<ClientResponse> exchange = webClient.post()
//                .uri("/checkPoint")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(checkPointRequest), CheckPointRequest.class).exchange();
//        HttpStatus httpStatus = clientResponse.statusCode();
//
//        httpStatus.value();
//        httpStatus.getReasonPhrase()
        Mono<CheckPointResponse> checkPointResponseMono = clientResponse.bodyToMono(CheckPointResponse.class);
        CheckPointResponse block = checkPointResponseMono.block();
        printJson("checkPoint", checkPointRequest);
        printJson("checkPoint", block);
        logger.info("request:{},response:{}", checkPointRequest, block);
        return block;
    }

    @Override
    public RoundResultResponse sendRoundResult(String channelId, String accountId, String gameId, String gameCycleId,
                                               boolean result) {

        RoundResultRequest checkPointRequest = new RoundResultRequest();
        checkPointRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, Stages
                .Game101.PLAY_CARDS_OVER);
        WebClient webClient = getInteractWebClient(gameId);

        ClientResponse clientResponse = webClient.post()
                .uri("/sendResults")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(checkPointRequest), RoundResultRequest.class).exchange().block();
        Mono<RoundResultResponse> roundResultResponseMono = clientResponse.bodyToMono(RoundResultResponse.class);
        RoundResultResponse block = roundResultResponseMono.block();
        printJson("sendRoundResult", checkPointRequest);
        printJson("sendRoundResult", block);
        logger.info("request:{},response:{}", checkPointRequest, block);
        return block;
    }

    @Override
    public EndGameResponse endGame(String channelId, String accountId, String gameId, String gameCycleId, String
            stageId) {
        EndGameRequest endGameRequest = new EndGameRequest();
        endGameRequest.init(channelId, accountId, String.valueOf(gameId), gameCycleId, stageId);
        WebClient webClient = getWebClient(gameId);

        ClientResponse clientResponse = webClient.post()
                .uri("/endGame")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(endGameRequest), EndGameRequest.class).exchange().block();
        Mono<EndGameResponse> roundResultResponseMono = clientResponse.bodyToMono(EndGameResponse.class);
        EndGameResponse block = roundResultResponseMono.block();
        if (!block.isSuccess()) {
            logger.error("发送游戏结束消息时失败,{}", block);
        }
        printJson("endGame", endGameRequest);
        printJson("endGame", block);
        logger.info("request:{},response:{}", endGameRequest, block);
        return block;
    }

    public static WebClient webClient;

    public static WebClient interactWebClient;

    public WebClient getWebClient(String baseUrl) {
        logger.info("engineUrl:{}", baseUrl);
        if (webClient == null) {
            webClient = WebClient.builder().baseUrl(baseUrl).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType
                    .APPLICATION_JSON_VALUE)
                    .build();
        }
        return webClient;
    }

    public WebClient getInteractWebClient(String gameId) {
        if (interactWebClient == null) {
            interactWebClient = WebClient.builder().baseUrl(getInteractBaseUrl(gameId)).defaultHeader(HttpHeaders
                            .CONTENT_TYPE,
                    MediaType
                            .APPLICATION_JSON_VALUE)
                    .build();
        }
        return interactWebClient;
    }

    public String getLotteryEngineBaseUrl(String gameId) {
        String lotteryEngineUrl = serverConfigProperties.getLotteryEngineUrl();
        String url = String.format(lotteryEngineUrl, gameId);
        return url;
    }

    public String getInteractBaseUrl(String gameId) {
        String lotteryEngineUrl = serverConfigProperties.getLotteryInteractUrl();
        String url = String.format(lotteryEngineUrl, gameId);
        return url;
    }
}
