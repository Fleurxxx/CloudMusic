package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.service.SearchService;
import com.service.UserService;
import com.service.impl.SearchServiceImpl;
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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {

    /**
    * 登录
    */
    protected void loginn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
//        System.out.println("登录请求过来了");
        //通过流获取前端传参
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        String account = getuser.getEmail();
        String password = getuser.getPassword();
        String newpwd = MD5.generateMD5(password);
        UserService ps = new UserServiceImpl();
        boolean yy = ps.login(account,newpwd);
        boolean zz = ps.loginByPhone(account,newpwd);
        Integer id = ps.selectOfEmail(account);
        if(yy){
            String token= TokenUtils.getTokenGetId(new VerifyUser(id,account,password));
            Cookie cookie=new Cookie("Token",token);
            resp.addCookie(cookie);
            //把username返回值保存到session当前会话中，名称为an，只要当前会话不过期，任何地方都可以访到
            req.getSession().setAttribute("an",account);
            req.getSession().setAttribute("password",newpwd);
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
        MD5 md5 = new MD5();
        String newpwd = md5.generateMD5(password);
//        System.out.println(code + "  " + codeac);
        UserService ps = new UserServiceImpl();
        if(!code.equals(codeac)){
            out.write(JsonUtil.toJson(new Result(false,400,"验证码错误",null)));
        }else{
            user.setUser_id(Integer.parseInt(id));
            user.setPassword(newpwd);
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
        EmailUtil.sendEmail(user,rjcode);
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
        System.out.println(getuser);
        User user = new User();
        user.setEmail(getuser.getEmail());
        user.setPassword(MD5.generateMD5(getuser.getPassword()));
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
                //将userid存到session域中
                request.getSession().setAttribute("uuid",id);
                out.write(JsonUtil.toJson(new Result(true, 200, "jwt验证成功", user)));
            } else {
                out.write(JsonUtil.toJson(new Result(false, 400, "jwt验证失败", null)));
            }
        } else {
            out.write(JsonUtil.toJson(new Result(false, 400, "jwt验证失败", null)));
        }
    }

    /**
     * 获取他人基本信息
     */
    protected void getOtherMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        Integer otherid = getuser.getUser_id();
        System.out.println(otherid);
        UserService userService = new UserServiceImpl();
        User user = userService.selectOfUserId(otherid);
        System.out.println(user.getUser_name());
        out.write(ResultUtil.ac("获取到他人信息",user));
    }

    /**
     * 上传头像
     */
    protected void updatePhoto(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        String s = reader.readLine();
//        JSONObject jsonObject = JSONObject.parseObject(s);
//        String photo = jsonObject.getString("user_photo");
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        System.out.println(getuser);
        System.out.println(getuser.getUser_photo());
//        System.out.println("66" + request.getParameter("user_photo"));
        User user = new User();
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        user.setUser_id(id);
        user.setUser_photo(getuser.getUser_photo());
        UserService userServlet = new UserServiceImpl();
        if(userServlet.updateHeaderImg(user)){
            out.write(ResultUtil.ac("上传成功",null));
        }else {
            out.write(ResultUtil.wa("上传失败",null));
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
        System.out.println(getuser.getUser_photo());
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
     * 关注
     */
    protected void concern(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Fans getuser = JSON.parseObject(s, Fans.class);
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        int focusid = Integer.parseInt(request.getParameter("fans_focusid"));
        System.out.println(id+" 关注 "+focusid);
        Fans fans = new Fans();
        fans.setFans_focusid(focusid);
        fans.setFans_id(id);
        UserService userService = new UserServiceImpl();
        if(userService.selectConcernState(fans)){ //表里已有关系数据，只需要修改状态
            if(userService.updateConcernACC(focusid,id)){
                out.write(ResultUtil.ac("已关注",null));
            }else{
                out.write(ResultUtil.wa("关注失败",null));
            }
        }else {
            if(userService.updateConcernAC(focusid,id)){
                out.write(ResultUtil.ac("已关注",null));
            }else{
                out.write(ResultUtil.wa("关注失败",null));
            }
        }

    }

    /**
     * 取消关注
     */
    protected void cancelConcern(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Fans getuser = JSON.parseObject(s, Fans.class);
        Integer id = (Integer) request.getSession().getAttribute("uuid");
//        int focusid = getuser.getFans_focusid();
        int focusid = Integer.parseInt(request.getParameter("fans_focusid"));
        System.out.println(id+" 取消关注 "+focusid);
        UserService userService = new UserServiceImpl();
        if(userService.updateConcernWA(focusid,id)){
            out.write(ResultUtil.ac("已取消关注",null));
        }else{
            out.write(ResultUtil.wa("取消失败",null));
        }
    }

    /**
     * 获取关注列表
     */
    protected void getConcernList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Fans getuser = JSON.parseObject(s, Fans.class);
        Integer userid = (Integer) request.getSession().getAttribute("uuid");
        List<Fans> concernlist=new ArrayList<>();
        UserService userService = new UserServiceImpl();
        concernlist = userService.selectConcernList(userid);
        concernlist.forEach(list->{
            if(list.getFans_fdetail()==null){
                list.setFans_fdetail("这个人很懒,什么也没留下");
            }
            if(list.getFans_fophoto()==null){
                list.setFans_fophoto("../img/images/默认头像.png");
            }
            list.setFans_type(1);
        });
        out.write(ResultUtil.ac("获取到关注列表",concernlist));
    }

    /**
     * 获取粉丝列表
     */
    protected void getFansList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Fans getuser = JSON.parseObject(s, Fans.class);
        Integer userid = (Integer) request.getSession().getAttribute("uuid");
        List<Fans> concernlist=new ArrayList<>();
        UserService userService = new UserServiceImpl();
        concernlist = userService.selectFansList(userid);
        concernlist.forEach(list->{
            if(list.getFans_detail()==null){
                list.setFans_detail("这个人很懒,什么也没留下");
            }
            if(list.getFans_photo()==null){
                list.setFans_photo("../img/images/默认头像.png");
            }
            list.setFans_type(1);
        });
        out.write(ResultUtil.ac("获取到粉丝列表",concernlist));
    }

    /**
     *获取他人关注列表
     */
    protected void get_invite_following_list(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        PrintWriter out = response.getWriter();
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Fans getuser = JSON.parseObject(s, Fans.class);
        Integer userid = getuser.getFans_id();
        List<Fans> other_concernlist=new ArrayList<>();
        UserService userService = new UserServiceImpl();
        other_concernlist = userService.selectOtherConcernList(userid);
//        System.out.println("好呗"+search.size());
        out.write(ResultUtil.ac("获取到他人关注列表",other_concernlist));
    }

//    /**
//     * 获取他人粉丝列表
//     */
//    protected void get_invite_fans_list(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
//       PrintWriter out = response.getWriter();
//        //通过流获取前端传参
//        BufferedReader reader = request.getReader();
//        String s = reader.readLine();
//        Fans getuser = JSON.parseObject(s, Fans.class);
//        Integer userid = getuser.getFans_id();
//        List<Fans> other_concernlist=new ArrayList<>();
//        UserService userService = new UserServiceImpl();
//        other_concernlist = userService.selectFansList(userid);
////        System.out.println("好呗"+search.size());
//        out.write(ResultUtil.ac("获取到他人关注列表",other_concernlist));
//    }

    /**
     * 获取全部用户列表
     */
    protected void getAllUserList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        ArrayList<User> userlist=new ArrayList<>();
        UserService userService = new UserServiceImpl();
        userlist = userService.selectAllUserInfo();
//        System.out.println("好呗"+userlist.size());
        out.write(ResultUtil.ac("获取全部用户",userlist));
    }

    /**
     * 获取全部歌手列表
     */
    protected void getAllSingerList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        ArrayList<User> userlist=new ArrayList<>();
        UserService userService = new UserServiceImpl();
        userlist = userService.selectAllSingerInfo();
//        System.out.println("好呗"+userlist.size());
        out.write(ResultUtil.ac("获取全部歌手",userlist));
    }

    /**
     * 封号
     */
    protected void forbidAccount(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        PrintWriter out = response.getWriter();
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        User user = new User();
        user.setUser_id(getuser.getUser_id());
        UserService userService = new UserServiceImpl();
        if(userService.forbidAccount(user)){
            out.write(ResultUtil.ac("已封号",null));
        }else{
            out.write(ResultUtil.wa("未能封号",null));
        }
    }

    /**
     * 解封
     */
    protected void unserAccount(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        PrintWriter out = response.getWriter();
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        User getuser = JSON.parseObject(s, User.class);
        User user = new User();
        user.setUser_id(getuser.getUser_id());
        UserService userService = new UserServiceImpl();
        if(userService.unserAccount(user)){
            out.write(ResultUtil.ac("已解封",null));
        }else{
            out.write(ResultUtil.wa("未能解封",null));
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
