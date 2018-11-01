package client;

import org.sunyata.game.server.OctopusRequest;
import org.sunyata.game.server.OctopusResponse;
import org.sunyata.game.server.Session;
import org.sunyata.game.server.OctopusMsgHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class TestClientHandler implements OctopusMsgHandler<Object> {
    private final static Logger log = LoggerFactory.getLogger(TestClientHandler.class);


    @Override
    public void onConnect(Session<Object> session) throws Exception {
        log.info("连接boss服务器成功！");

    }

    @Override
    public void onDisconnect(Session<Object> session) throws Exception {
        log.info("boss服务器断开！");
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

    @Override
    public void onMessage(OctopusRequest request, OctopusResponse response) throws Exception {
        log.info(String.valueOf(request.getMessage().getCmd()));
    }
}
