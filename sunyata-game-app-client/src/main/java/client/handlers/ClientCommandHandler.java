package client.handlers;

import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by leo on 17/4/18.
 */
public interface ClientCommandHandler {
    void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception;
}
