package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusPacketMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusPacketRequest {
    private Session session;

    public OctopusPacketRequest(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    public OctopusPacketMessage getMessage() {
        return message;
    }

    public OctopusPacketRequest setMessage(OctopusPacketMessage message) {
        this.message = message;
        return this;
    }

    private OctopusPacketMessage message;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public OctopusPacketRequest setContext(ChannelHandlerContext context) {
        this.context = context;
        return this;
    }

    ChannelHandlerContext context;

    //@SuppressWarnings("unchecked")
    public final <T> Session<T> getSession() {
        return session;
    }

    public final <T> OctopusPacketRequest setSession(Session<T> session) {
        this.session = session;
        return this;
    }

}
