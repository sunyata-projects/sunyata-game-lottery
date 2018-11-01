package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusRawMessage;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OctopusMsgChannelHandler extends ChannelHandler<OctopusMsgHandler<?>, OctopusRawMessage> {
    private OctopusMsgHandler<?> messageHandler;

    public OctopusMsgChannelHandler(OctopusMsgHandler<?> messageHandler) {
        super(messageHandler);
        this.messageHandler = messageHandler;
    }



        @Override
    protected void channelRead0(ChannelHandlerContext ctx, OctopusRawMessage msg) throws Exception {
        Session session = ctx.channel().attr(SESSION).get();
        OctopusRequest request = new OctopusRequest(ctx).setSession(session).setContext(ctx).setMessage
                (msg);
        OctopusResponse response = new OctopusResponse(ctx, msg);
        messageHandler.onMessage(request,response);
    }
}
