<<<<<<< HEAD
# sunyata-game
         
interact->engine:
    Game101:
        StartGame
            startGameRequest:
            {
             "messageId" : "20180412155510604",
             "timeStamp" : 1523519710,
             "channelId" : "105",
             "accountId" : "aaa",
             "gameCycleId" : null,
             "stageId" : "StartGame",
             "gameData" : { },
             "version" : "1.0",
             "settings" : null,
             "gameId" : "101"
            }
            startGameResponse:
            {
             "returnCode" : "000000",
             "returnMsg" : null,
             "messageId" : null,
             "timeStamp" : null,
             "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
             "stageId" : null,
             "gameData" : { },
             "hasUnfinishedGameCycle" : false,
             "success" : true
            } 
        WAGER.FirstWager:            
            wagerReqeust:
            {
              "messageId" : "20180412155510905",
              "timeStamp" : 1523519710,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : "WAGER.FirstWager",
              "gameData" : {
                "centerId" : null,
                "wagerId" : "10101",
                "wagerAmount" : "1",
                "prizeId" : "1010102"
              },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "101"
            }
            wagerResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412155510925",
              "timeStamp" : 1523519710,
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : null,
              "gameData" : {
                "symbolDistribution" : {
                  "centerId" : "2306",
                  "center" : "26,40,27,15,29,42,30,31,45,19,20,47,34,23,10,38,51"
                }
              },
              "success" : true
            }
        WAGER.TimesWager:            
            wagerRequest:
            {
              "messageId" : "20180412155903027",
              "timeStamp" : 1523519943,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : "WAGER.TimesWager",
              "gameData" : {
                "centerId" : "2306",
                "wagerId" : "10101",
                "wagerMultiple" : 3,
                "prizeId" : "1010102"
              },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "101"
            }
            wagerResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412155903035",
              "timeStamp" : 1523519943,
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : null,
              "gameData" : {
                "symbolDistribution" : {
                  "centerId" : "2306",
                  "left" : "39,16,3,43,17,5,6,33,8,48,35,9,22,36,11,50,25",
                  "cardId" : "11528",
                  "center" : "26,40,27,15,29,42,30,31,45,19,20,47,34,23,10,38,51",
                  "right" : "13,0,1,14,2,41,4,44,18,7,46,21,49,37,24,52,53",
                  "under" : "12,32,28"
                }
              },
              "success" : true
            }
        WAGER.PlayCardsOver   
            checkPointRequest:
            {
              "messageId" : "20180412160220053",
              "timeStamp" : 1523520140,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : "WAGER.PlayCardsOver",
              "gameData" : { },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "101"
            }
            checkPointResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412160220067",
              "timeStamp" : 1523520140,
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : null,
              "gameData" : {
                "drawFlag" : "1",
                "prizeAmount" : 1,
                "prizeName" : "四等奖B",
                "symbolDistribution" : {
                  "drawFlag" : "1"
                },
                "prizeId" : "1010105"
              },
              "success" : true
            }
        WAGER.LuckyDraw          
            checkPoint:
            {
              "messageId" : "20180412160411220",
              "timeStamp" : 1523520251,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : "WAGER.LuckyDraw",
              "gameData" : { },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "101"
            }
            checkPoint:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412160411225",
              "timeStamp" : 1523520251,
              "gameCycleId" : "Ctp8kXhIEqutmbaC8of8",
              "stageId" : null,
              "gameData" : {
                "prizeAmount" : 300000,
                "prizeName" : "幸运大奖",
                "prizeId" : "1010201"
              },
              "success" : true
            }  
    Game105:
        StartGame
            startGameRequest:
            {
              "messageId" : "20180412160714319",
              "timeStamp" : 1523520434,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : null,
              "stageId" : "StartGame",
              "gameData" : {
                "initCash" : 1000000,
                "initCashPoint" : 100000000
              },
              "version" : "1.0",
              "settings" : null,
              "gameId" : "105"
            }
            startGameResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : null,
              "timeStamp" : null,
              "gameCycleId" : "dE8YhXs0sWZhxsd6bOJK",
              "stageId" : null,
              "gameData" : {
                "accountCashPoints" : 100000
              },
              "hasUnfinishedGameCycle" : false,
              "success" : true
            }
        WAGER.PointWager-1
            wagerRequest:
            {
              "messageId" : "20180412160812480",
              "timeStamp" : 1523520492,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : "dE8YhXs0sWZhxsd6bOJK",
              "stageId" : "WAGER.PointWager-1",
              "gameData" : {
                "isOpenBox" : true,
                "wagerId" : "10501",
                "wagerAmount" : "dE8YhXs0sWZhxsd6bOJK",
                "prizeId" : "1050116"
              },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "105"
            }
            wagerResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412160812501",
              "timeStamp" : 1523520492,
              "gameCycleId" : "dE8YhXs0sWZhxsd6bOJK",
              "stageId" : null,
              "gameData" : {
                "accountPrizePoints" : 600,
                "accountCashPoints" : 10000,
                "symbolDistribution" : {
                  "cards" : [ [ 4, 5, 8, 9, 15, 16, 17, 22, 23, 25, 27, 28, 30, 32, 34, 42, 46, 48, 50, 51 ], [ 4, 5, 8, 9, 15, 16, 23, 27, 32, 46, 50 ], [ 11, 14, 15, 23, 24, 26, 31, 32, 44, 45, 52 ], [ 14, 15, 17, 26, 31, 32, 42 ], [ 6, 7, 9, 16, 20, 29, 46 ], [ 6, 7, 9, 11, 16, 23, 28, 30, 34, 44 ], [ 5, 11, 14, 16, 18, 23, 31, 32, 43, 47 ], [ 5, 11, 16, 20, 22, 23, 32, 43, 47 ], [ 0, 16, 19, 20, 32, 34, 39, 40, 44 ], [ 0, 14, 18, 19, 31, 32, 39, 40, 44, 45 ], [ 3, 9, 10, 18, 26, 30, 32, 38, 39, 53 ], [ 52, 53 ], [ 2, 21 ], [ 2, 3, 9, 10, 18, 20, 21, 24, 30, 32 ], [ 4, 7, 9, 17, 27, 28, 32, 33, 50, 52 ] ],
                  "opendBoxCount" : "0"
                },
                "accountTotalPoints" : 0
              },
              "success" : true
            }  
        WAGER.PointWagerPlayerOver-1
            checkPointRequest:
            {
              "messageId" : "20180412160813047",
              "timeStamp" : 1523520493,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : null,
              "stageId" : "WAGER.PointWagerPlayerOver-1",
              "gameData" : { },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "105"
            }
            checkPointResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412160813059",
              "timeStamp" : 1523520493,
              "gameCycleId" : null,
              "stageId" : null,
              "gameData" : { },
              "success" : true
            }                  
        WAGER.CashDraw            
            checkPointRequest:
            {
              "messageId" : "20180412173143034",
              "timeStamp" : 1523525503,
              "channelId" : "105",
              "accountId" : "aaa",
              "gameCycleId" : null,
              "stageId" : "WAGER.CashDraw",
              "gameData" : { },
              "version" : "1.0",
              "startGame" : false,
              "gameId" : "105"
            }
            checkPointResponse:
            {
              "returnCode" : "000000",
              "returnMsg" : null,
              "messageId" : "20180412173143038",
              "timeStamp" : 1523525503,
              "gameCycleId" : null,
              "stageId" : null,
              "gameData" : {
                "prizeAmount" : 56.7,
                "prizeName" : "奖金关固定奖",
                "prizeId" : "1050302"
              },
              "success" : true
            }                                                                 
=======
# sunyata.game.lottery
>>>>>>> cf33b96b1542c9faaab4bfcfe9825a1be4312f63
