package org.sunyata.game.login;

import org.sunyata.game.login.models.UserEntity;

/**
 * Created by leo on 17/11/16.
 */
public class UserLoginRetInfo {
//    /**
//     * id
//     */
//    private int id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 昵称
     */
    private String accountId;
    /**
     * uuid
     */
    private String uuid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 0:女生,1:男生,2:未知
     */
    private int sex;
    /**
     * 如果用户进入过房间,未主动退出房间id
     */
    private String roomCheckId;
    private int gold;
    private String loginToken;
    private String ip;
    private UserEntity userEntity;
    private String channelId;

//    public int getId() {
//        return id;
//    }
//
//    public UserLoginRetInfo setId(int id) {
//        this.id = id;
//        return this;
//    }

    public String getName() {
        return name;
    }

    public UserLoginRetInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public UserLoginRetInfo setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public UserLoginRetInfo setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserLoginRetInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public UserLoginRetInfo setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public UserLoginRetInfo setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
        return this;
    }

    public int getGold() {
        return gold;
    }

    public UserLoginRetInfo setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public UserLoginRetInfo setLoginToken(String loginToken) {
        this.loginToken = loginToken;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public UserLoginRetInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public UserLoginRetInfo setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }
}
