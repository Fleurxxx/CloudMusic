package com.entity;

public class Fans {

    private int fans_id;
    private int fans_name;
    private String fans_photo;
    private int fans_focusid;
    private int fans_type;


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

    public int getFans_name() {
        return fans_name;
    }

    public void setFans_name(int fans_name) {
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
