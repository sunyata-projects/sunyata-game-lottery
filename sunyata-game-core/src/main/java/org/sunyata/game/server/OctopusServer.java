package org.sunyata.game.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;

public final class OctopusServer {
    private static final int DEFAULT_BOSS_THREAD_NUMS = 4;
    private static final int DEFAULT_WORKER_THREAD_NUMS = 8;

//    public static final OctopusServer create(int port, final PxMsgHandler<?> messageHandler) {
//        return create(port, messageHandler, DEFAULT_BOSS_THREAD_NUMS, DEFAULT_WORKER_THREAD_NUMS);
//    }
//
//
//    public static final OctopusServer create(int port,
//                                        final PxMsgHandler<?> messageHandler, int bossThreadNums,
//                                        int workerThreadNums) {
//        return create(new PxMsgFactory(), port, messageHandler, bossThreadNums, workerThreadNums);
//    }
//
//    public static final OctopusServer create(PxMsgFactory pxMsgFactory, int port,
//                                        final PxMsgHandler<?> messageHandler, int bossThreadNums,
//                                        int workerThreadNums) {
//        return new OctopusServer(port, new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel ch) throws Exception {
//                ChannelPipeline p = ch.pipeline();
//                p.addLast("messageDecoder", new PxMsgDecoder(pxMsgFactory));
//                p.addLast("messageEncoder", new PxMsgEncoder());
//
//                p.addLast("handler", new PxMsgChannelHandler(messageHandler));
//            }
//        }, bossThreadNums, workerThreadNums);
//    }


    public static final OctopusServer createOctopusServer(int port,
                                                          final OctopusPacketMsgHandler<?> messageHandler, int
                                                             bossThreadNums,
                                                          int workerThreadNums) {
        return new OctopusServer(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("messageDecoder", new OctopusPacketMsgDecoder());
                p.addLast("messageEncoder", new OctopusPacketMsgEncoder());

                p.addLast("handler", new OctopusPacketMsgChannelHandler(messageHandler));
            }
        }, bossThreadNums, workerThreadNums);
    }

    public static final OctopusServer createOctopusToGatewayServer(int port,
                                                                   final OctopusPacketMsgHandler<?> messageHandler, int
                                                                      bossThreadNums,
                                                                   int workerThreadNums) {
        return new OctopusServer(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("messageDecoder", new OctopusPacketMsgDecoder());
                p.addLast("messageEncoder", new OctopusPacketMsgEncoder());

                p.addLast("handler", new OctopusPacketMsgChannelHandler(messageHandler));
            }
        }, bossThreadNums, workerThreadNums);
    }

    public static final OctopusServer createWebSocketServer(int port,
                                                            final OctopusMsgHandler<?> messageHandler, int bossThreadNums,
                                                            int workerThreadNums) {
        return new OctopusServer(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
//                p.addLast("messageDecoder", new OctopusMsgDecoder(null));
//                p.addLast("messageEncoder", new OctopusPacketMsgEncoder());
//
//                p.addLast("handler", new OctopusMsgChannelHandler(messageHandler));
                p.addLast(new HttpServerCodec());

                p.addLast(new ChunkedWriteHandler());

                p.addLast(new HttpObjectAggregator(64 * 1024));

                p.addLast(new WebSocketServerHandler(messageHandler));

                p.addLast(new IdleStateHandler(60, 0, 0));

                p.addLast(new OctopusMsgChannelHandler(messageHandler));
            }
        }, bossThreadNums, workerThreadNums);
    }

    private final int port;
    private final ChannelInitializer<SocketChannel> initializer;
    private final int bossThreadNums;
    private final int workerThreadNums;
    private final static Logger log = LoggerFactory
            .getLogger(OctopusServer.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private OctopusServer(int port,
                          ChannelInitializer<SocketChannel> initializer, int bossThreadNums,
                          int workerThreadNums) {
        this.port = port;
        this.initializer = initializer;
        this.bossThreadNums = bossThreadNums;
        this.workerThreadNums = workerThreadNums;
    }

    public static final int SYSTEM_PROPERTY_PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());
    ThreadFactory threadRpcFactory = new NamedThreadFactory("NettyRPC ThreadFactory");

    public void start() throws Exception {
        if (bossGroup != null) {
            throw new ConnectException("不能重复启动监听");
        }
        log.info("启动端口监听！{}", port);
        bossGroup = new NioEventLoopGroup(bossThreadNums);
//        workerGroup = new NioEventLoopGroup(workerThreadNums);
        workerGroup = new NioEventLoopGroup(SYSTEM_PROPERTY_PARALLEL, threadRpcFactory, SelectorProvider.provider());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .option(ChannelOption.SO_RCVBUF, 10 * 1024 * 1024)
//                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
//                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(initializer)
                    //.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port);
            f.get();
        } finally {
            // bossGroup.shutdownGracefully();
            // workerGroup.shutdownGracefully();
        }
    }

    // public void broadcast(Object obj){
    // for (Channel channel : channelList) {
    // channel.write(obj);
    // }
    // }

    public void close() throws InterruptedException, ExecutionException {
        bossGroup.shutdownGracefully().get();
        workerGroup.shutdownGracefully().get();
    }

    public int getPort() {
        return port;
    }
}
