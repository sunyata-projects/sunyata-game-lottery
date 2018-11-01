/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.constant;

import java.math.BigDecimal;

/**
 * 运行中使用的各种常量值
 *
 * @author Zhanglei
 * @date 2017/10/31
 */
public class Constants {
    public static final BigDecimal BD0 = BigDecimal.ZERO;
    public static final BigDecimal BD1 = BigDecimal.ONE;
    public static final BigDecimal BD5 = new BigDecimal(5);
    public static final BigDecimal BD10 = BigDecimal.TEN;
    public static final BigDecimal BD100 = new BigDecimal(100);
    public static final BigDecimal BD1000 = new BigDecimal(1000);

    public static final String STAGE_WAGER = "Wager";
    public static final String STAGE_PLAY_CARDS_OVER = "PlayCardsOver";
    public static final String STAGE_LUCK_DRAW = "LuckyDraw";

    public static final String WagerId = "wagerId";
    public static final String WagerAmount = "wagerAmount";

    public static final String SymbolsUrl = "/gameCycle/symbol";



    //奖等信息
    public static final String JsonKeyPrizeId = "prizeId";
    public static final String JsonKeyPrizeName = "prizeName";
    public static final String JsonKeyPrizeAmount = "prizeAmount";
    public static final String JsonKeyPrizeContent = "prizeContent";

    //打牌结果

    public static final String JsonKeyRoundResultIsWin = "isWin";
    public static final String JsonKeyRoundBombNumbers = "bombNumbers";
    public static final String JsonKeyDrawFlag = "drawFlag";

    //消除赛 stargGame
    public static final String EliminateInitCash = "initCash";
    public static final String EliminateInitCashPoint = "initCashPoint";

    public static final String EliminateAccountTotalPoints = "accountTotalPoints";
    public static final String EliminateAccountCashPoints = "accountCashPoints";
    public static final String EliminateAccountPrizePoints = "accountPrizePoints";

    public static final String EliminateIsOpenBox = "isOpenBox";
    public static final String EliminateIsHitJackpot = "isHitJackpot";
    public static final String EliminateopendBoxCount = "opendBoxCount";
    public static final String EliminateCards = "cards";

}
