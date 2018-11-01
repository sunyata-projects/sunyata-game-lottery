package org.sunyata.core.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leo on 17/11/16.
 */
public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static EncrypAES encrypAES = new EncrypAES();

    public Utils() {
    }

    public static String getCurrYearMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        String date = year + "" + (month < 10 ? "0" + month : Integer.valueOf(month));
        return date;
    }

    private static Date getLastMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, -1);
        return cal.getTime();
    }

    public static String getLastYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        return sdf.format(getLastMonthDate(date));
    }

    public static String getLastYearMonthInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(getLastMonthDate(date));
    }

    public static String getNowYearMonthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return sdf.format(date);
    }

    public static boolean allowAccessByIp(String limitIPs, String ip) {
        boolean canIn = false;
        String[] ipArr = limitIPs.split(",");
        if (ipArr.length > 0) {
            for (int i = 0; i < ipArr.length; ++i) {
                if (ipArr[i].equals(ip)) {
                    canIn = true;
                    break;
                }

                canIn = false;
            }
        }

        return canIn;
    }

    public static boolean allowAccessByIP(String limitIp, String requestIp) {
        boolean canIn = false;
        String[] ipArr = limitIp.split(",");
        if (ipArr.length > 0) {
            for (int i = 0; i < ipArr.length; ++i) {
                if (ipArr[i].contains("*")) {
                    int len = ipArr[i].indexOf("*");
                    ipArr[i] = ipArr[i].substring(0, len);
                    if (requestIp.indexOf(ipArr[i]) == 0) {
                        canIn = true;
                        break;
                    }

                    canIn = false;
                } else {
                    if (ipArr[i].equals(requestIp)) {
                        canIn = true;
                        break;
                    }

                    canIn = false;
                }
            }
        }

        return canIn;
    }


    public static String getMainMatchId(String matchId) {
        return matchId.length() > 12 ? matchId.substring(0, matchId.length() - 8) : matchId;
    }

    public static Integer getPartnerIdByAppId(Integer appId) {
        String appIdString = String.valueOf(appId);
        return Integer.valueOf(appIdString.substring(0, 3));
    }

    public static String getNegative(String v) {
        BigDecimal vb = new BigDecimal(v);
        return vb.compareTo(BigDecimal.ZERO) <= 0 ? v : vb.multiply(new BigDecimal("-1")).toPlainString();
    }

    public static String getNegative(BigDecimal v) {
        return leq(v, BigDecimal.ZERO) ? v.toPlainString() : v.multiply(new BigDecimal("-1")).toPlainString();
    }

    public static boolean le(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == -1;
    }

    public static boolean leq(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) <= 0;
    }

    public static boolean eq(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == 0;
    }

    public static boolean ge(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == 1;
    }

    public static boolean geq(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    public static int getNumber(String param, int value) {
        try {
            return Integer.parseInt(param);
        } catch (Exception var3) {
            return value;
        }
    }

    public static Date getDate(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, day.intValue());
        return calendar.getTime();
    }

    public static String getDatePars1(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String getDatePars2(Date date) {
        try {
            return sdf1.format(date);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String decrypt(String EncryptedString) {
        try {
            return encrypAES.toDecryptor(EncryptedString);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String encrypt(String plaintext) {
        try {
            return encrypAES.toEncrytor(plaintext);
        } catch (Exception var2) {
            return null;
        }
    }
}