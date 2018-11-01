package org.sunyata.game;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.Session;
import org.sunyata.game.server.OctopusPacketMsgHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginListenHandler implements OctopusPacketMsgHandler<Client> {
    private static final Logger log = LoggerFactory.getLogger(LoginListenHandler.class);

    @Autowired
    private MessageProcessor messageManager;

    public LoginListenHandler() {
    }

    @Override
    public void onConnect(Session<Client> session) throws Exception {
        log.info("收到Scene连接!{}", session);
    }

    @Override
    public void onDisconnect(Session<Client> session) throws Exception {
        if (session != null) {
            //sceneManager.unreg(session.get());
        }
        log.info("Scene断开!{}", session);
    }


    @Override
    public void onException(Session<Client> session, Throwable cause) throws Exception {
        log.error("错误，Scene网关！{}", session, cause);
        session.channel.close();
    }

    @Override
    public Session<Client> createSession(ChannelHandlerContext ctx) throws Exception {
        return new Session<>(ctx.channel());
    }

    @Override
    public boolean onIn(Session<Client> session, ByteBuf in) throws Exception {
        return false;
    }


    @Override
    public void onMessage(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        messageManager.handler(request, response);

    }

//    public void setMessageManager(SceneMessageManager messageManager) {
//        this.messageManager = messageManager;
//    }
}
