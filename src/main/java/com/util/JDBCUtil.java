package src.main.java.com.util;

import org.springframework.beans.factory.SmartInitializingSingleton;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil{
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    /**
     * 文件的读取，只需要读取一次即可拿到这些值，使用静态代码块。
     */
    static{//读取资源文件，获取值
        try {
            //1.创建Properties集合类
            Properties pro = new Properties();
//            //获取src路径（绝对路径）下的文件方式-->Classloader类加载器
//            ClassLoader classLoader = JDBCUtil.class.getClassLoader();
//            URL res = classLoader.getResource("sql.properties");
//            System.out.println(res + "hhh");
//            String path = res.getPath();
//            System.out.println(path);
            //2.加载文件
            pro.load(new FileReader("C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\sql.properties"));
//            pro.load(new FileReader(path));
            //3.获取数据，赋值
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            //4.注册驱动
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*获取连接对象*/
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    /*释放资源*/
    public static void close(Statement stmt, Connection conn){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*释放资源(重载)*/
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
