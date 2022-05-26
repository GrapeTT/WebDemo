package com.demo.common.email;

import cn.hutool.log.Log;
import com.demo.common.tools.DateUtils;
import org.apache.commons.lang3.RandomUtils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @Description：邮件服务
 * @Author：涛哥
 * @Time：2019/3/28 15:32
 * @Version：1.0
 */
public class EmailClient {
    private static final Log LOG = Log.get();
    
    //发送邮件的主机
    private String host;
    //连接端口
    private String port;
    //发送人电子邮箱
    private String sender;
    //是否进行认证
    private String isAuth;
    //邮箱授权码
    private String authCode;
    //邮件主题
    private String title;
    //系统属性
    private Properties properties = null;
    //默认session对象
    private Session session = null;
    
    /**
     * @Description：初始化系统属性和默认session对象
     * @Author：涛哥
     * @Time：2019/3/28 16:02
     */
    private void init() {
        if(properties == null) {
            properties = System.getProperties();
            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);
            //设置端口
            properties.setProperty("mail.smtp.port", port);
            //设置认证
            properties.put("mail.smtp.auth", isAuth);
    
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
            properties.setProperty("mail.smtp.socketFactory.port", port);
        }
        if(session == null) {
            session = Session.getDefaultInstance(properties, new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(sender, authCode); //发件人邮件用户名、授权码
                }
            });
        }
    }
    
    /**
     * @Description：发送邮件
     * @param receiver 收件人电子邮箱
     * @Author：涛哥
     * @Time：2019/3/28 16:02
     */
    public String sendEmail(String receiver) {
        //生成验证码
        String validateCode = buildValidateCode();
        
        //验证码过期时间（5分钟过期）
        Date invalidTime = new Date();
        invalidTime.setTime(invalidTime.getTime() + 300000);
        
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(sender));
            
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            
            // Set Subject: 邮件主题
            message.setSubject(title, "UTF-8");
            
            // 设置邮件正文
            message.setContent("您的验证码：" + validateCode + "。<br>" +
                            "验证码将在5分钟（" + DateUtils.format(invalidTime) + "）后过期，请尽快使用。<br>" +
                            "以上为系统邮件，请勿回复。谢谢！<br>",
                    "text/html;charset=UTF-8");
            
            // 发送消息
            Transport.send(message);
            return validateCode;
        }catch (Exception e) {
            LOG.error("发送验证码邮件失败，receiver=" + receiver, e);
            return null;
        }
    }
    
    /**
     * @Description：生成验证码
     * @Author：涛哥
     * @Time：2019/3/28 16:24
     */
    private String buildValidateCode() {
        StringBuilder valivalidateCode = new StringBuilder();
        int code;
        for(int i = 0; i < 6; i++) {
            code = RandomUtils.nextInt(0, 10);
            valivalidateCode.append(code);
        }
        return valivalidateCode.toString();
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getIsAuth() {
        return isAuth;
    }
    
    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }
    
    public String getAuthCode() {
        return authCode;
    }
    
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
