package com;

import com.dao.BaseDao;

public class deleteSql {
    public static void main(String[] args){
        String sql = "update mv set title=? where id=?";//改
//        String sql = "delete from mv where id=?";//删
        Object[] obj = {"什么",123};
        BaseDao bd = new BaseDao();
        int result = bd.modifyData(sql,obj);
        if(result>0){
            System.out.println("修改成功");
        }

        //查询所有
//        BaseDao bd = new BaseDao();
//        String sql = "select * from mv";
//        Object[] obj = {};
//        ResultSet rs = bd.getDataByAny(sql,obj);
//        while(true){
//            try {
//                if (!rs.next()) break;
//                System.out.println(rs.getString(1)+"---"+rs.getString(2));
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    }
}
