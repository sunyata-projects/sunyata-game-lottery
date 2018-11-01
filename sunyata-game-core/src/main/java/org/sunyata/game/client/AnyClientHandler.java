package org.sunyata.game.client;

import org.sunyata.game.server.OctopusRequest;
import org.sunyata.game.server.OctopusResponse;
import org.sunyata.game.server.Session;
import org.sunyata.game.server.OctopusMsgHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class AnyClientHandler implements OctopusMsgHandler<Object> {
    @Autowired
    AnyClientManager anyClientManager;
    private final static Logger log = LoggerFactory.getLogger(AnyClientHandler.class);

    //@Autowired
    //private GatewayService gatewayService;

    private Runnable connectCallback;

    private AnyClient client;

    public AnyClientHandler(Runnable connectCallback) {
        this.connectCallback = connectCallback;
    }

    @Override
    public void onConnect(Session<Object> session) throws Exception {
        log.info("连接服务器成功！");
        // gatewayService.onSceneConnect(client);
        connectCallback.run();
    }

    @Override
    public void onDisconnect(Session<Object> session) throws Exception {
        log.info("连接断开!！");
//        anyClientManager.onDisconnect(client.getServiceName(),client.getServerId());
        this.client.setTerminative(true);
        //gatewayService.onSceneDisconnect(client);
    }

    @Override
    public void onException(Session<Object> session, Throwable cause)
            throws Exception {
        log.info("错误!{}", session, cause);
    }

    @Override
    public Session<Object> createSession(ChannelHandlerContext ctx)
            throws Exception {
        return new Session<>(ctx.channel());
    }

    @Override
    public boolean onIn(Session<Object> session, ByteBuf in) throws Exception {
        return true;
    }

//    @Override
//    public void onMessage(PxMsg msg) throws Exception {
//        //gatewayService.handlerScene(msg);
//    }

    public void setClient(AnyClient client) {
        this.client = client;
    }

    @Override
    public void onMessage(OctopusRequest msg, OctopusResponse response) throws Exception {

    }
}
