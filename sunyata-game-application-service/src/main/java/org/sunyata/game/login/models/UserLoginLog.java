package org.sunyata.game.login.models;

import java.util.Date;

/**
 * Created by leo on 17/11/16.
 */
public class UserLoginLog {
    private int id;
    /**用户id*/
    private int user_id;
    private Date login_date;
    private Date logout_date;
    /**经度*/
    private Double longitude;
    /**纬度*/
    private Double latitude;
    /**ip*/
    private String ip;
    private int version;
    private String login_token;

    public int getId() {
        return id;
    }

    public UserLoginLog setId(int id) {
        this.id = id;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    public UserLoginLog setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public UserLoginLog setLogin_date(Date login_date) {
        this.login_date = login_date;
        return this;
    }

    public Date getLogout_date() {
        return logout_date;
    }

    public UserLoginLog setLogout_date(Date logout_date) {
        this.logout_date = logout_date;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public UserLoginLog setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public UserLoginLog setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public UserLoginLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public UserLoginLog setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getLogin_token() {
        return login_token;
    }

    public UserLoginLog setLogin_token(String login_token) {
        this.login_token = login_token;
        return this;
    }
}
