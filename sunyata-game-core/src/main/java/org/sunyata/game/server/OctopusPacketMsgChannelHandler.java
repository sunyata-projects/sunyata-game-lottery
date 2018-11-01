package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusPacketMessage;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OctopusPacketMsgChannelHandler extends ChannelHandler<OctopusPacketMsgHandler<?>, OctopusPacketMessage> {
    private OctopusPacketMsgHandler<?> messageHandler;

    public OctopusPacketMsgChannelHandler(OctopusPacketMsgHandler<?> messageHandler) {
        super(messageHandler);
        this.messageHandler = messageHandler;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OctopusPacketMessage msg) throws Exception {
        Session session = ctx.channel().attr(SESSION).get();
        OctopusPacketRequest request = new OctopusPacketRequest(ctx).setSession(session).setContext(ctx).setMessage
                (msg);
        OctopusPacketResponse response = new OctopusPacketResponse(ctx, msg);
        messageHandler.onMessage(request,response);
    }
}
