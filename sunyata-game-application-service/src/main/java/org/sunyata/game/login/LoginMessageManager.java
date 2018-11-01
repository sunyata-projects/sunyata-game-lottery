//package org.sunyata.game.login;
//
//import OctopusRequest;
//import OctopusResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @author leo on 16/9/27.
// */
//@Component
//public class LoginMessageManager {
//    private static final Logger log = LoggerFactory.getLogger(LoginMessageManager.class);
//
////    @Autowired
////    private SceneManager sceneManager;
////    @Autowired
////    private RoomService roomService;
////    @Autowired
////    private UserService userService;
//
//    public void handler(OctopusRequest request, OctopusResponse response) {
//        try {
//            log.info(String.valueOf(request.getMessage().getCmd()));
//        } catch (
//                Throwable th
//                )
//
//        {
//
//        }
//
//    }
//
////    private Scene checkScene(PxMsg msg) {
////        Session<Scene> sceneSession = msg.getSession();
////        final Scene scene = sceneSession.get();
////        if (scene == null) {
////            log.error("未注册的Scene:{},sceneSession:{}", msg, sceneSession);
////            throw new ServerRuntimeException("未注册的Scene:" + msg + ",sceneSession:" + sceneSession);
////        }
////        return scene;
////    }
//}
