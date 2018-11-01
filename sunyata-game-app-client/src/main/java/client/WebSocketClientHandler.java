package client;

import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.springframework.context.ApplicationContext;

/**
 * Created by leo on 17/4/13.
 */
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private CommandMessageManager commandMessageManager;
    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public WebSocketClientHandler(ApplicationContext commandMessageManager, WebSocketClientHandshaker handshaker) {
        this.commandMessageManager = new CommandMessageManager();
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            System.out.println("WebSocket Client connected!");
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            System.out.println("WebSocket Client received message: " + textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();


//            while (decideFullPackage(20, buf)) {
//                OctopusRawMessage msg = new OctopusInRawMessage();
//                msg.setCmd(buf.readInt());//4
//                msg.setSerial(buf.readInt());//4
//                msg.setVersion(buf.readFloat());//4
//                msg.setDestServerId(buf.readInt());//4
//                msg.setLength(buf.readInt());//4
//                int length = msg.getLength();
//                //buf.readInt();
//                byte[] bytes = new byte[length];
//                if (length != 0) {
//                    buf.readBytes(bytes, 0, length);
//                }
//                msg.setBody(bytes);
//                //buf.discardReadBytes();
//                //ctx.fireChannelRead(msg);
//                //logger.info("request->cmd:{},length:{}", msg.getCmd(), msg.getLength());
//                out.add(msg);
//                logger.info("消息接收解码完成->cmd:{},length:{}", msg.getCmd(), msg.getLength());
//            }
            OctopusToUserRawMessage octopusRawMessage = (OctopusToUserRawMessage) new OctopusToUserRawMessage()
                    .fromByteBuf(content);
            commandMessageManager.handler(octopusRawMessage, ctx);
            //octopusRawMessage.getCmd()
//
//            int command = content.readInt();
//            long v = content.readInt();
//            int errorCode = content.readInt();
//            int length = content.readInt();
//            if (command == Integer.parseInt(Commands.loginRet)) {
//                byte[] bytes = new byte[length];
//                if (length != 0) {
//                    content.readBytes(bytes, 0, length);
//                }
//                Common.LoginResponseMsg loginResponseMsg = Common.LoginResponseMsg.parseFrom(bytes);
//                System.out.println("command:" + Commands.loginRet + ",返回结果:" + errorCode);
//            }
//            if (i == 10003) {
//                byte[] bytes = new byte[content.readableBytes()];
//                int readerIndex = content.readerIndex();
//                content.getBytes(readerIndex, bytes);
//                Common.ProfileResponseMsg profileResponseMsg = Common.ProfileResponseMsg.parseFrom(bytes);
//                System.out.println(profileResponseMsg.getDisplayName());
//            }
//            System.out.println("命令:" + command + "," + "返回:" + errorCode);
        }
    }

    private boolean decideFullPackage(int headSize, ByteBuf buf) {
        // TODO Auto-generated method stub
        if (buf.capacity() >= headSize) {
            int length = buf.getInt(16);
            int total = buf.writerIndex() - buf.readerIndex();
            if (total - 20 >= length) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }
}