package com.entity;

/**
 * 验证用户密码类
 */
public class VerifyUser {

    private Integer userid;
    private String username;
    private String password;

    public VerifyUser(Integer userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "VerifyUser{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
