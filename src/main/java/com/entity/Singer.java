package com.entity;

public class Singer {
    private int singer_id;
    private int singer_type;//歌手类型
    private int singer_state;//状态，0正常，1被封
    private int access_count;//歌手热度
    private int singer_phone;
    private String singer_name;
    private String singer_photo;
    private String singer_detail;
    private String singer_brith;
    private String singer_sex;

    private int num_fans;
    private int num_focus;

    public int getSinger_phone() {
        return singer_phone;
    }

    public void setSinger_phone(int singer_phone) {
        this.singer_phone = singer_phone;
    }

    public int getNum_fans() {
        return num_fans;
    }

    public void setNum_fans(int num_fans) {
        this.num_fans = num_fans;
    }

    public int getNum_focus() {
        return num_focus;
    }

    public void setNum_focus(int num_focus) {
        this.num_focus = num_focus;
    }

    public int getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(int singer_id) {
        this.singer_id = singer_id;
    }

    public int getSinger_type() {
        return singer_type;
    }

    public void setSinger_type(int singer_type) {
        this.singer_type = singer_type;
    }

    public int getSinger_state() {
        return singer_state;
    }

    public void setSinger_state(int singer_state) {
        this.singer_state = singer_state;
    }

    public int getAccess_count() {
        return access_count;
    }

    public void setAccess_count(int access_count) {
        this.access_count = access_count;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getSinger_photo() {
        return singer_photo;
    }

    public void setSinger_photo(String singer_photo) {
        this.singer_photo = singer_photo;
    }

    public String getSinger_detail() {
        return singer_detail;
    }

    public void setSinger_detail(String singer_detail) {
        this.singer_detail = singer_detail;
    }

    public String getSinger_brith() {
        return singer_brith;
    }

    public void setSinger_brith(String singer_brith) {
        this.singer_brith = singer_brith;
    }

    public String getSinger_sex() {
        return singer_sex;
    }

    public void setSinger_sex(String singer_sex) {
        this.singer_sex = singer_sex;
    }
}
