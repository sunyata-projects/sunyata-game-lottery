package org.sunyata.game.server;

import org.sunyata.game.server.message.OctopusInRawMessage;
import org.sunyata.game.server.message.OctopusRawMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author leo
 */
public class OctopusMsgDecoder extends ByteToMessageDecoder {
    Logger logger = LoggerFactory.getLogger(OctopusMsgDecoder.class);

    public OctopusMsgDecoder() {
//        if (messageFactory == null) {
//            throw new NullPointerException("messageFactory");
//        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
                          List<Object> out) throws Exception {
        logger.info("消息接收解码开始......");
        while (decideFullPackage(20, buf)) {
            OctopusRawMessage msg = new OctopusInRawMessage();
            msg.setCmd(buf.readInt());//4
            msg.setSerial(buf.readInt());//4
            msg.setVersion(buf.readFloat());//4
            msg.setDestServerId(buf.readInt());//4
            int length = buf.readInt();
            //msg.setLength(buf.readInt());//4
            //int length = msg.getLength();
            //buf.readInt();
            msg.setBody(null);

            if (length > 0) {
                byte[] bytes = new byte[length];
                buf.readBytes(bytes, 0, length);
                msg.setBody(bytes);
            }

            //buf.discardReadBytes();
            //ctx.fireChannelRead(msg);
            //logger.info("request->cmd:{},length:{}", msg.getCmd(), msg.getLength());
            out.add(msg);
            logger.info("消息接收解码完成->cmd:{}", msg.getCmd());
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
}
