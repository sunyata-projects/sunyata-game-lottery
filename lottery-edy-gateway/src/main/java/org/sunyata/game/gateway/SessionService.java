package org.sunyata.game.gateway;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.server.Session;
import org.sunyata.game.server.message.LogoutJsonBodySerializer;
import org.sunyata.game.server.message.MessageFactory;
import org.sunyata.game.server.message.OctopusPacketMessage;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.service.ServerLocation;
import org.sunyata.lottery.edy.common.constant.Commands;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class SessionService {
    private final static Logger log = LoggerFactory.getLogger(SessionService.class);

    private final User[] userArray;
    /**
     * 用户注册和离开都需要这个锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    IdPool idPool;

    @Autowired
    AnyClientManager anyClientManager;
    @Autowired
    ServerLocation serverLocation;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    public SessionService() {
        //this.config = config;
        this.userArray = new User[1024];
    }

    public Session<User> reg(ChannelHandlerContext ctx) throws Exception {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            short id = idPool.generateId();
            User user = new User(id, ctx.channel());
            Session<User> session = new Session<>(ctx.channel());
            session.set(user);

            userArray[id] = user;
            log.info("注册Session:{}", id);
            return session;
        } finally {
            lock.unlock();
        }
    }

    public void unReg(User user) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (user != null) {
                short id = user.getId();
                //LogoutPxMsg msg = new LogoutPxMsg(id);
                //gatewayService.toBoss(msg);
                idPool.add(id);
                userArray[id] = null;
                ClientServerInfo randomInnerGatewayServerInfo = serverLocation.getRandomInnerGatewayServerInfo();
                LogoutJsonBodySerializer logoutJsonBodySerializer = new LogoutJsonBodySerializer().setGatewayServerId
                        (serverConfigProperties.getServerId())
                        .setUserInGatewayId(id);
                OctopusPacketMessage toSystemPacketJsonBodyMessage = MessageFactory
                        .createToSystemPacketJsonBodyMessage(id, serverConfigProperties.getServerId(), Commands
                                .Logout, logoutJsonBodySerializer);
                try {
                    anyClientManager.forwardMessage(randomInnerGatewayServerInfo, toSystemPacketJsonBodyMessage);
                } catch (Exception e) {
                    log.error("注销时发生错误:{}", e);
                }
                log.info("注销Session:{}", id);
            }
        } finally {
            lock.unlock();
        }
    }

    public User getUser(int sessionId) {
        if (sessionId > -1 && sessionId < userArray.length) {
            return userArray[sessionId];
        }
        return null;
    }
}
