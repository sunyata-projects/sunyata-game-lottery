package org.sunyata.game.service;

/**
 * Created by leo on 17/11/2.
 */
public interface UserCacheService {
//    UserCacheInfo getUserInfo(int userId);

    void cacheUserInfo(UserCacheInfo userEntity);

    void removeCacheUserInfo(int gatewayServerId, int userInGatewayId);

}
