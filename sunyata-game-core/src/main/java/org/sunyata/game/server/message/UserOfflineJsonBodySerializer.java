package org.sunyata.game.server.message;

public class UserOfflineJsonBodySerializer extends AbstractJsonBodySerializer {
    public int getUserId() {
        return userId;
    }

    public UserOfflineJsonBodySerializer setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    private int userId;

    public int getRoomId() {
        return roomId;
    }

    public UserOfflineJsonBodySerializer setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }

    private int roomId;


}
