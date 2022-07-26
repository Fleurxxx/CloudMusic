package com.util;

//多线程实现邮件发送
//使用多线程的原因就是提高用户的体验，一旦一个页面3s及以上的时间白屏就可能被用户关掉
//所以我们在用户提交表单之后，将费时的邮件发送工作使用一个子线程来完成，而主线程还是去完成它自己的事情
//这么做就可以提高用户体验，不然用户等待邮件发送的时间

import com.entity.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

//多线程这种处理就可以称为异步处理
public class SendEmail implements Runnable{
    private String from = "984209872@qq.com";//我们用来向客户发送邮件的邮箱
    private String username = "984209872@qq.com";//用于登陆SMTP服务器的用户名
    private String password = "nrfmvbkumljdbbih";//授权码

    private User user;
    private String code;
    public SendEmail(User user,String code) {
        this.user = user;//用于获取用户邮箱地址
        this.code = code;
    }

    @Override
    public void run() {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", "smtp.qq.com");  //设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
            prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

            // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //使用JavaMail发送邮件的5个步骤

            //1、创建定义整个应用程序所需的环境信息的 Session 对象
            //使用QQ邮箱的时候才需要，其他邮箱不需要这一段代码
            Session session = Session.getDefaultInstance(prop, new Authenticator() {//获取和SMTP服务器的连接对象
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、授权码
                    return new PasswordAuthentication(username, password);
                }
            });

            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);

            //2、通过session得到transport对象
            Transport ts = session.getTransport();//通过这一次和SMTP服务器的连接对象获取发送邮件的传输对象

            //3、使用邮箱的用户名和授权码连上SMTP邮件服务器，即登陆

            ts.connect("smtp.qq.com", username, password);


            //4、创建邮件对象MimeMessage——点击网页上的写信
            //创建一个邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人——在网页上填写发件人
            message.setFrom(new InternetAddress(username));//设置发件人
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发——在网页上填写收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));//设置收件人
            //邮件的标题——在网页上填写邮件标题
            message.setSubject("网易云音乐注册验证");//设置邮件主题
            //邮件的文本内容——在网页上填写邮件内容
            message.setContent("<p><h2>欢迎来到网易云音乐！</h2></p>本次验证码为：<h4>"+code+"</h4>请妥善保管您的密码，如有问题请及时联系网站客服，再次欢迎您的加入！！", "text/html;charset=UTF-8");//设置邮件的具体内容

            //5、发送邮件——在网页上点击发送按钮
            ts.sendMessage(message, message.getAllRecipients());

            //6、关闭连接对象，即关闭服务器上的连接资源
            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
