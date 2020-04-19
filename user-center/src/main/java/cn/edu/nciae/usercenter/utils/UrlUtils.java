package cn.edu.nciae.usercenter.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/18 11:57 PM
 */
@Component
public class UrlUtils {

    UrlUtils() throws Exception{
        this(strDefaultKey);
    }

    UrlUtils(String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /** 字符串默认键值 */
    private static String strDefaultKey = "SecretKey0";
    /** 加密工具 */
    private Cipher encryptCipher = null;
    /** 解密工具 */
    private Cipher decryptCipher = null;


    /**
     * desc : encrypt bytes
     * @param arrB -
     * @return byte[]
     * @throws Exception -
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * desc : encrypt
     * @param strIn -
     * @return String
     * @throws Exception -
     */
    public String encrypt(String strIn) throws Exception {
        return String.valueOf(encrypt(strIn.getBytes()));
    }

    /**
     * desc : decrypt
     * @param arrB - input arr
     * @return byte[]
     * @throws Exception -
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * desc : decrypt the encoded str
     * @param strIn -
     * @return String
     * @throws Exception-
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(strIn.getBytes()));
    }

    /**
     * desc : get the secret key from the str key. needs 8 bit otherwise get 0 filled
     * @param arrBTmp - secret key bytes
     * @return Key
     * @throws Exception -
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }
}