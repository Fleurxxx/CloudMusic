package com.entity;

public class Fans {

    private int fans_id;
    private String fans_name;
    private String fans_photo;
    private int fans_focusid;
    private String fans_foname;
    private String fans_fophoto;
    private int fans_type;

    private String fans_detail;//粉丝简介
    private String fans_fdetail;//被关注者简介

    public String getFans_detail() {
        return fans_detail;
    }

    public void setFans_detail(String fans_detail) {
        this.fans_detail = fans_detail;
    }

    public String getFans_fdetail() {
        return fans_fdetail;
    }

    public void setFans_fdetail(String fans_fdetail) {
        this.fans_fdetail = fans_fdetail;
    }

    public String getFans_foname() {
        return fans_foname;
    }

    public void setFans_foname(String fans_foname) {
        this.fans_foname = fans_foname;
    }

    public String getFans_fophoto() {
        return fans_fophoto;
    }

    public void setFans_fophoto(String fans_fophoto) {
        this.fans_fophoto = fans_fophoto;
    }

    public int getFans_id() {
        return fans_id;
    }

    public void setFans_id(int fans_id) {
        this.fans_id = fans_id;
    }

    public int getFans_type() {
        return fans_type;
    }

    public void setFans_type(int fans_type) {
        this.fans_type = fans_type;
    }

    public int getFans_focusid() {
        return fans_focusid;
    }

    public void setFans_focusid(int fans_focusid) {
        this.fans_focusid = fans_focusid;
    }

    public String getFans_photo() {
        return fans_photo;
    }

    public void setFans_photo(String fans_photo) {
        this.fans_photo = fans_photo;
    }

    public String getFans_name() {
        return fans_name;
    }

    public void setFans_name(String fans_name) {
        this.fans_name = fans_name;
    }

    @Override
    public String toString() {
        return "Fans{" +
                "fans_id=" + fans_id +
                ", fans_name=" + fans_name +
                ", fans_photo='" + fans_photo + '\'' +
                ", fans_focusid=" + fans_focusid +
                ", fans_type=" + fans_type +
                '}';
    }
}
