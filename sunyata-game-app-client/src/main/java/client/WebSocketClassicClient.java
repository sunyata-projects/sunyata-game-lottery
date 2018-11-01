package client; /**
 * Created by leo on 17/4/14.
 */

import com.google.common.collect.ArrayListMultimap;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.ApplicationContext;
import org.sunyata.game.lottery.contract.protobuf.common.Common;
import org.sunyata.game.lottery.contract.protobuf.regular.GameRegular;
import org.sunyata.lottery.edy.common.constant.Commands;
import org.sunyata.lottery.edy.common.enums.GameId;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * io.netty.handler.codec.http.websocketx.extension
 * Created by leo on 17/4/13.
 */
public final class WebSocketClassicClient {

    //CommandMessageManager commandMessageManager;
    static final String URL = System.getProperty("url", "ws://127.0.0.1:22012/websocket?token=tokenValuehahaha");
    //static final String URL = System.getProperty("url","ws://172.21.120.174:15002/websocket");
    private static AtomicLong serialCount = new AtomicLong();
    private static ApplicationContext applicationContext;

    public static Channel connect() throws SSLException, URISyntaxException {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        final String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS(S) is supported.");
            return null;
        }

        final boolean ssl = "wss".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            io.netty.handler.codec.http.cookie.DefaultCookie cookie = new io.netty.handler.codec.http.cookie
                    .DefaultCookie("cid", "tokenValud");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);

            DefaultHttpHeaders entries = new DefaultHttpHeaders();
            entries.add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode
                    (cookie));
            //CommandMessageManager bean = SpringContextUtilForServer.getBean(CommandMessageManager.class);
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(applicationContext,
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, true, entries));

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                            }
                            p.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(8192),
                                    WebSocketClientCompressionHandler.INSTANCE,
                                    handler);
                        }
                    });

            Channel ch = b.connect(uri.getHost(), port).sync().channel();
            handler.handshakeFuture().sync();
            return ch;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    private final static ArrayListMultimap<String, Runnable> mapCallbacks = ArrayListMultimap.create();

    public static void start() throws IOException, URISyntaxException, InterruptedException {
        Channel ch = null;
        try {
            ch = connect();
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = console.readLine();
                if (msg == null) {
                    break;
                } else if ("bye".equals(msg.toLowerCase())) {
                    ch.writeAndFlush(new CloseWebSocketFrame());
                    ch.closeFuture().sync();
                    break;

                } else if ("connect".equals(msg.toLowerCase())) {
//                    ch.writeAndFlush(new CloseWebSocketFrame());
//                    ch.closeFuture().sync();
                    ch = connect();
                } else if ("ping".equals(msg.toLowerCase())) {
                    WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
                    ch.writeAndFlush(frame);

                } else if ("login".equals(msg.toLowerCase())) {
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = "lcl".getBytes();
                    int length = bytes.length;
                    Common.LoginRequestMsg.Builder builder = Common.LoginRequestMsg.newBuilder();
                    builder.setChannelId("105").setAccountId("lcl").setTimeStamp("201004399458585555").setSign
                            ("sdfsdf");
                    byte[] loginBytes = builder.build().toByteArray();
                    buffer.writeInt(Integer.parseInt(Commands.Login)).writeInt(2323232).writeFloat(1.0f).writeInt(-1)
                            .writeInt(loginBytes.length)
                            .writeBytes
                                    (loginBytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);

                } else if ("bet".equals(msg.toLowerCase())) {
                    Common.BetRequestMsg.Builder builder = Common.BetRequestMsg.newBuilder();
                    Common.BetRequestMsg betRequestMsg = builder.setGameType(Integer.parseInt(GameId.GAME_101.getCode())).setAmt(10)
                            .build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = betRequestMsg.toByteArray();
                    buffer.writeInt(Integer.parseInt(Commands.Bet)).writeInt(2323232).writeFloat(1.0f).writeInt(-1)
                            .writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("raise".equals(msg.toLowerCase())) {

                    GameRegular.RegularRaiseBetRequestMsg.Builder builder = GameRegular.RegularRaiseBetRequestMsg
                            .newBuilder();
                    GameRegular.RegularRaiseBetRequestMsg build = builder.setTimes(3).build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = build.toByteArray();
                    buffer.writeInt(Integer.parseInt(Commands.RegularRaise)).writeInt(2323232).writeFloat(1.0f)
                            .writeInt(-1)
                            .writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("play".equals(msg.toLowerCase())) {
                    for (int i = 0; i < 10; i++) {
                        Common.PlayRequestMsg.Builder builder = Common.PlayRequestMsg.newBuilder();
                        builder.setIsAuto(true);
                        builder.setRolePosition(1);
                        Common.PlayRequestMsg playRequestMsg = builder.build();

                        ByteBuf buffer = Unpooled.buffer();
                        byte[] bytes = playRequestMsg.toByteArray();
                        buffer.writeInt(50006).writeInt(2323232).writeFloat(1.0f).writeInt(-1).writeInt(bytes.length).writeBytes
                                (bytes);

                        WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                        ch.writeAndFlush(frame);
                    }
//                    GameRegular.RegularRaiseBetRequestMsg.Builder builder = GameRegular.RegularRaiseBetRequestMsg
//                            .newBuilder();
//                    GameRegular.RegularRaiseBetRequestMsg build = builder.setTimes(3).build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = build.toByteArray();
//                    buffer.writeInt(Integer.parseInt(Commands.RegularRaise)).writeInt(2323232).writeFloat(1.0f)
//                            .writeInt(-1)
//                            .writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
                }

            }
        } finally

        {
            //group.shutdownGracefully();
        }
    }

    static Thread t = null;

    public static void main(String[] args) throws Exception {
        t = new Thread(() -> {
            try {
                start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.run();
        ;

    }


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketClassicClient.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}