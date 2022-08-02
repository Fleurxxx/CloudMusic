package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.entity.User;
import com.service.UserService;
import com.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private UserDao pd = new UserDaoImpl();
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDao mapper = sqlSession.getMapper(UserDao.class);
    @Override
    public boolean login(String an, String pwd) {
        return pd.login(an,pwd);
    }

    @Override
    public boolean loginByPhone(String phone, String pwd) {
        return false;
    }

    @Override
    public boolean register(User user) {
        return pd.register(user);
    }

    @Override
    public boolean checkName(String username) {
        boolean flag;
        if(mapper.checkUserName(username)){
            flag=false;
        }else{
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean checkEmail(String email) {
        boolean flag;
        if(mapper.checkUserName(email)){
            flag=false;
        }else{
            flag=true;
        }
        return flag;
    }

    @Override
    public int selectOfEmail(String email) {
        return mapper.getIdByEmail(email);
    }

    @Override
    public boolean checkLoginId(String loginId) {
        return false;
    }

    @Override
    public boolean checkPhone(String phone) {
        return false;
    }

    @Override
    public User selectOfUserId(int userId) {
        User user = mapper.getUserById(userId);
        System.out.println(user);
        sqlSession.close();
        return user;
    }

    @Override
    public boolean updatePwd(User user) {
        int res = mapper.updatePwd(user);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateUser(User user) {
        int res = mapper.updateUser(user);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateHeaderImg(String headSculptureUrl, int userId) {
        return false;
    }

    @Override
    public ArrayList<User> selectAllUserInfo() {
        return null;
    }

    @Override
    public ArrayList<User> selectAllUserInfoLimit(int startRowNum, int showRowsNum) {
        return null;
    }

    @Override
    public boolean updateUserStateId(int userStateId, int userId) {
        return false;
    }
}
