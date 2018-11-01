package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusPacketMessage;
import org.sunyata.game.server.message.OctopusPacketRawMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author leo
 */
public class OctopusPacketMsgDecoder extends ByteToMessageDecoder {
    Logger logger = LoggerFactory.getLogger(OctopusPacketMsgDecoder.class);

    public OctopusPacketMsgDecoder() {
        setSingleDecode(true);
    }

    private boolean decideFullPackage(int headSize, ByteBuf buf) {
        // TODO Auto-generated method stub
        //int capacity = buf.capacity();
        //logger.info("包体信息,capacity:{},headSize:{}", capacity, headSize);
        int readableLen = buf.readableBytes();
        if (readableLen >= headSize) {
            int length = buf.getInt(headSize - 4);
//            logger.info("包体长度:{}", length);
            int total = buf.writerIndex() - buf.readerIndex();
//            logger.info("readIdx:{}", buf.readerIndex());
//            logger.info("writeIdx:{}", buf.writerIndex());
//            logger.info("readLength:{}", total);
            if (total - headSize >= length) {
                return true;
            }
        }
        logger.info("包大小不满足要求,不处理");
        return false;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private final ReadWriteLock keyLock = new ReentrantReadWriteLock(true);

    //@Override
    protected void decode2(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Lock lock = keyLock.readLock();
        lock.lock();
        try {
            logger.info("消息接收解码开始......");
            final int headerSize = 20;
            while (decideFullPackage(headerSize, in)) {
                OctopusPacketMessage msg = new OctopusPacketRawMessage();
                msg.fromByteBuf(in);
                out.add(msg);
                logger.info("消息接收解码完成->messageType:{},sourceServerId:{}", msg.getMessageType(), msg.getSourceServerId
                        ());
            }
        } finally {
            lock.unlock();
        }
    }

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
        //logger.info("收到消息数量:{}", atomicInteger.incrementAndGet());
        int readableLen = in.readableBytes();
        final int headerSize = 20;
        Lock lock = keyLock.readLock();
        lock.lock();
        try {
            while (readableLen >= headerSize) {
                int readerIndex = in.readerIndex();
                OctopusPacketMessage msg = new OctopusPacketRawMessage();
                int messageType = in.readInt();//  1
                msg.setMessageType(messageType);//4
                int sourceServerId = in.readInt();//   2
                msg.setSourceServerId(sourceServerId);//4
                int destServerId = in.readInt();// 3
                msg.setDestServerId(destServerId);
                int dataId = in.readInt();//   4
                msg.setDataId(dataId);//4
                int length = in.readInt();//   5
                msg.setBody(null);
                if (readableLen >= (length + headerSize)) {
                    if (length > 0) {
                        byte[] bytes = new byte[length];
                        in.readBytes(bytes);
                        msg.setBody(bytes);
                        logger.info("收到消息数量:{}", atomicInteger.incrementAndGet());
                        out.add(msg);
                        readableLen = in.readableBytes();
                    }
                } else {
                    in.readerIndex(readerIndex);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }

    }
}
