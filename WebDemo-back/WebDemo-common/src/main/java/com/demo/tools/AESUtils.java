package com.demo.tools;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Description：AES加解密工具类
 * @Name：AES
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/3/30 22:54
 * @Version：1.0
 */
public class AESUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);
    
    //AES KEY（必须为16位16进制字符串）
    private static String KEY = "1234567890abcdef";
    //算法/模式/补码方式
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * @Description：AES加密
     * @Author：涛哥
     * @Time：2019/3/30 22:58
     */
    public static String encrypt(String value) {
        try {
            byte[] raw = KEY.getBytes();  //获得密码的字节数组
            SecretKeySpec sKEY = new SecretKeySpec(raw, "AES"); //根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM);  //根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.ENCRYPT_MODE, sKEY); //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte [] byte_value = value.getBytes(StandardCharsets.UTF_8); //获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] encode_value = cipher.doFinal(byte_value); //密码器加密数据
            return Base64.encode(encode_value); //将加密后的数据转换为字符串返回
        } catch (Exception e) {
            LOGGER.error("AES加密失败", e);
            return null;
        }
    }
    
    /**
     * @Description：AES解密
     * @Author：涛哥
     * @Time：2019/3/30 22:59
     */
    public static String decrypt(String value) {
        try {
            byte[] raw = KEY.getBytes();  //获得密码的字节数组
            SecretKeySpec sKEY = new SecretKeySpec(raw, "AES"); //根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM);  //根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.DECRYPT_MODE, sKEY); //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte [] encode_value = Base64.decode(value); //把密文字符串转回密文字节数组
            byte [] byte_value = cipher.doFinal(encode_value); //密码器解密数据
            return new String(byte_value,StandardCharsets.UTF_8); //将解密后的数据转换为字符串返回
        } catch (Exception e) {
            LOGGER.error("AES解密失败", e);
            return null;
        }
    }
}
