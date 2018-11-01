package client.handlers;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by leo on 17/11/24.
 */
public class Utils {
    static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    public static void sendMessage(String commandId, Message msg, ChannelHandlerContext response) throws IOException {
        System.out.println("将要发送信息,请按回车键....");
        console.readLine();
        ByteBuf buffer = Unpooled.buffer();
        byte[] bytes = msg.toByteArray();
        int length = bytes.length;
        buffer.writeInt(Integer.parseInt(commandId)).writeInt(2323232).writeFloat(1.0f)
                .writeInt(UserManager.getRoomDestServerId())
                .writeInt(length)
                .writeBytes
                        (bytes);
        WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        response.writeAndFlush(frame);
    }
}
