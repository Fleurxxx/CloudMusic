package com.servlet;

import com.entity.Song;
import com.service.SongService;
import com.service.impl.SongServiceImpl;
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

@WebServlet("/msgUploadServlet")
public class MsgUploadServlet extends HttpServlet {

    /**
     *上传歌词/封面
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
                    //文件保存地址
                    filedName = "C:\\Users\\86183\\IdeaProjects\\untitled4\\src\\main\\webapp\\music\\mp3\\"+filename;
                    Integer id = (Integer) request.getSession().getAttribute("uuid");
                    System.out.println(id);
                    Song song = new Song();
                    song.setSong_singerid(id);
                    if(houzhui.equals("mp3") || houzhui.equals("ogg")){
                        System.out.println("是ogg");
                        String src = "..\\music\\mp3\\"+filename;
                        String songid = filename.substring(0, filename.lastIndexOf("."));
                        song.setSong_path(src);
                        song.setSong_id(songid);
                        song.setSong_state(0);
                        System.out.println("上传成的歌曲路径："+src);
                        System.out.println("上传成的歌曲id："+songid);
                        SongService songService = new SongServiceImpl();
                        if(songService.updateSong(song)){
                            response.getWriter().write(ResultUtil.ac("上传成功",null));
                        }else {
                            response.getWriter().write(ResultUtil.wa("上传失败",null));
                        }
                    }else if(houzhui.equals("jpg") || houzhui.equals("png")){
                        String src = "..\\img\\cover\\"+filename;
                        song.setSong_path(src);
                        System.out.println("上传的封面路径："+src);
                        SongService songService = new SongServiceImpl();
                        if(songService.updateSongImg(song)){
                            response.getWriter().write(ResultUtil.ac("上传成功",null));
                        }else {
                            response.getWriter().write(ResultUtil.wa("上传失败",null));
                        }
                    }else if(houzhui.equals("lrc")){
                        String src = "..\\music\\lrc\\"+filename;
                        song.setLyric_path(src);
                        System.out.println("上传的歌词路径："+src);
                        SongService songService = new SongServiceImpl();
                        if(songService.updateSongLrc(song)){
                            response.getWriter().write(ResultUtil.ac("上传成功",null));
                        }else {
                            response.getWriter().write(ResultUtil.wa("上传失败",null));
                        }
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
}
