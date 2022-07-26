package com.servlet;

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.SendEmail;
import src.main.java.com.util.GetTime;
import com.util.GetID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/css");
        System.out.println("注册请求过来了");
        PrintWriter out = response.getWriter();
        User user = new User();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordagain = request.getParameter("passwordagain");
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String date = new GetTime().getTime();
        String id = new GetID().getid();
        user.setEmail(email);
        int rd = (int) (Math.random() * 10000);
        String rjcode = String.valueOf(rd);
        while (rjcode.length() < 4) {
            rjcode = "0" + rjcode;
        }
        SendEmail sendmail = new SendEmail(user,rjcode);//获取子线程对象
        new Thread(sendmail).start();//启动子线程
        request.setAttribute("message","我们已向您的邮箱发送验证码,请您及时进行查收。由于网络原因，您收到邮件的时间存在延迟，敬请谅解~");
        /* --- 处理业务逻辑，耗时。（异步处理）--- */
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(code);
        out.write("验证码：" + rjcode);
        /* -------- 响应 -------- */
        if(!code.equals(rjcode)){
            out.write("验证码错误");
        }else if(!password.equals(passwordagain)){
            out.write("注册失败，两次密码不一致");
        }else{
            user.setUser_id(Integer.parseInt(id));
            user.setPassword(password);
            user.setUser_phone(Integer.parseInt(phone));
            user.setUser_date(date);
            UserService ps = new UserServiceImpl();
            boolean yy = ps.register(user);
            if(yy){
                System.out.println("注册成功");
            }else{
                System.out.println("注册失败");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
