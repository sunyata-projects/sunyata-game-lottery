package client.handlers;

/**
 * Created by leo on 17/11/24.
 */
public class UserManager {
    private static int roomDestServerId = -1;

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        UserManager.currentUserId = currentUserId;
    }

    public static int currentUserId = 0;

    public static int getCurrentLocationIndex() {
        return currentLocationIndex;
    }

    public static void setCurrentLocationIndex(int currentLocationIndex) {
        UserManager.currentLocationIndex = currentLocationIndex;
    }

    public static int currentLocationIndex = 0;

    public static void setRoomDestServerId(int roomDestServerId) {
        UserManager.roomDestServerId = roomDestServerId;
    }

    public static int getRoomDestServerId() {
        return roomDestServerId;
    }
}
