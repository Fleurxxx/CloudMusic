package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.Song;
import com.entity.User;
import com.service.SongService;
import com.service.UserService;
import com.service.impl.SongServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.GetID;
import com.util.ResultUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/fileUploadServlet")
public class FileUploadServlet extends HttpServlet{

    /**
     *上传音乐
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024*1024*500);/*限制文件大小500MB*/
        try {
            List<FileItem> listFile = upload.parseRequest(request);
            for (FileItem item : listFile) {
                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString();
                    System.out.println(name + ":" + value);
                }else{
                    String filedName = item.getFieldName();
                    String filename = item.getName();
                    String filetype = item.getContentType();
                    String houzhui = filename.substring(filename.lastIndexOf(".")+1);
                    long size = item.getSize();
                    System.out.println("后缀："+houzhui);
//                    System.out.println(filedName);
//                    System.out.println(filename);
//                    System.out.println(filetype);
//                    System.out.println(size);
                    Integer id = (Integer) request.getSession().getAttribute("uuid");
                    System.out.println(id);
                    Song song = new Song();
                    song.setSong_singerid(id);
                    if(houzhui.equals("mp3") || houzhui.equals("ogg")){
                        //文件保存地址
                        filedName = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\mp3\\"+filename;
                        String src = "..\\music\\mp3\\"+filename;
                        String songid = filename.substring(0, filename.lastIndexOf("."));
                        //将songid存到session域中
                        request.getSession().setAttribute("ssid",songid);
                        song.setSong_path(src);
                        song.setSong_id(songid);
                        song.setSong_state(0);
                        System.out.println("上传成的歌曲路径："+src);
                        System.out.println("上传成的歌曲id："+songid);
                        SongService songService = new SongServiceImpl();
//                        if(songService.updateSong(song)){
//                            response.getWriter().write(ResultUtil.ac("上传成功",null));
//                        }else {
//                            response.getWriter().write(ResultUtil.wa("上传失败",null));
//                        }
                        response.getWriter().write(ResultUtil.ac("上传歌曲成功",src));
                    }else if(houzhui.equals("jpg") || houzhui.equals("png")){
                        //文件保存地址
                        filedName = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\img\\cover\\"+filename;
                        String src = "..\\img\\cover\\"+filename;
                        song.setSong_path(src);
                        String songid = (String) request.getSession().getAttribute("ssid");
                        song.setSong_id(songid);
                        System.out.println("上传的封面路径："+src);
                        SongService songService = new SongServiceImpl();
//                        if(songService.updateSongImg(song)){
//                            response.getWriter().write(ResultUtil.ac("上传成功",null));
//                        }else {
//                            response.getWriter().write(ResultUtil.wa("上传失败",null));
//                        }
                        response.getWriter().write(ResultUtil.ac("上传封面成功",src));
                    }else if(houzhui.equals("lrc")){
                        //文件保存地址
                        filedName = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\lrc\\"+filename;
                        String src = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\lrc\\"+filename;
                        song.setLyric_path(src);
                        String songid = (String) request.getSession().getAttribute("ssid");
                        song.setSong_id(songid);
                        System.out.println("上传的歌词路径："+src);
                        SongService songService = new SongServiceImpl();
//                        if(songService.updateSongLrc(song)){
//                            response.getWriter().write(ResultUtil.ac("上传成功",null));
//                        }else {
//                            response.getWriter().write(ResultUtil.wa("上传失败",null));
//                        }
                        response.getWriter().write(ResultUtil.ac("上传歌词成功",src));
                    }
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
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
//
//    //没用了，下载不能用axios发送请求，只能表单提交
//    protected void songsdown(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BufferedReader reader = request.getReader();
//        String s = reader.readLine();
//        Song getuser = JSON.parseObject(s, Song.class);
//        //1、获取下载文件的路径(不太对，需要后续拼接）
//        String fName = getuser.getSong_path();
//        File tempFile =new File(fName.trim());
//        //2、下载的文件名
//        String fileName = tempFile.getName();
//        System.out.println("fileName = " + fileName);
//        String uploadPath="C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\ogg";
//        InputStream in;
//        BufferedInputStream bis;
//        ServletOutputStream outputStream = null;
//        try {
//            response.setHeader("content-type","application/force-download");
//            //3、设置javaweb下载设置响应头--想办法让浏览器能够支持下载我们需要的东西，
//            //设置响应头以下载的方式返回到浏览器,通过URLEncoder进行编码转换，使其可以识别中文
//            response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
//            //4、获取下载文件的输入流（拼接的文件路径）
//            in=new FileInputStream(uploadPath+File.separator+fileName);
//            System.out.println(uploadPath+File.separator+fileName);
//            byte[] buffer=new byte[1024];
//            //5、创建缓冲区
//            int len=0;
//            //6、获取OutputStream对象
//            outputStream = response.getOutputStream();
//            //7、将FileOutStream流写入到buffer缓冲区
//            //使用OutputStream将缓冲区中的数据输出到客户端
//            while((len=in.read(buffer))!=-1){
//                outputStream.write(buffer,0,len);
//            }
//            //8.关闭流
//            outputStream.flush();
//            outputStream.close();
//            in.close();
//            System.out.println("下载成功");
//        } catch (FileNotFoundException e) {
//            System.out.println("文件未找到");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
