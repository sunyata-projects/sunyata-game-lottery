/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.constant;

/**
 * Process phase definition of each game
 *
 * @author Zhanglei
 * @date 2017/04/10
 */
public class Stages {
    public static final String INIT = "Init";
    public static final String START_GAME = "StartGame";
    public static final String END_GAME = "EndGame";

    // 欢乐斗地主.常规赛
    public static class Game101 {
        public static final String FIRST_WAGER = "WAGER.FirstWager";
        public static final String TIMES_WAGER = "WAGER.TimesWager";
        public static final String PLAY_CARDS_OVER = "WAGER.PlayCardsOver";
        public static final String LUCKY_DRAW = "WAGER.LuckyDraw";
    }

    // 欢乐斗地主.消除赛
    public static class Game105 {
        public static final String FIRST_WAGER = "WAGER.PointWager-%s";
        public static final String TIMES_WAGER = "TimesWager";
        public static final String PLAY_OVER = "WAGER.PointWagerPlayerOver-%s";
        public static final String CASH_DRAW = "WAGER.CashDraw";
        public static final String End_GAME = "EndGame";
    }

    // 欢乐斗地主.经典赛
    public static class Game107 {
        public static final String FIRST_WAGER = "FirstWager";
        public static final String TIMES_WAGER = "TimesWager";
        public static final String PLAY_CARDS_OVER = "WAGER.PlayCardsOver";
        public static final String LUCKY_DRAW = "LuckyDraw";
    }
}
