package org.sunyata.game.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ChannelHandler<T extends NetHandler<?>, M> extends SimpleChannelInboundHandler<M> {

    protected final static Logger log = LoggerFactory.getLogger(ChannelHandler.class);

    public static final AttributeKey<Session> SESSION = AttributeKey.valueOf("Octopus.ClientHandler.SESSION");
    private final NetHandler<?> messageHandler;

    public ChannelHandler(NetHandler<?> messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
        Channel channel = ctx.channel();
        if (log.isDebugEnabled()) {
            log.debug("连接成功{}", channel);
        }
        Session session = messageHandler.createSession(ctx);
        ctx.channel().attr(SESSION).set(session);
        messageHandler.onConnect(session);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
        if (log.isDebugEnabled()) {
            log.debug("连接断开{}", ctx);
        }
        messageHandler.onDisconnect(ctx.channel().attr(SESSION).get());
        ctx.channel().attr(SESSION).set(null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        messageHandler.onException(ctx.channel().attr(SESSION).get(), cause);
        ctx.close();
    }

}
