package com.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description：RSA工具类
 * @Name：RSAUtils
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/5/6 11:33
 * @Version：1.0
 */
public class RSAUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
    
    //指定加密算法为RSA
    private static final String ALGORITHM = "RSA";
    //公钥
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDImmbdWBfTN67u7vTLUWGkSUrL\n" +
            "7ANBBmLWvyeYsafIRKNJMU1aSQat5jStLEjGwG8rz94wDy2gSoZCzBw6YEwuH665\n" +
            "m4Oiuk3pjpd98l9bauSxNRVB60K9y20okixpgiarJgOnuGaVK4NMo9gGacsCiw9C\n" +
            "qqJXtjoxq5Z09nJNcwIDAQAB";
    //私钥
    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMiaZt1YF9M3ru7u\n" +
            "9MtRYaRJSsvsA0EGYta/J5ixp8hEo0kxTVpJBq3mNK0sSMbAbyvP3jAPLaBKhkLM\n" +
            "HDpgTC4frrmbg6K6TemOl33yX1tq5LE1FUHrQr3LbSiSLGmCJqsmA6e4ZpUrg0yj\n" +
            "2AZpywKLD0Kqole2OjGrlnT2ck1zAgMBAAECgYANclQc2C/Yp63hNEFSc6UQ5xzN\n" +
            "aIebTztG289iq93sTbWITXa665Q4CetUBFjogbOyAyAIpqvPR8+SIK5vt07D1iix\n" +
            "mkDzLPAvZQHqcC1jqp5qY9OJ12+FlmgHWJi5RLxrjmdGIhtb5nM3EE5MLm8l1+wI\n" +
            "hQF8zx0jGW+1t2adoQJBAP0gLcDFbClUD4JASztS4pksm9btzUjznzFHjm9hJaG3\n" +
            "VR0DC1sxpqhkdqyLmGDhXXwMiJYg8ll3JqQZBfWH/lECQQDK4YsKq7cRc0bG+dD7\n" +
            "tMcjw8vPrVTgO9ROaZCK3kU/nYfew2QqTe5ZNt+NCwR0/iQmvE42MA409qlxvE2M\n" +
            "LAqDAkAEBE61kGx2+26DkqGe/2G5LIwMjRmE3tjX31rTSadfOZmQLEwx1kfexDld\n" +
            "45k3cFwba9d/CmbOJKXoVL1TeCUBAkEAnc4ReJtYKhqgUuS9yQs0Wn1RaphIDKJ8\n" +
            "4QD7jQiWlITTylHw93bmjj5AZFTNgrKrK/YMX9nBMzEpW248Y2teQQJBAPiuZlj1\n" +
            "pAaisWtD13M1xSr5U6x1C8ugwi+sm3zNDxstSdTEFGW3eLhtWC1xzkMvYYdPcky1\n" +
            "ixudvt5XOWuGSBg=";
    
    /**
     * @Description：获取公钥
     * @Author：涛哥
     * @Time：2019/5/6 13:08
     */
    private static PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(PUBLIC_KEY);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    
    /**
     * @Description：获取私钥
     * @Author：涛哥
     * @Time：2019/5/6 13:06
     */
    private static PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(PRIVATE_KEY);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    
    /**
     * @Description：加密
     * @Author：涛哥
     * @Time：2019/5/6 11:35
     */
    public static String encrypt(String source) throws Exception {
        Key publicKey = getPublicKey();
        //得到Cipher对象来实现对源数据的RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] sourceBytes = source.getBytes();
        //执行加密操作
        byte[] resultBytes = cipher.doFinal(sourceBytes);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(resultBytes);
    }
    
    /**
     * @Description：解密
     * @Author：涛哥
     * @Time：2019/5/6 11:37
     */
    public static String decrypt(String source) throws Exception {
        Key privateKey = getPrivateKey();
        //得到Cipher对象对已用公钥加密的数据进行RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] sourceBytes = decoder.decodeBuffer(source);
        //执行解密操作
        try {
            byte[] resultBytes = cipher.doFinal(sourceBytes);
            return new String(resultBytes);
        }catch (Exception e){
            LOGGER.error("RSA解密失败", e);
            return null;
        }
    }
}
