package com.service;

import com.entity.User;

public interface UserService {
    //登录（根据账号、密码查找）
    boolean login(String an,String pwd);
    //注册（添加用户）
    boolean register(User user);

}
