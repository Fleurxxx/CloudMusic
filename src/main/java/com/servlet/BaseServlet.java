package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    /*
     * init用于servlet初始化时调用，只调用一次，后续再次请求将不会再次调用init
     * service：每次请求servlet时都会调用service
     * service方法会调用doGet、doPost方法（HTTPServlet重写了service方法调用了doGet、doPost）
     * 重写了Service就可以不重写doGet、doPost，直接在service中进行了处理
     *
     * 继承BaseServlet的子类servlet会自动调用service方法
     * */

    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取传来的方法名字
        String methodName = req.getParameter("method");
        System.out.println("调用方法："+methodName);
        Method method = null;
        //判断用户有没有传入参数。
        if(methodName == null || methodName.trim().length() == 0) {
//            methodName = "index";
            throw new RuntimeException("您没有传递method参数,无法确定您要调用的方法！");
        }
        // 当请求参数不为空的时候
        if(methodName != null) {
            // 利用java的反射机制，找到对应的方法，并获取到方法的返回值，如果返回为一个路径，那么进行统一的处理
            try {

                /* 获取指定类的字节码对象
                 * Class<? extends BaseServlet> aClass = this.getClass();
                 * this.getClass()获取当前servlet类的class类对象，
                 * 进行了泛型约束Class<? extends BaseServlet>， 获取的是BaseServlet或者其子类的类对象
                 */
                /* 通过类的字节码对象获取方法的字节码对象
                 * getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
                 * 获取方法对象（method类对象），需要的参数：需要获取的方法的方法名、HttpServletRequest.class, HttpServletResponse.class
                 */
                Class<? extends BaseServlet> aClass = this.getClass();
                method = aClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                /* 让方法执行
                 * this：当前类，使用service方法的类，这里是继承BaseServlet的类
                 * method.invoke在this这个类中调用method方法
                 */
                method.invoke(this, req, resp);
//                if(result != null && !result.trim().isEmpty()) {//如果请求处理方法返回不为空
//                    int index = result.indexOf(":");//获取第一个冒号的位置
//                    if(index == -1) {//如果没有冒号，使用转发
//                        req.getRequestDispatcher(result).forward(req, resp);
//                    } else {//如果存在冒号
//                        String start = result.substring(0, index);//分割出前缀
//                        String path = result.substring(index + 1);//分割出路径
//                        if(start.equals("f")) {//前缀为f表示转发
//                            req.getRequestDispatcher(path).forward(req, resp);
//                        } else if(start.equals("r")) {//前缀为r表示重定向
//                            resp.sendRedirect(req.getContextPath() + path);
//                        }
//                    }
//                }


            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
