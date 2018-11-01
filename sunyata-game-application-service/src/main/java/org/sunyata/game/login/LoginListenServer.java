//package org.sunyata.game.login;
//
//import PxServer;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ExecutionException;
//
///**
// * @author leo on 16/9/27.
// */
//@Component
//public class LoginListenServer {
//    public static final int BOSS_THREAD_NUMS = 2;
//    public static final int WORKER_THREAD_NUMS = 4;
//
//    private int port;
//
//    private LoginListenHandler messageHandler;
//    private PxServer pxServer;
//
//    public void start() throws Exception {
//        pxServer = PxServer.createOctopusServer(port, messageHandler, BOSS_THREAD_NUMS, WORKER_THREAD_NUMS);
//        pxServer.start();
//    }
//
//    public void close() throws ExecutionException, InterruptedException {
//        if(pxServer != null){
//            pxServer.close();
//        }
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public void setMessageHandler(LoginListenHandler messageHandler) {
//        this.messageHandler = messageHandler;
//    }
//}
