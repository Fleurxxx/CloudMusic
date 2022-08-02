package com.service;

import com.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface UserService {
    //查询（根据账号、密码查找）账号登录
    boolean login(String an,String pwd);
    //查询（根据手机号、密码登录）手机号登录
    boolean loginByPhone(String phone,String pwd);
    //添加（添加用户）注册
    boolean register(User user);
    //查询（根据用户名称查找）用于Ajax判断此名称是否已经被使用
    boolean checkName(String username);
    //查询（根据用户账号查找）用于Ajax判断账号是否已经被注册
    boolean checkLoginId(String loginId);
    //查询（根据手机号查询）用于Ajax判断此手机号是否被绑定
    boolean checkPhone(String phone);
    //查询（根据邮箱）用于Ajax查询
    boolean checkEmail(String email);
    //查询，根据用户id查询用户信息
    int selectOfEmail(String email);
    //查询，根据用户id查询用户信息
    User selectOfUserId(int userId);
    //修改（用户修改自己信息）
    boolean updatePwd(User user);
    //修改（用户修改自己信息）
    boolean updateUser(User user);
    //修改/上传用户头像
    boolean updateHeaderImg(String headSculptureUrl, int userId);
    //无条件查询用户（用于计算用户个数）
    ArrayList<User> selectAllUserInfo();
    //分页查询所有用户账号信息
    ArrayList<User> selectAllUserInfoLimit(int startRowNum, int showRowsNum);
    //修改用户userStateId值
    boolean updateUserStateId(int userStateId, int userId);
}
