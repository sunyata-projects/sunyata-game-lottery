package org.sunyata.game.edy.interact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author leo
 */
@Component
public class RoundResultStoreService {
    public static final String userLocationKeyPrefix = "round_result_";
    Logger logger = LoggerFactory.getLogger(SymbolHandler.class);
    @Autowired
    RedisTemplate<String, Object> template;

    public void store(String gameCycleId, boolean result) {
        template.opsForHash().put(userLocationKeyPrefix, gameCycleId, new RoundResult().setResult(result));
    }

    public RoundResult getResult(String gameCycleId) {
        Object o = template.opsForHash().get(userLocationKeyPrefix, gameCycleId);
        if (o != null) {
            RoundResult o1 = (RoundResult) o;
//            return o1.getResult() == 1;
            return o1;
        }
        return null;
        //return false;

    }
}
