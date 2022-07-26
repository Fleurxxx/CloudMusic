package com.servlet;

import com.service.UserService;
import com.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //在tomcat中添加的URIEncoding只能处理get请求的乱码
        response.setCharacterEncoding("UTF-8");//只能处理post请求的乱码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/css");
        System.out.println("登录请求过来了");
        PrintWriter out = response.getWriter();
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        UserService ps = new UserServiceImpl();
        boolean yy = ps.login(account,password);
        String username = "匿名";
        if(yy){
            System.out.println("登录成功");
            //把username返回值保存到session当前会话中，名称为usr，只要当前会话不过期，任何地方都可以访到
            request.getSession().setAttribute("name",username);
            //跳转登录成功页面（转发/重定向）
//            request.getRequestDispatcher("/success.html").forward(request,response);
        }else{
            System.out.println("登录失败");
//            request.getRequestDispatcher("/error.html").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
