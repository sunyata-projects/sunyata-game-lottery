//package org.sunyata.game.room.commands;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.sunyata.game.ServerCommand;
//import org.sunyata.game.ServerConfigProperties;
//
//import org.sunyata.game.command.AbstractCommandHandler;
//import org.sunyata.game.server.OctopusPacketRequest;
//import org.sunyata.game.server.OctopusPacketResponse;
//import org.sunyata.game.service.UserLocationInfo;
//import org.sunyata.lottery.edy.common.constant.Commands;
//
///**
// * Created by leo on 17/11/16.
// */
//@ServerCommand(commandId = Commands.START_GAME_COMMAND, routeModel = ServerCommand.lbMode)
//public class StartGameCommand extends AbstractCommandHandler {
//    Logger logger = LoggerFactory.getLogger(StartGameCommand.class);
//    @Autowired
//    ServerConfigProperties serverConfigProperties;
//    //    @Autowired
////    AnyClientManager anyClientManager;
////    @Autowired
////    CommandService commandService;
////    @Autowired
////    ApplicationService applicationService;
//
////    @Autowired
////    UserCacheService userCacheService;
//
//    @Override
//    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
//        return true;
//    }
//
//    @Override
//    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
//        int userIdInGateway = request.getMessage().getDataId();
//        int sourceServerId = request.getMessage().getSourceServerId();
//        UserLocationInfo userInfo = userLocationService.getUserLocationInfo(sourceServerId, userIdInGateway);
//        String accountId = userInfo.getAccountId();//此为帐户名称
//        byte[] body = request.getMessage().getRawMessage().getBody();
////        Room.CreateRoomReq createRoomReq = Room.CreateRoomReq.parseFrom(body);
////        RoomConfigInfo roomConfigInfo = new RoomConfigInfo().fromProtobufReq(createRoomReq);
////        int gameType = createRoomReq.getGameType();
////        try {
////
////
////            String roomCheckId = applicationService.createRoom(userId, ServiceNames.getSceneServiceName(gameType),
////                    roomConfigInfo);
//////        ClientServerInfo descClientServerInfo = commandService.getDescClientServerInfo(sourceServerId, ServiceNames
//////                .game_gateway_service);
////            //构造返回消息
////            Room.CreateRoomRes.Builder builder = Room.CreateRoomRes.newBuilder();
////            builder.setRoomCheckId(roomCheckId);
//////        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserPacketMessage(userIdInGateway,
////// serverConfigProperties
//////                        .getGatewayServerId(),
//////                sourceServerId, Commands.createRoomRet, builder.build().toByteArray());
////            //anyClientManager.forwardMessage(descClientServerInfo, toUserPacketMessage);
////            sendMessageToUser(userIdInGateway, sourceServerId, Commands.createRoomRet, builder.build().toByteArray());
////        } catch (ExistsForMySelfException ex) {
////            sendErrorMessageToUser(userIdInGateway, sourceServerId, Integer.parseInt(ErrorCodes.hasCreateRoom));
////        }
//    }
//
//    @Override
//    public String getCommand() {
//        return null;
//    }
//
//    @Override
//    public String getRetCommand() {
//        return null;
//    }
//
//
//}