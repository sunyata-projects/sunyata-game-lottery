package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusRawMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusRequest {
    private Session session;

    public OctopusRequest(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    public OctopusRawMessage getMessage() {
        return message;
    }

    public OctopusRequest setMessage(OctopusRawMessage message) {
        this.message = message;
        return this;
    }

    private OctopusRawMessage message;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public OctopusRequest setContext(ChannelHandlerContext context) {
        this.context = context;
        return this;
    }

    ChannelHandlerContext context;

    //@SuppressWarnings("unchecked")
    public final <T> Session<T> getSession() {
        return session;
    }

    public final <T> OctopusRequest setSession(Session<T> session) {
        this.session = session;
        return this;
    }

}
