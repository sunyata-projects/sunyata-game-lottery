/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.constant;

/**
 * Command name defination of Request
 *
 * @author Zhanglei
 * @date 2017/04/10
 */
public class Commands {
    public static final String START_GAME = "StartGame";
    //public final static String START_GAME_COMMAND = "10001";//
    public static final String WAGER = "Wager";
    public static final String GAMBLE = "Gamble";
    public static final String CHECKPOINT = "CheckPoint";
    public static final String END_GAME = "EndGame";
    //登录
    public static final String Login =      "10001";
    public final static String loginRet = "1000101";//登录信息下行
    //登录出
    public static final String Logout =     "10002";
    //个性化信息获取
    public static final String Profile =    "10003";
    public static final String ProfileRet =    "1000301";
    //踢人
    public static final String KickPlayer = "10004";
    //下注,人民币游戏的第一个命令
    public static final String Bet =             "10005";
    public static final String BetRet =             "1000501";

    //兑换点数
    public static final String Exchange =             "10006";
    public static final String ExchangeRet =             "1000601";

    public static final String EndGame =             "10099";
    public static final String EndGameRet =             "1009901";


    //通知玩家余额发生变化
    public static final String NotifyBalanceChanged =             "10007";

    //常规赛50
    //断线续玩
    public static final String RegularBreakPlay =       "50001";
    //发牌
    public static final String RegularDeal =            "50003";
    //加注
    public static final String RegularRaise =           "50004";
    public static final String RegularRaiseRet =           "5000401";
    //    //发暗牌及农民牌
//    public static final String RegularDark =            "50005";
    //出牌
    public static final String RegularPlay =            "50006";
    public static final String RegularPlayRet =            "5000601";

    //是否翻牌
    public static final String RegularGuessSize =            "50007";
    public static final String RegularGuessSizeRet =            "5000701";

    //结算
    public static final String RegularClear =           "50008";
    public static final String RegularClearRet =           "5000801";

    //闯关赛51

    //断线续玩
    public static final String MissionBreakPlay =       "51001";
    //发牌
    public static final String MissionDeal =            "51002";
    //出牌
    public static final String MissionPlay =            "51003";
    //结算
    public static final String MissionClear =           "51004";


    //拼图赛52
    //断线续玩
    public static final String PuzzleBreakPlay =       "52001";
    //发牌
    public static final String PuzzleDeal =            "52003";

    //结算
    public static final String PuzzleClear =           "52004";


    //消除53

    public static final String EliminateBreakPlay =       "53001";
    //点数下注
    public static final String EliminateGamePointBet =    "53003";
    public static final String EliminateGamePointBetRet =    "5300301";
    //发牌
    public static final String EliminateDeal =            "53004";
    //结算
    public static final String EliminateClear =           "53005";
    public static final String EliminateClearRet =           "5300501";

    //放弃游戏点数
    public static final String EliminateGameUpGamePoint =           "53006";


    //疯狂63
    public static final String CrazyBreakPlay =       "63001";
    //发牌
    public static final String CrazyDeal =            "63004";
    //拖拽
    public static final String CrazyDrag =            "63005";
    //拖拽结束
    public static final String CrazyDragOver =            "63006";
    //进入比倍
    public static final String CrazyEnterGuessSize =            "63007";
    //一次比倍
    public static final String CrazyGuessSize =            "63008";
    //结算
    public static final String CrazyClear =           "63009";

    //排名赛54

    //断线续玩
    public static final String RankBreakPlay =       "54001";
    //发牌
    public static final String RankDeal =            "54002";
    //出牌
    public static final String RankPlay =            "54003";
    //转盘
    public static final String RankTurn =            "54004";

    //每局结算
    public static final String RoundClear =            "54005";
    //游戏结算
    public static final String RankClear =           "54006";


    public static final String RankRemainTime = "54007";

    //请求排行榜 前三十名
    public static final String RankList = "54008";

    //历史排行
    public static final String RankHistoryList = "54009";


    //经典赛55

    //断线续玩
    public static final String ClassicBreakPlay =       "55001";
    //发牌
    public static final String ClassicDeal =            "55002";
    //出牌
    public static final String ClassicPlay =            "55003";
    //转盘
    public static final String ClassicTurn =            "55004";
    //进入比倍
    public static final String ClassicEnterGuessSize =            "55005";

    //一次比倍
    public static final String ClassicGuessSize =            "55006";

    //中间过程结算
    public static final String ClassicSummaryClear =            "55007";
    //游戏结算
    public static final String ClassicClear =           "55008";
}
