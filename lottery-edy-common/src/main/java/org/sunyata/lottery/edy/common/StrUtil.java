//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.sunyata.lottery.edy.common;

import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StrUtil {
    public StrUtil() {
    }

    public static boolean IsNotNull(String str) {
        boolean res = false;
        if (null != str) {
            str = str.replaceAll(" ", "");
            if (!"".equals(str)) {
                res = true;
            }
        }

        return res;
    }

    public static boolean IsNull(String str) {
        boolean res = false;
        if (null == str) {
            res = true;
        }

        if (null != str) {
            str = str.replaceAll(" ", "");
            if ("".equals(str)) {
                res = true;
            }
        }

        return res;
    }

    public static byte[] getFromBASE64(String s) {
        if (s == null) {
            return null;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                byte[] b = decoder.decodeBuffer(s);
                return b;
            } catch (Exception var3) {
                return null;
            }
        }
    }

    public static String getBASE64(String s) {
        return s == null ? null : (new BASE64Encoder()).encode(s.getBytes());
    }

    public static String moneyAddComma(String str1) {
        str1 = (new StringBuilder(str1)).reverse().toString();
        String str2 = "";

        for(int i = 0; i < str1.length(); ++i) {
            if (i * 3 + 3 > str1.length()) {
                str2 = str2 + str1.substring(i * 3, str1.length());
                break;
            }

            str2 = str2 + str1.substring(i * 3, i * 3 + 3) + ",";
        }

        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }

        str2 = (new StringBuilder(str2)).reverse().toString();
        return str2;
    }

    public static boolean areNotEmpty(String key, String value) {
        return !StringUtils.isEmpty(key) && !StringUtils.isEmpty(value);
    }

    public static void main(String[] args) {
        String s = moneyAddComma("1001020");
        System.out.println(s);
    }
}
