package org.sunyata.game.gateway.server;

import org.sunyata.game.server.OctopusRequest;
import org.sunyata.game.server.OctopusResponse;
import org.sunyata.game.server.Session;
import org.sunyata.game.server.OctopusMsgHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.gateway.GatewayService;
import org.sunyata.game.gateway.SessionService;
import org.sunyata.game.gateway.User;

import java.io.IOException;

@Component
public class WebSocketServerHandler implements OctopusMsgHandler<User> {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);
    @Autowired
    private SessionService sessionService;

    @Autowired
    GatewayService gatewayService;

    public WebSocketServerHandler() {
    }

    @Override
    public void onConnect(Session<User> session) throws Exception {
        log.info("收到Client连接!{}", session);
    }

    @Override
    public void onDisconnect(Session<User> session) throws Exception {

        if (session != null) {
            sessionService.unReg(session.get());
        }

        log.info("Client断开!{}", session);
    }


    @Override
    public void onException(Session<User> session, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            log.error("错误，断开玩家{},{}", session, cause.getMessage());
        } else {
            log.error("错误，断开玩家{}", session, cause);
        }
        session.channel.close();
    }

    @Override
    public Session<User> createSession(ChannelHandlerContext ctx) throws Exception {
        Session<User> session = sessionService.reg(ctx);
        return session;
    }

    @Override
    public boolean onIn(Session<User> session, ByteBuf in) throws Exception {
        return true;
    }


    @Override
    public void onMessage(OctopusRequest request, OctopusResponse response) throws Exception {
        log.info("开始处理消息:{}", request.getMessage().getCmd());
        gatewayService.handlerClient(request, response);
    }
}
