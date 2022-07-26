package com.dao.impl;

import src.main.java.com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public boolean login(String an, String pwd) {
        String sql = "select * from user where email=? and password=?";
        ResultSet rs = this.getDataByAny(sql,new Object[] {an,pwd});
        boolean flag = false;
        while (true){
            try {
                if (!rs.next()) break;
                flag = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public boolean register(User user) {
        String sql = "insert into user (" +
                USERID + "," + EMAIL + "," + PASSWORD + "," + PHONE + "," + DATE +
                ") values(?,?,?,?,?)";
        int rs = this.modifyData(sql, new Object[]{user.getUser_id(),user.getEmail(),user.getPassword(),user.getUser_phone(),user.getUser_date()});
        boolean flag = false;
        if (rs > 0) {
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public List<User> getUserLike(String value) {
        return null;
    }
}
