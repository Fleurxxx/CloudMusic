package com.servlet;

import com.alibaba.fastjson.JSON;
import com.entity.Song;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet("/fileDownServlet")
public class FileDownServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BufferedReader reader = request.getReader();
//        String s = reader.readLine();
//        Song getuser = JSON.parseObject(s, Song.class);
//        //1、获取下载文件的路径(不太对，需要后续拼接）
//        String fName = getuser.getSong_path();
//        File tempFile =new File(fName.trim());
//        //2、下载的文件名
//        String fileName = tempFile.getName();
        String fileName = request.getParameter("fileName");
        System.out.println("fileName = " + fileName);
        String uploadPath="C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\ogg";
        InputStream in;
        BufferedInputStream bis;
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/force-download");
            //3、设置javaweb下载设置响应头--想办法让浏览器能够支持下载我们需要的东西，
            //设置响应头以下载的方式返回到浏览器,通过URLEncoder进行编码转换，使其可以识别中文
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            //4、获取下载文件的输入流（拼接的文件路径）
            in=new FileInputStream(uploadPath+File.separator+fileName);
            System.out.println(uploadPath+File.separator+fileName);
            byte[] buffer=new byte[1024];
            //5、创建缓冲区
            int len=0;
            //6、获取OutputStream对象
            outputStream = response.getOutputStream();
            //7、将FileOutStream流写入到buffer缓冲区
            //使用OutputStream将缓冲区中的数据输出到客户端
            while((len=in.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
            //8.关闭流
            outputStream.flush();
            outputStream.close();
            in.close();
            System.out.println("下载成功");
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
