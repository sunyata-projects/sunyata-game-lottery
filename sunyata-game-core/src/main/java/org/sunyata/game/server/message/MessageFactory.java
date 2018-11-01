package org.sunyata.game.server.message;

/**
 * Created by leo on 17/11/15.
 */
public class MessageFactory {
    public static OctopusToUserRawMessage createToUserMessage(String cmd, byte[] bytes) {
        OctopusToUserRawMessage result = new OctopusToUserRawMessage();
        result.setCmd(Integer.parseInt(cmd));
        result.setSerial(1);
        result.setCode(0);
        if (bytes != null) {
            result.setLength(bytes.length);
            result.setBody(bytes);
        } else {
            result.setLength(0);
            result.setBody(null);
        }
        return result;
    }

    public static OctopusToUserRawMessage createToUserErrorMessage(int commandId, int errorCode) {
        OctopusToUserRawMessage result = new OctopusToUserRawMessage();
        result.setCmd(commandId);
        result.setSerial(1);
        result.setCode(errorCode);
        result.setLength(0);
        result.setBody(null);
        return result;
    }

    public static OctopusPacketMessage createFromUserMessage(int userId, int gatewayServerId, OctopusRawMessage
            rawMessage) {
        OctopusPacketMessage result = new OctopusPacketRawMessage().setMessageType(1).setDataId(userId)
                .setSourceServerId
                        (gatewayServerId).setRawMessage(rawMessage);
        return result;
    }

    public static OctopusPacketMessage createToSystemPacketMessage(int dataId, int sourceServerId, OctopusRawMessage
            rawMessage) {
        OctopusPacketMessage result = new OctopusPacketRawMessage().setMessageType(0).setDataId(dataId)
                .setSourceServerId
                        (sourceServerId).setRawMessage(rawMessage);
        return result;
    }


    public static <T extends JsonBodySerializer> OctopusPacketMessage createToSystemPacketJsonBodyMessage(int dataId,
                                                                                                          int sourceServerId,
                                                                                                          String cmdId, T t) {
        AbstractOctopusRawMessage abstractOctopusRawMessage = new DefaultOctopusJsonBodyMessage<T>().setPropertyInfo
                (t).setCmd(Integer.parseInt(cmdId)).setSerial(1);

        OctopusPacketMessage result = new OctopusPacketRawMessage().setMessageType(OctopusPacketMessage
                .toSysMessageType).setDataId(dataId)
                .setSourceServerId(sourceServerId).setRawMessage(abstractOctopusRawMessage);
        return result;
    }

    public static OctopusPacketMessage createToUserPacketMessage(int userId, int gatewayServerId, OctopusRawMessage
            rawMessage) {
        OctopusPacketMessage result = new OctopusPacketRawMessage().setMessageType(-1).setDataId(userId)
                .setSourceServerId(-1).setRawMessage(rawMessage.setDestServerId(gatewayServerId));
        return result;
    }

    public static OctopusPacketMessage createToUserPacketMessage(int userId, int sourceServerId, int destServerId,
                                                                 String cmdId, byte[] bytes) {
        return new OctopusPacketRawMessage()
                .setMessageType(-1)
                .setDataId(userId)
                .setSourceServerId(sourceServerId)
                .setDestServerId(destServerId)
                .setRawMessage(MessageFactory.createToUserMessage(cmdId, bytes));
    }

    public static OctopusPacketMessage createToUserErrorPacketMessage(int commandId, int userIdInGateway, int
            sourceServerId, int destServerId, int errorCode) {
        return new OctopusPacketRawMessage()
                .setMessageType(-1)
                .setDataId(userIdInGateway)
                .setSourceServerId(sourceServerId)
                .setDestServerId(destServerId)
                .setRawMessage(MessageFactory.createToUserErrorMessage(commandId, errorCode));
    }

    public static OctopusRawMessage createRawMessage(int messageType) {
        if (messageType == -1) {
            return new OctopusToUserRawMessage();
        } else {
            return new OctopusInRawMessage();
        }
    }
}
