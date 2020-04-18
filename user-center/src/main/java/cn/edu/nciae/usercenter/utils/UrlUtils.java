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
    private static String strDefaultKey = "SecretKey09876012345";
    /** 加密工具 */
    private Cipher encryptCipher = null;
    /** 解密工具 */
    private Cipher decryptCipher = null;

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

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
        return byteArr2HexStr(encrypt(strIn.getBytes()));
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
        return new String(decrypt(hexStr2ByteArr(strIn)));
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