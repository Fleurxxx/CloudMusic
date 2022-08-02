package com.dao;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import src.main.java.com.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 只有登录注册用到了
 */
public class BaseDao {
    //增删改的必要条件
    //sql语句
    //需要增删改的位置条件
    public int modifyData(String sql, Object[] obj){
        Connection conn = null;
        int result = 0;
        try {
            conn = JDBCUtil.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i=0; i<obj.length; i++){
                ps.setObject(i+1, obj[i]);
            }
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    //查询
    public ResultSet getDataByAny(String sql, Object[] obj){
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i=0; i<obj.length; i++){
                ps.setObject(i+1,obj[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }
}
