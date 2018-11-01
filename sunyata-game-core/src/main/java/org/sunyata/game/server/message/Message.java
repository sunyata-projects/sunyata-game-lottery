package org.sunyata.game.server.message;

import org.sunyata.game.server.Session;

import java.util.List;

/**
 * @author leo
 */
public interface Message {
//	private static final byte messageType = 0;
//	private static final byte messageId = 0;

//	public Message(byte messageType, byte messageId) {
//		this.messageType = messageType;
//		this.messageId = messageId;
//	}
//
//    abstract void decode(Input in) throws IOException, ProtocolException;
//
//    abstract void encode(Output out) throws IOException, ProtocolException;

    abstract int getMessageType();

    abstract int getMessageId();

    abstract <T> Session<T> getSession();

    abstract <T> void setSession(Session<T> session);
    byte[] toBytes();
    Object toPbObject();
    List<Integer> toList(int[] array);
}
