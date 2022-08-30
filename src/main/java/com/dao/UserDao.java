package com.dao;

import com.entity.Fans;
import com.entity.User;
import org.apache.ibatis.annotations.Param;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个接口负责所有user表增删改查操作
 */
public interface UserDao {

    public final String USERID = "user_id";
    public final String NAME = "user_name";
    public final String EMAIL = "email";
    public final String PASSWORD = "password";
    public final String BIRTH = "user_birth";
    public final String PHONE = "user_phone";
    public final String GENDER = "user_gender";
    public final String PHOTO = "user_photo";
    public final String DETAILS = "user_details";
    public final String DATE = "user_date";
    public final String TYPE = "user_type";
    public final String STATE = "user_state";

    /**
     *
     * @param an 账号
     * @param pwd 密码
     * @return
     */
    //登录（根据账号、密码查找）
    boolean login(String an, String pwd);

    //登录（根据手机号、密码查找）
    boolean loginByPhone(String phone, String pwd);

    //注册（添加用户）
    boolean register(User user);
    /*----------------------使用mybatis舍去以上操作--------------------------*/

    //查询（根据用户名称查找）用于Ajax判断此名称是否已经被使用
    Boolean checkUserName(String userName);

    //查询（根据邮箱查找）用于Ajax判断此邮箱是否已经被使用
    Boolean checkEmail(String email);

    //查询（根据手机号查询）用于Ajax判断此手机号是否被绑定
    boolean checkPhone(String phone);

    //查询全部用户
    List<User> getUserList();

    //根据email查询用户id
    int getIdByEmail(String email);

    //根据ID查询用户
    User getUserById(int id);

    //插入一个用户
    int addUser(User user);

    //修改密码
    int updatePwd(User user);

    //上传头像
    int updateHeaderImg(User user);

    //修改用户信息
    int updateUser(User user);

    //删除用户信息
    int deleteUser(User user);

    //查询，判断是否关注过
    boolean selectConcernState(Fans fans);

    //添加（关注状态）关注
    int updateConcernAC(@Param("focusid") Integer focusid,@Param("userid") Integer userid);

    //添加（关注状态）关注
    int updateConcernACC(@Param("focusid") Integer focusid,@Param("userid") Integer userid);

    //修改（关注状态）取消关注
    int updateConcernWA(@Param("focusid") Integer focusid,@Param("userid") Integer userid);

    //根据用户id查询关注列表
    List<Fans> selectConcernList(Integer userid);//userid是粉丝id

    //根据用户id查询粉丝列表
    List<Fans> selectFansList(Integer userid);//userid是被关注者id

    //根据他人用户id查询他人关注列表
    List<Fans> selectOtherConcernList(Integer userid);

    //根他人据用户id查询他人粉丝列表
    List<Fans> selectOtherFansList(Integer userid);

    //模糊搜索
    List<User> getUserLike(String value);

    //无条件查询用户
    ArrayList<User> selectAllUserInfo();

    //无条件查询歌手
    ArrayList<User> selectAllSingerInfo();

    //封号
    int forbidAccount(User user);

    //封号
    int unserAccount(User user);

    //查询用户名字
    String getUsername(String name);
}
