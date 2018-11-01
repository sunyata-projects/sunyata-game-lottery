package org.sunyata.game.service;

/**
 * Created by leo on 17/11/2.
 */
public interface UserLocationService {
    UserLocationInfo getUserLocationInfo(Integer userId);

    UserLocationInfo getUserLocationInfo(int serverId, int userIdInGateway);

    void cacheUserLocation(UserLocationInfo userLocationInfo);

    void removeCacheUserLocation(int gatewayServerId, int userInGatewayId);
}
