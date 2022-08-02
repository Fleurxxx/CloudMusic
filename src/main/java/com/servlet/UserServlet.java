package com.servlet;

import com.alibaba.fastjson.JSON;
import com.entity.Result;
import com.entity.User;
import com.entity.VerifyUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.*;


import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {

    /**
    * 登录
    */
    protected void loginn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        System.out.println("请求过来了");
        //通过流获取前端传参
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        String account = getuser.getEmail();
        String password = getuser.getPassword();
        UserService ps = new UserServiceImpl();
        boolean yy = ps.login(account,password);
        boolean zz = ps.loginByPhone(account,password);
        Integer id = ps.selectOfEmail(account);
        if(yy){
            String token= TokenUtils.getTokenGetId(new VerifyUser(id,account,password));
            Cookie cookie=new Cookie("Token",token);
            resp.addCookie(cookie);
            //把username返回值保存到session当前会话中，名称为an，只要当前会话不过期，任何地方都可以访到
            req.getSession().setAttribute("an",account);
            req.getSession().setAttribute("password",password);
            req.getSession().setMaxInactiveInterval(2000);
            out.write(JsonUtil.toJson(new Result(true,200,"登录成功",null)));
            System.out.println("登录成功");
        }else{
            out.write(JsonUtil.toJson(new Result(false,400,"用户名或密码错误",null)));
            System.out.println("登录失败");
        }
    }
    /**
     *注册
     */
    protected void registerr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        String username = getuser.getUser_name();
        String email = getuser.getEmail();
        String password = getuser.getPassword();
        int phone = getuser.getUser_phone();
        String code = getuser.getCode();
        String date = new TimeUtil().getTime();
        String id = new GetID().getid();
        User user = new User();
        user.setUser_name(username);
        user.setEmail(email);
        String str=email.substring(0, email.indexOf("@"));
        String codeac= (String) request.getSession().getAttribute(str);
//        System.out.println(code + "  " + codeac);
        UserService ps = new UserServiceImpl();
        if(!code.equals(codeac)){
            out.write(JsonUtil.toJson(new Result(false,400,"验证码错误",null)));
        }else{
            user.setUser_id(Integer.parseInt(id));
            user.setPassword(password);
            user.setUser_phone(phone);
            user.setUser_date(date);
            user.setUser_photo("../img/images/moren.jpg");
            user.setUser_type(0);
            user.setUser_state(0);
            boolean yy = ps.register(user);
            if(yy){
                System.out.println("注册成功");
                out.write(JsonUtil.toJson(new Result(true,200,"注册成功",null)));
            }else{
                System.out.println("注册失败");
                out.write(JsonUtil.toJson(new Result(false,400,"注册失败",null)));
            }
        }
    }

    /**
     *  判断用户名是否存在
     */
    protected void checkName(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
//        System.out.println(getuser);
        String username= getuser.getUser_name();
        UserService cn = new UserServiceImpl();
        if(cn.checkName(username)){
            out.write(JsonUtil.toJson(new Result(true,200,"名字取得好！可用",null)));
        }else{
            out.write(JsonUtil.toJson(new Result(false,400,"此用户名已被注册，请更换一个",null)));
        }
    }

    /**
     * 判断邮箱是否存在
     */
    protected void checkEmail(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
//        System.out.println(getuser);
        String email = getuser.getEmail();
        UserService cn = new UserServiceImpl();
        if(cn.checkEmail(email)){
            out.write(JsonUtil.toJson(new Result(true,200,"邮箱可用",null)));
        }else{
            out.write(JsonUtil.toJson(new Result(false,400,"该邮箱已被注册",null)));
        }

    }

    /**
     * 发送验证码
     */
    protected void sendCode(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PrintWriter out = resp.getWriter();
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        String email = getuser.getEmail();
        User user = new User();
        user.setEmail(email);
        String rjcode = EmailUtil.getCode(6);
        //截取qq号
        String str = email.substring(0, email.indexOf("@"));
        System.out.println("验证码："+rjcode);
        //将验证码存到session域中
        req.getSession().setAttribute(str,rjcode);
        //设置验证码的过期时间为5分钟之后
        req.getSession().setMaxInactiveInterval(300);
//        EmailUtil.sendEmail(user,rjcode);
        out.write(JsonUtil.toJson(new Result(true,200,"已发送验证码",null)));
    }

    /**
     * 忘记密码
     */
    protected void forgetPwd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PrintWriter out = resp.getWriter();
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        User user = new User();
        user.setEmail(getuser.getEmail());
        user.setPassword(getuser.getPassword());
        UserService us = new UserServiceImpl();
        if(us.updatePwd(user)){
            System.out.println("修改成功");
            out.write(JsonUtil.toJson(new Result(true, 200, "修改成功", null)));
        }else{
            System.out.println("修改失败");
            out.write(JsonUtil.toJson(new Result(false, 400, "修改失败", null)));
        }
    }


    /**
     * 获取用户信息
     */
    protected void getUserMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        PrintWriter out = response.getWriter();
        Cookie cookie = CookieUtil.findCookie("Token", request.getCookies());
        if (cookie != null) {
            String token = cookie.getValue();
            //验证token
            if (TokenUtils.getIsToken(token)) {
                //解密,获取email
                String str = TokenUtils.getTokenId(token);
                Integer id = Integer.parseInt(str);
                UserService userService = new UserServiceImpl();
                System.out.println("解密以后获取到的id为："+id);
                User user = userService.selectOfUserId(id);
                user.setUser_date(TimeUtil.getAgeByBirth(user.getUser_date()));
                System.out.println(user.getUser_date()+"oooooooooo");
                out.write(JsonUtil.toJson(new Result(true, 200, "jwt验证成功", user)));
            } else {
                out.write(JsonUtil.toJson(new Result(false, 400, "jwt验证失败", null)));
            }
        } else {
            out.write(JsonUtil.toJson(new Result(false, 400, "jwt验证失败", null)));
        }
    }

    /**
     * 修改用户信息
     */
    protected void updateUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
//        System.out.println(getuser);
        User user = new User();
        user.setUser_id(getuser.getUser_id());
        user.setUser_name(getuser.getUser_name());
        user.setUser_gender(getuser.getUser_gender());
        user.setUser_birth(getuser.getUser_birth());
        user.setUser_phone(getuser.getUser_phone());
        user.setUser_detail(getuser.getUser_detail());
        UserService us = new UserServiceImpl();
        if(us.updateUser(user)){
            System.out.println("修改成功");
            out.write(JsonUtil.toJson(new Result(true, 200, "修改成功", null)));
        }else{
            System.out.println("修改失败");
            out.write(JsonUtil.toJson(new Result(false, 400, "修改失败", null)));
        }

    }

    /**
     * 退出
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.销毁session对象
        req.getSession().invalidate();
        //2.删除cookie对象
        req.getSession().removeAttribute("username");
        req.getSession().removeAttribute("password");
        Cookie cookie=CookieUtil.findCookie("token",req.getCookies());
//        Cookie cookie = new Cookie("token","msg");
        cookie.setPath(req.getContextPath());
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        resp.getWriter().write(JsonUtil.toJson(new Result(true,200,"退出成功！",null)));
        resp.sendRedirect(req.getContextPath()+"/index.jsp");
    }


}
