/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;

/**
 * 消息中的一些业务数据Key
 *
 * @author Zhanglei
 * @date 2017/10/31
 */
public class GameDataKey {
    public static final String CREATE_TIMESTAMP = "createTimestamp";
    public static final String UPDATE_TIMESTAMP = "updateTimestamp";
    
    public static final String LAST_STAGE_ID = "lastStageId";
    public static final String LAST_RETURN_CODE = "lastReturnCode";
    public static final String LAST_RETURN_MESSAGE = "lastReturnMsg";
    public static final String LAST_GAME_DATA = "lastGameData";

    public static final String STAGE_ID = "stageId";

    public static final String WAGER_ID = "wagerId";
    public static final String WAGER_AMOUNT = "wagerAmount";
    public static final String WAGER_MULTIPLE = "wagerMultiple";
    public static final String WAGER_CONTENT = "wagerContent";

    public static final String JACKPOT_ID = "jackpotId";
    public static final String JACKPOT_NAME = "jackpotName";
    public static final String JACKPOT_BALANCE = "jackpotBalance";

    public static final String SYMBOL_ID = "symbolId";
    public static final String SYMBOL_DISTRIBUTION = "symbolDistribution";

    public static final String PRIZE_ID = "prizeId";
    public static final String PRIZE_NAME = "prizeName";
    public static final String PRIZE_AMOUNT = "prizeAmount";
    public static final String PRIZE_CONTENT = "prizeContent";
}
