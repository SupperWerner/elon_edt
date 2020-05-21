package com.zsy.msm.utils;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * @ClassName EmilUtils
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/20 9:54 PM
 * @Version 1.0
 */

public class EmilUtils {
    /**
     * 发送邮件的方法
     * @param to	接收人
     * @param title	邮件主题
     * @param content	邮件内容
     * @return
     */
    public static String sendMail(String to, String title, String content) {
        /**
         * 1、设置邮箱的一些属性，关于Properties类的介绍，见末尾博客
         * 2、创建认证对象authenticator，使用自己的邮件账号和授权码
         * 3、获得一个session对象，用来保存认证对象
         * 4、创建邮件消息对象message
         * 	4.1、设置message的发送人，这个要和认证对象的账号一致
         *  4.2、设置message的接收人
         * 	4.3、设置邮件的主题和内容
         */

        // 1、创建Properties属性对象，并设置一些邮件的属性
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.163.com"); // 设置邮箱服务器
        props.setProperty("mail.transport.protocol", "SMTP"); // 设置邮箱发送的协议
        props.setProperty("mail.smtp.auth", "true"); // 设置认证方式

        // 2、创建认证对象authenticator
        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zy9999987", "YYHRMWHLNDKNYICQ"); // 邮件账号和授权码，注意不是密码。
            }
        };

        // 3、创建会话对象session
        Session session = Session.getInstance(props, authenticator);

        // 4、创建邮件消息对象，设置发送人、接收人、邮件主题、邮件内容
        MimeMessage mess = new MimeMessage(session);
        try {
            mess.setFrom(new InternetAddress("zy9999987@163.com")); // 设置邮件的发件人
            mess.setRecipients(Message.RecipientType.TO, to); // 设置收件人
            mess.setSubject(title); // 设置邮件标题
            mess.setContent(content, "text/html;charset=utf-8"); // 设置邮件内容和格式

            // 5、发送邮件
            Transport.send(mess);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "发送邮件失败, 原因:" + e.getMessage();
        }
        return "发送邮件成功！接收人：" + to;
    }

}
