package com.entity;

public class SearchUser {
    private String headimg;
    private String searchname;//被搜用户昵称
    private String searchid;//被搜用户id
    private String detail;//被搜用户简介
    private String focusid;//被搜用户与用户之间的状态
    private String fans;
    private String conn;
    private Integer nowpage;
    private Integer uuid;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getNowpage() {
        return nowpage;
    }

    public void setNowpage(Integer nowpage) {
        this.nowpage = nowpage;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }

    public String getSearchid() {
        return searchid;
    }

    public void setSearchid(String searchid) {
        this.searchid = searchid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFocusid() {
        return focusid;
    }

    public void setFocusid(String focusid) {
        this.focusid = focusid;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }
}
