package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.entity.User;
import com.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao pd = new UserDaoImpl();
    @Override
    public boolean login(String an, String pwd) {
        return pd.login(an,pwd);
    }

    @Override
    public boolean register(User user) {
        return pd.register(user);
    }
}
