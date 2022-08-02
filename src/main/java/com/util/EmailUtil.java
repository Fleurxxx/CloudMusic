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
import java.util.Random;

//多线程这种处理就可以称为异步处理
public class EmailUtil{

    public static void sendEmail(User user,String code) throws Exception{
        String from = "984209872@qq.com";//我们用来向客户发送邮件的邮箱
        String username = "984209872@qq.com";//用于登陆SMTP服务器的用户名
        String password = "nrfmvbkumljdbbih";//授权码

        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");  //设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.port", "465");//端口
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
        message.setSubject("网易云音乐验证");//设置邮件主题
        //邮件的文本内容——在网页上填写邮件内容
        if(user.getUser_name()==null || user.getUser_name().equals("")){
            message.setContent("<p><h2>欢迎来到网易云音乐</h2></p> 尊敬的用户您好！本次验证码为：<h4>"+code+"</h4>请妥善保管您的验证码，如有问题请及时联系网站客服。", "text/html;charset=UTF-8");//设置邮件的具体内容
        }else{
            message.setContent("<p><h2>欢迎来到网易云音乐</h2></p>用户："+user.getUser_name()+" 您好！本次验证码为：<h4>"+code+"</h4>请妥善保管您的验证码，如有问题请及时联系网站客服。", "text/html;charset=UTF-8");//设置邮件的具体内容
        }

        //5、发送邮件——在网页上点击发送按钮
        ts.sendMessage(message, message.getAllRecipients());

        //6、关闭连接对象，即关闭服务器上的连接资源
        ts.close();
    }




    public static String getCode(int n) {
        //3、定义一个字符串变量记录生成的随机字符
        String code = "";
        Random r = new Random();
        //2、定义一个for循环，循环n次，依次生成随机字符
        for (int i = 0; i < n; i++) {
            //i=0 1 2
            //3、生成一个随机字符，英文大、小写 数字（0 1 2 ）
            int type = r.nextInt(3);//0 1 2
            switch (type) {
                case 0:
                    //大写字符（A 65-Z 65+25）
                    char ch = (char) (r.nextInt(26) + 65);
                    code += ch;
                    break;
                case 1:
                    //小写字符（a 97-z 97+25）
                    char ch1 = (char) (r.nextInt(26) + 97);
                    code += ch1;
                    break;
                case 2:
                    //数字字符
                    code += r.nextInt(10);//0-9
                    break;
            }
        }
        return code;
    }

}
