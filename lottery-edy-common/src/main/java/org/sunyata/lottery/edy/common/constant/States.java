/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 张雷 on 2018/2/23.
 *
 * @author Zhanglei
 * @date 2018/02/23
 */
public class States {
    public static final String INIT = "Init";
    public static final String START_GAME = "StartGame";
    public static final String END_GAME = "EndGame";

    public static boolean isInit(String state) {
        return INIT.equalsIgnoreCase(state);
    }

    public static boolean isEndGame(String state) {
        return END_GAME.equalsIgnoreCase(state);
    }

    // 欢乐斗地主.常规赛
    public static class Game101 extends Stages {
        public static final String FIRST_WAGER_FINISH = "FirstWager.Finish";
        public static final String TIMES_WAGER_FINISH = "TimesWager.Finish";
        public static final String PLAY_CARDS_OVER_FINISH = "PlayCardsOver.Finish";
        public static final String LUCKY_DRAW_FINISH = "LuckyDraw.Finish";

        public static List<String> getStates() {
            return Arrays.asList(INIT, START_GAME, FIRST_WAGER_FINISH, TIMES_WAGER_FINISH, PLAY_CARDS_OVER_FINISH,
                LUCKY_DRAW_FINISH, END_GAME);
        }
    }
}
