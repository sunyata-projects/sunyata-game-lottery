package client;//package org.sunyata.game.test.client;
//
//import OctopusPacketRawMessage;
//import SinglePxMsg;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
///**
// * Created by leo on 17/10/26.
// */
//@Component
//public class TestCommand implements CommandLineRunner {
//
//    @Autowired
//    TestClient client;
//
//    @Override
//    public void run(String... strings) throws Exception {
//        OctopusPacketRawMessage msg = new OctopusPacketRawMessage();
//        byte[] bytes = "lcl".getBytes();
//        msg.setCmd(15001);
//        msg.setSerial(2323232);
//        msg.setVersion(1.0f);
//        msg.setLength(bytes.length);
//        msg.setBody(bytes);
//        ByteBuf buffer = Unpooled.buffer();
//        buffer.writeInt(55003).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
//                (bytes);
//        SinglePxMsg sm = new SinglePxMsg((short) 1, buffer);
//        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//        client.writeAndFlush(frame);
//    }
//}
