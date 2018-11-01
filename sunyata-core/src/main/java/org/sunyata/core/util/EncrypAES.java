package org.sunyata.core.util;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.DecimalFormat;

/**
 * Created by leo on 17/11/16.
 */

public class EncrypAES {
    private static String defaultKey = "01AE020-UYSLPWSX";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    private static EncrypAES instance;

    public EncrypAES() {
    }

    public static EncrypAES getInstance() {
        return instance;
    }

    private EncrypAES(String keyvalue) {
        Security.addProvider(new SunJCE());
        if (keyvalue == null) {
            keyvalue = defaultKey;
        }

        byte[] arrBTmp = null;

        try {
            arrBTmp = keyvalue.getBytes("utf-8");
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

        byte[] arrB = new byte[16];

        for (int key = 0; key < arrBTmp.length && key < arrB.length; ++key) {
            arrB[key] = arrBTmp[key];
        }

        SecretKeySpec var10 = new SecretKeySpec(arrB, "AES");

        try {
            this.encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            this.encryptCipher.init(1, var10);
            this.decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            this.decryptCipher.init(2, var10);
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (NoSuchPaddingException var7) {
            var7.printStackTrace();
        } catch (InvalidKeyException var8) {
            var8.printStackTrace();
        }

    }

    public byte[] Encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] src = null;

        try {
            src = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return this.encryptCipher.doFinal(src);
    }

    public byte[] Decryptor(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return this.decryptCipher.doFinal(buff);
    }

    public String toEncrytor(String msg) throws Exception {
        String str = new String((new HexTransfer()).byteArr2HexStr(getInstance().Encrytor(msg)));
        return str;
    }

    public String toDecryptor(String msg) throws Exception {
        String str = new String(getInstance().Decryptor((new HexTransfer()).hexStr2ByteArr(msg.toLowerCase())));
        return str;
    }

    public static void main(String[] args) throws Exception {
        String msg = "92750.00";
        System.out.println("加密后:" + (new EncrypAES()).toEncrytor(msg));
        String aa = "6324b8b8658421df9921b0dcfdc77a89";
        DecimalFormat df = new DecimalFormat("##0.00");
        String num = df.format(Double.valueOf((new EncrypAES()).toDecryptor(aa)));
        System.out.println("解密后:" + num);
    }

    static {
        instance = new EncrypAES(defaultKey);
    }
}