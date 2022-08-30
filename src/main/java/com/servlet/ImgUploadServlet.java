package com.servlet;

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.GetID;
import com.util.ResultUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/imgUploadServlet")
public class ImgUploadServlet extends HttpServlet {

    /**
     *上传图片
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024*1024*30);/*限制文件大小10MB*/
        try {
            List<FileItem> listFile = upload.parseRequest(request);
            for (FileItem item : listFile) {
                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString();
                    System.out.println(name + ":" + value);
                }else{
                    String filedName = item.getFieldName();//fileInfo
                    String filename = item.getName();//文件名（带后缀
                    String filetype = item.getContentType();//类型/后缀
                    long size = item.getSize();
//                    System.out.println(filedName);
//                    System.out.println(filename);
//                    System.out.println(filetype);
//                    System.out.println(size);
                    //文件保存地址
                    filedName = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\img\\head\\"+filename;
                    // 封装文件
                    OutputStream out = new FileOutputStream(filedName);
                    InputStream in = item.getInputStream();//文件输入流
                    // 复制文件
                    byte[] buffer = new byte[1024];
                    int len = 0;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    // 释放资源
                    in.close();
                    out.close();
                    User user = new User();
                    Integer id = (Integer) request.getSession().getAttribute("uuid");
                    String src = "..\\img\\head\\"+filename;
                    user.setUser_id(id);
                    user.setUser_photo(src);
                    System.out.println("新生成的头像路径："+src);
                    UserService userServlet = new UserServiceImpl();
                    if(userServlet.updateHeaderImg(user)){
                        response.getWriter().write(ResultUtil.ac("上传成功",null));
                    }else {
                        response.getWriter().write(ResultUtil.wa("上传失败",null));
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

}
