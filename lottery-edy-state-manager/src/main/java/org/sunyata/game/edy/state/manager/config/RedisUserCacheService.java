package org.sunyata.game.edy.state.manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.sunyata.game.service.UserCacheInfo;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;

/**
 * Created by leo on 17/11/2.
 */
@Component
public class RedisUserCacheService implements UserCacheService, UserLocationService {
    public static final String userLocationKeyPrefix = "user_location_key";
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RedisTemplate<String, Object> template;

    String generateUserKey(int gatewayServerId, int userSessionId) {
        return "user_location_server_session_id_" + String.valueOf(gatewayServerId) + "_" + String.valueOf
                (userSessionId);
    }

    String generateUserKey(Integer userId) {
        return "user_location_user_id" + userId;
    }

    String generateUserInfoKey(UserCacheInfo userCacheInfo) {
        return "user_info_user_id_" + userCacheInfo.getChannelId() + "_" + userCacheInfo.getAccountId();
    }

    String generateUserInfoKey(UserLocationInfo userCacheInfo) {
        return "user_info_user_id_" + userCacheInfo.getChannelId() + "_" + userCacheInfo.getAccountId();
    }
//
//    public void cacheUserLocation(String userName, int gatewayServerId, int userId) {
//        UserLocationInfo info = new UserLocationInfo().setGatewayServerId(gatewayServerId).setUserIdInGateway(userId)
//                .setUserName
//                        (userName);
//
//        template.opsForHash().put(userLocationKeyPrefix, generateUserKey(gatewayServerId, userId), info);
//        template.opsForHash().put(userLocationKeyPrefix, generateUserKey(userName), info);
//    }

    @Override
    public UserLocationInfo getUserLocationInfo(Integer userId) {
        Object o = template.opsForHash().get(userLocationKeyPrefix, generateUserKey(userId));
        if (o != null) {
            return (UserLocationInfo) o;
        }
        return null;
    }

    @Override
    public UserLocationInfo getUserLocationInfo(int serverId, int userIdInGateway) {
        Object o = template.opsForHash().get(userLocationKeyPrefix, generateUserKey(serverId, userIdInGateway));
        if (o != null) {
            return (UserLocationInfo) o;
        }
        return null;
    }

//    @Override
//    public UserCacheInfo getUserInfo(int userId) {
//        Object o = template.opsForHash().get(userLocationKeyPrefix, generateUserInfoKey(userId));
//        return (UserCacheInfo) o;
//    }

    @Override
    public void cacheUserInfo(UserCacheInfo userCacheInfo) {
        template.opsForHash().put(userLocationKeyPrefix, generateUserInfoKey(userCacheInfo),
                userCacheInfo);
    }

    @Override
    public void removeCacheUserInfo(int gatewayServerId, int userInGatewayId) {
        UserLocationInfo userLocationInfo = getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo != null) {
            template.opsForHash().delete(userLocationKeyPrefix, generateUserInfoKey(userLocationInfo));
        }
    }

    @Override
    public void cacheUserLocation(UserLocationInfo userLocationInfo) {
        template.opsForHash().put(userLocationKeyPrefix, generateUserKey(userLocationInfo.getGatewayServerId(),
                userLocationInfo.getUserIdInGateway()), userLocationInfo);
        template.opsForHash().put(userLocationKeyPrefix, generateUserInfoKey(userLocationInfo), userLocationInfo);
    }

    @Override
    public void removeCacheUserLocation(int gatewayServerId, int userInGatewayId) {
        UserLocationInfo userLocationInfo = getUserLocationInfo(gatewayServerId, userInGatewayId);
        if (userLocationInfo != null) {
            template.opsForHash().delete(userLocationKeyPrefix, generateUserKey(gatewayServerId, userInGatewayId));
            template.opsForHash().delete(userLocationKeyPrefix, generateUserInfoKey(userLocationInfo));
        }
    }
}
