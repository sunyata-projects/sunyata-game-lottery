package org.sunyata.game.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OctopusClient {
    private final static Logger log = LoggerFactory.getLogger(OctopusClient.class);

    private static final int DEFAULT_THREAD_NUMS = 1;
    private static final int RETRY_WAIT = 1000;

    public static final OctopusClient createOctopusClient(String host, int port,
                                                          final OctopusMsgHandler<?> messageHandler, int threadNums) {
        return new OctopusClient(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("messageDecoder", new OctopusMsgDecoder());
                p.addLast("messageEncoder", new OctopusPacketMsgEncoder());

                p.addLast("handler", new OctopusMsgChannelHandler(messageHandler));
            }
        }, host, port, threadNums);
    }


    public static final OctopusClient createWebSocketClient(String host, int port,
                                                            final OctopusMsgHandler<?> messageHandler, int threadNums) {
        return new OctopusClient(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("messageDecoder", new OctopusMsgDecoder());
                p.addLast("messageEncoder", new OctopusPacketMsgEncoder());

                p.addLast("handler", new OctopusMsgChannelHandler(messageHandler));
            }
        }, host, port, threadNums);
    }

    //
    private final String host;
    private final int port;
    private final int threadNums;

    private Channel channel;
    private final ChannelInitializer<SocketChannel> initializer;
    private EventLoopGroup group;
    Bootstrap b = new Bootstrap();


    private OctopusClient(ChannelInitializer<SocketChannel> initializer,
                          String host, int port, int threadNums) {
        this.host = host;
        this.port = port;
        this.threadNums = threadNums;
        this.initializer = initializer;
    }

    public void connectRetry() throws Exception {
        connect(true);
    }

    public void connectNoRetry() throws Exception {
        connect(false);
    }

    private final ReadWriteLock keyLock = new ReentrantReadWriteLock(true);

    public void connect(boolean retry) throws Exception {
//        if (group != null) {
//            throw new ConnectException("不能重复连接！");
//        }
        Lock lock = keyLock.readLock();
        lock.lock();
        try {
            if (group == null) {
                group = new NioEventLoopGroup(threadNums);
            }
        } finally {
            lock.unlock();
        }
        try {
            b.group(group).channel(NioSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 10 * 1024 * 1024)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(initializer);
            if (retry) {
                while (true) {
                    ChannelFuture f = b.connect(host, port);
                    channel = f.channel();
                    f.await();
                    Throwable throwable = f.cause();
                    if (throwable == null) {
                        return;
                    }
                    log.error("连接失败！等待" + RETRY_WAIT + "开始重试！{}", throwable.getMessage());
                    Thread.sleep(RETRY_WAIT);
                }
            } else {
                ChannelFuture f = b.connect(host, port);
                channel = f.channel();
                f.get();
                // f.channel().closeFuture().sync();
                // log.error("连接失败！");
            }

        } finally {
            // group.shutdownGracefully();
        }
    }

    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel
                .localAddress();
    }

    public void write(Object msg) {
        log.debug("发送消息!{}", msg);
        channel.write(msg);
    }

    public void writeAndFlush(Object msg) {
//		log.debug("发送消息!{}", msg);
        channel.writeAndFlush(msg);
    }

    public Channel getChannel() {
        return channel;
    }

    public void close() throws InterruptedException, ExecutionException {
        group.shutdownGracefully().get();
        group = null;
    }

}
