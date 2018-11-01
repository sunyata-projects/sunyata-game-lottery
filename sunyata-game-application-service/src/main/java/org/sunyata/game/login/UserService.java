package org.sunyata.game.login;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class UserService {
    //登录类型1用户名密码,2手机验证码,3微信,4token,9匿名,
    public static final int LOGIN_TYPE_USERNAME_PASSWORD = 1;
    public static final int LOGIN_TYPE_MOBILE_SMS = 2;
    public static final int LOGIN_TYPE_WEIXIN = 3;
    public static final int LOGIN_TYPE_TOKEN = 4;
    public static final int LOGIN_TYPE_ANONYMOUS = 9;


    public UserLoginRetInfo loginByToken(String channelId, String accountId, String timeStamp, String sign) throws
            Exception {
        if (!checkTime(timeStamp)) {
            return null;
        }
        if (checkToken(channelId, accountId, timeStamp, sign)) {
            UserLoginRetInfo login = new UserLoginRetInfo().setAccountId(accountId).setChannelId(channelId).setName
                    (accountId);
            return login;
        } else {
            return null;
        }
    }

    private static long expireTime = 5 * 60 * 1000;

    public boolean checkTime(String timestamp) throws Exception {
        return true;
//        try {
//
//            if (Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)) > expireTime) {
//                throw new Exception("时间超时");
//            }
//            return true;
//        } catch (Exception e) {
//            //logg.info("时间戳是：" + timestamp, e);
//            throw new Exception("上传时间超时");
//        }
    }

    public boolean checkToken(String channelId, String accountId, String timestamp, String sign) throws Exception {
        return true;
//        String secret = getSecret(channelId);
//        String oriString = channelId + accountId + timestamp + secret;
//        byte[] bytes = encryptMD5(oriString.getBytes("UTF-8"));
//        String s = SecretUtil.byte2hex(bytes);
//        return StringUtils.equals(s, sign);
    }

    private String getSecret(String channelId) {
        return "asdfasdf";
    }


    private static String digestPassword(String password) {
        return Base64.encodeBase64URLSafeString(DigestUtils.sha256(salt + password));
    }

    private static final String salt = "n532ij";


}
