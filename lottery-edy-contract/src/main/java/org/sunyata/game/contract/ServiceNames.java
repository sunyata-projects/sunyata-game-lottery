package org.sunyata.game.contract;

/**
 * Created by leo on 17/11/9.
 */
public class ServiceNames {
    //game login service
    public final static String game_majiang_scene_service = "game-majiang-scene-service";
    public final static String game_gateway_service = "game-gateway-service";
    public final static String game_application_service = "game-application-service";
    public final static String game_inner_gateway_service = "game-inner_gateway-service";

    public static String getSceneServiceName(int gameType) {
        if (gameType == 31001) {
            return game_majiang_scene_service;
        } else {
            return game_majiang_scene_service;
        }
    }
}
