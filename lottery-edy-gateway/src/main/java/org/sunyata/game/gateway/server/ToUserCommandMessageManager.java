package org.sunyata.game.gateway.server;

import org.sunyata.game.MessageProcessor;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusPacketMessage;
import org.sunyata.game.server.message.OctopusToUserRawMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.gateway.SessionService;
import org.sunyata.game.gateway.User;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/11/8.
 */
@Component
public class ToUserCommandMessageManager implements MessageProcessor {
    Logger logger = LoggerFactory.getLogger(ToUserCommandMessageManager.class);

    @Autowired
    SessionService sessionService;

    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void handler(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        OctopusPacketMessage packetMessage = request.getMessage();
        OctopusToUserRawMessage rawMessage = (OctopusToUserRawMessage) packetMessage.getRawMessage();
        logger.info("网关->用户,CommandId:{},UserSessionId:{}", rawMessage.getCmd(), packetMessage.getDataId());
        User user = sessionService.getUser(packetMessage.getDataId());
        if (user != null) {
            if (packetMessage.getDataId() == 0) {
                logger.info("发关给客户端消息总量:{}", atomicInteger.incrementAndGet());
            }
            user.writeAndFlush(rawMessage);
        } else {
            logger.error("用户已离线:userSessionID:", packetMessage.getDataId());
        }
    }
}
