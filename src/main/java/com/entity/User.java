package com.entity;


public class User {
    private int user_id;
    private int user_phone;
    private int user_type;
    private int user_state;
    private String user_name;
    private String email;
    private String password;
    private String user_gender;
    private String user_photo;
    private String user_detail;
    private String user_date;
    private String user_birth;


    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_state() {
        return user_state;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(int user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(String user_detail) {
        this.user_detail = user_detail;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_phone=" + user_phone +
                ", user_type=" + user_type +
                ", user_state=" + user_state +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_photo='" + user_photo + '\'' +
                ", user_detail='" + user_detail + '\'' +
                ", user_date='" + user_date + '\'' +
                ", user_birth='" + user_birth + '\'' +
                '}';
    }
}
