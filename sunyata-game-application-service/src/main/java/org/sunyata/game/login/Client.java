package org.sunyata.game.login;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;


public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private Channel channel;

    private int id;
    private final ReentrantLock lock = new ReentrantLock();

    private String address;

    private int port;

    Client() {
    }

    void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Gateway [channel=" + channel + "]";
    }

    void setId(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    void close() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            channel.close();
        } finally {
            lock.unlock();
        }
    }

//    private void checkMsg(Message msg) throws Exception {
//        Message testMsg;
//        try {
//            testMsg = messageFactory.getMessage(msg.getMessageType(), msg.getMessageId());
//        } catch (Exception e) {
//            throw new Exception("错误的消息", e);
//        }
//        if (!testMsg.getClass().isAssignableFrom(msg.getClass())) {
//            throw new Exception("错误的消息");
//        }
//    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
