/*
 * Copyright 2017 AGTech.com All right reserved. This software is the
 * confidential and proprietary information of AGTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AGTech.com.
 */

package org.sunyata.lottery.edy.common.vo;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * Created by 张雷 on 2018/2/26.
 *
 * @author Zhanglei
 * @date 2018/02/26
 */
public class GameData extends HashMap<String, Object> {
    private final JSONObject payload;
    //private Map<String,Object> map = new HashMap<String,Object>();

    public GameData() {
        this.payload = new JSONObject();
    }

    public GameData(JSONObject payload) {
        this.payload = payload;
    }

    //
    public GameData set(String key, Object value) {
        this.put(key, value);
        return this;
    }

//    public void put(String key, Object value) {
//        this.put(key, value);
//        return this;
//    }

    //    public Object get(String key) {
//        return this.payload.get(key);
//    }
    public Object get(String key) {
        return this.getOrDefault(key, null);
    }

//    public <T> T get(String key, Class<T> type) {
//        return this.payload.getObject(key, type);
//    }

    public Integer getInteger(String key) {
        return (Integer) this.getOrDefault(key, null);
    }

    public String getString(String key) {
        return String.valueOf(this.getOrDefault(key, ""));
    }

    @Override
    public String toString() {
        return "GameData{" +
                "payload=" + payload +
                '}';
    }
}
