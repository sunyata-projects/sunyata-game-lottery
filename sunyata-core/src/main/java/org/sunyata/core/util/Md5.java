package org.sunyata.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Md5 {
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static final String MD5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] e = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(e);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public Md5() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + bByte);
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        return String.valueOf(iRet);
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for(int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }

        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;

        try {
            new String(strObj);
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }

    public final String randomTimeString(int length) {
        Random randGen = null;
        Object numbersAndLetters = null;
        if(length < 1) {
            return null;
        } else {
            if(randGen == null) {
                randGen = new Random();
            }

            char[] var7 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            char[] randBuffer = new char[length];

            for(int randomStr = 0; randomStr < randBuffer.length; ++randomStr) {
                randBuffer[randomStr] = var7[randGen.nextInt(35)];
            }

            String var8 = new String(randBuffer);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            var8 = df.format(new Date()) + var8;
            return var8;
        }
    }

    public final String randomString(int length) {
        Random randGen = null;
        Object numbersAndLetters = null;
        if(length < 1) {
            return null;
        } else {
            if(randGen == null) {
                randGen = new Random();
            }

            char[] var6 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            char[] randBuffer = new char[length];

            for(int i = 0; i < randBuffer.length; ++i) {
                randBuffer[i] = var6[randGen.nextInt(35)];
            }

            return new String(randBuffer);
        }
    }

    public static void main(String[] args) {
        Md5 getMD5 = new Md5();
        System.out.println(GetMD5Code("bb"));
    }
}