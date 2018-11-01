package org.sunyata.game.service;

import java.io.Serializable;

/**
 * Created by leo on 17/11/15.
 */
public class UserCacheInfo implements Serializable {
    private String accountId;
    private String channelId;

    public String getAccountId() {
        return accountId;
    }

    public UserCacheInfo setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public UserCacheInfo setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
}
