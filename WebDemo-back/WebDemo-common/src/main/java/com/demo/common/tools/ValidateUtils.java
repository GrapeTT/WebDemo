package com.demo.common.tools;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.demo.common.email.EmailClient;
import com.demo.common.exception.AppException;
import com.demo.common.redis.RedisClient;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description：校验工具类
 * @Name：UidUtils
 * @Package：com.demo.tools
 * @Author：涛哥
 * @Time：2022/5/26 17:26
 * @Version：1.0
 */
@Component
public class ValidateUtils {
    private static final Log LOG = Log.get();
    
    /**
     * 验证码key前缀
     */
    private static final String CODE_KEY_PREFIX = "vt_code_";
    
    /**
     * 验证次数key前缀
     */
    private static final String VALIDATE_KEY_PREFIX = "vt_code_count_";
    
    @Autowired
    private EmailClient emailClient;
    
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 生成验证码
     * @author tao
     * @date 2022/5/26 17:28
     */
    public String genCode(String busKey, String ...keySuffixs) {
        String key = this.genKey(CODE_KEY_PREFIX, busKey, keySuffixs);
        //生成验证码
        String code = this.genValidateCode();
        //存入redis，设置5分钟过期
        redisClient.setEx(key, code,5L, TimeUnit.MINUTES);
        LOG.info("genCode，busKey={}，keySuffixs={}，code={}", busKey, JSONUtil.toJsonStr(keySuffixs), code);
        return code;
    }
    
    /**
     * 生成验证码并发送邮件
     * @author tao
     * @date 2022/5/26 17:28
     */
    public boolean genCodeAndSendEmail(String email, String busKey, String ...keySuffixs) {
        //生成验证码
        String code = this.genCode(busKey, keySuffixs);
        //验证码过期时间（5分钟过期）
        Date invalidTime = new Date();
        invalidTime.setTime(invalidTime.getTime() + 300000);
        //发送验证码
        String content = "您的验证码：" + code + "。<br>" +
                "验证码将在5分钟（" + DateUtils.format(invalidTime) + "）后过期，请尽快使用。<br>" +
                "以上为系统邮件，请勿回复。谢谢！<br>";
        boolean res = emailClient.sendEmail(email, content);
        if (res) {
            return true;
        }
        //清除缓存
        String key = this.genKey(CODE_KEY_PREFIX, busKey, keySuffixs);
        redisClient.del(key);
        return false;
    }
    
    /**
     * 判断验证码是否存在
     * @author tao
     * @date 2022/5/26 17:28
     */
    public boolean isCodeExsit(String busKey, String ...keySuffixs) {
        String key = this.genKey(CODE_KEY_PREFIX, busKey, keySuffixs);
        String serverCode = redisClient.get(key);
        return StringUtils.isNotBlank(serverCode);
    }
    
    /**
     * 验证验证码
     * @author tao
     * @date 2022/5/26 17:28
     */
    public boolean validateCode(String code, String busKey, String ...keySuffixs) throws Exception {
        String codeKey = this.genKey(CODE_KEY_PREFIX, busKey, keySuffixs);
        String validateKey = this.genKey(VALIDATE_KEY_PREFIX, busKey, keySuffixs);
        //判断验证码是否已过期
        String serverCode = redisClient.get(codeKey);
        if (StringUtils.isBlank(serverCode)) {
            throw new AppException("验证码已过期，请重新获取");
        }
        //判断验证是否超次数
        if (redisClient.incr(validateKey, 1) > 3) {
            //清除缓存
            redisClient.del(codeKey);
            redisClient.del(validateKey);
            throw new AppException("验证码已失效，请重新获取");
        }
        //验证验证码
        if (serverCode.equals(code)) {
            //验证成功，清除缓存
            redisClient.del(codeKey);
            redisClient.del(validateKey);
            return true;
        }
        return false;
    }
    
    /**
     * 生成key
     * @author tao
     * @date 2022/5/26 18:05
     */
    private String genKey(String keyPrefix, String busKey, String ...keySuffixs) {
        String key = keyPrefix + busKey;
        if (keySuffixs != null && keySuffixs.length > 0) {
            StringBuilder builder = new StringBuilder(key);
            for (String keySuffix : keySuffixs) {
                builder.append(keySuffix).append("_");
            }
            key = builder.toString();
        }
        return key;
    }
    
    /**
     * 生成验证码
     * @author tao
     * @date 2022/5/26 18:05
     */
    private String genValidateCode() {
        StringBuilder valivalidateCode = new StringBuilder();
        int code;
        for(int i = 0; i < 6; i++) {
            code = RandomUtils.nextInt(0, 10);
            valivalidateCode.append(code);
        }
        return valivalidateCode.toString();
    }
}
