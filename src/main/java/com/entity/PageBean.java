package com.entity;

public class PageBean {

    private int total; //数据表中总记录数
    private int listRows; //每页显示行数
    private int limit; //SQL语句使用limit从句,限制获取记录个数
    private String url; //自动获取url的请求地址
    private int pageNum; //总页数
    private int page;	//当前页

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getListRows() {
        return listRows;
    }

    public void setListRows(int listRows) {
        this.listRows = listRows;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PageBean(int total, int listRows, int limit, String url, int pageNum, int page) {
        this.total = total;
        this.listRows = listRows;
        this.limit = limit;
        this.url = url;
        this.pageNum = pageNum;
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "total=" + total +
                ", listRows=" + listRows +
                ", limit=" + limit +
                ", url='" + url + '\'' +
                ", pageNum=" + pageNum +
                ", page=" + page +
                '}';
    }
}
