package org.sunyata.game.business;

import org.springframework.stereotype.Component;
import org.sunyata.game.currency.models.FundAccountType;

/**
 * Created by leo on 17/11/20.
 */
@Component
public class FundLockKeys {
    public String getFundLockKey(String account, FundAccountType accountType) {
        return "fund_" + account + "_" + accountType.getValue();
    }

    public String getFundLockKey(String account) {
        //return new ReentrantZkLock2(baseLockPath + "/" + "fund_" + account, getZkSessionManager());
        return "fund_" + account;
    }


}
