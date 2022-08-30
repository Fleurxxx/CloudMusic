package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.entity.Fans;
import com.entity.User;
import com.service.UserService;
import com.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao pd = new UserDaoImpl();
    //1.获取sqlSession对象
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    //2.获取Mapper接口的代理对象
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
        //3.执行方法
        int res = mapper.updatePwd(user);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println(flag);
        //4.提交事务
        sqlSession.commit();
        //5.释放资源
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
    public boolean updateHeaderImg(User user) {
        int res = mapper.updateHeaderImg(user);
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
    public boolean selectConcernState(Fans fans) {
        boolean flag;
        if(mapper.selectConcernState(fans)){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateConcernAC(Integer focusid, Integer userid) {
        int res = mapper.updateConcernAC(focusid,userid);
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
    public boolean updateConcernACC(Integer focusid, Integer userid) {
        int res = mapper.updateConcernACC(focusid,userid);
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
    public boolean updateConcernWA(Integer focusid, Integer userid) {
        int res = mapper.updateConcernWA(focusid,userid);
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
    public List<Fans> selectConcernList(Integer userid) {
        List<Fans> list = mapper.selectConcernList(userid);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Fans> selectFansList(Integer userid) {
        List<Fans> list = mapper.selectFansList(userid);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Fans> selectOtherConcernList(Integer userid) {
        List<Fans> list = mapper.selectFansList(userid);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Fans> selectOtherFansList(Integer userid) {
        List<Fans> list = mapper.selectFansList(userid);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }


    @Override
    public ArrayList<User> selectAllUserInfo() {
        ArrayList<User> list = mapper.selectAllUserInfo();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public ArrayList<User> selectAllSingerInfo() {
        ArrayList<User> list = mapper.selectAllSingerInfo();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public boolean forbidAccount(User user) {
        int res = mapper.forbidAccount(user);
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
    public boolean unserAccount(User user) {
        int res = mapper.unserAccount(user);
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
    public ArrayList<User> selectAllUserInfoLimit(int startRowNum, int showRowsNum) {
        return null;
    }

    @Override
    public boolean updateUserStateId(int userStateId, int userId) {
        return false;
    }
}
