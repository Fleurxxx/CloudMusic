package com.servlet;

import com.alibaba.fastjson.JSON;
import com.entity.SearchUser;
import com.entity.Song;
import com.service.SearchService;
import com.service.impl.SearchServiceImpl;
import com.util.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchServlet")
public class SearchServlet extends BaseServlet {


    /**
     * 搜索相关用户
     */
    protected void searchuser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("搜索用户请求过来了");
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        SearchUser getuser = JSON.parseObject(s, SearchUser.class);
        SearchUser su = new SearchUser();
        su.setConn(getuser.getConn());
        su.setUuid(getuser.getUuid());
        su.setNowpage(getuser.getNowpage());
        System.out.println("搜索用户 "+su.getUuid() + " " + su.getConn() + " " + su.getNowpage());
        List<SearchUser>search=new ArrayList<>();
        SearchService searchService = new SearchServiceImpl();
        search= searchService.searchUser(su);
        search.forEach(list->{
            if(list.getDetail()==null){
                list.setDetail("这个人很懒,什么也没留下");
            }
            if(list.getFocusid()==null){
                list.setFocusid("0");
            }
            if(list.getHeadimg()==null){
                list.setHeadimg("../img/images/默认头像.png");
            }
//            System.out.println(list.getDetail());
//            System.out.println(list.getSearchid());
        });
        out.write(ResultUtil.ac("success",search));

    }


    /**
     * 搜索相关歌曲
     */
    protected void searchsong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("搜索歌曲请求过来了");
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        Song song = new Song();
        song.setConn(getsong.getConn());//搜索输入的关键字
        song.setType(getsong.getType()*7);//当前数据条数
        List<Song>search=new ArrayList<>();
        SearchService searchService = new SearchServiceImpl();
        search= searchService.searchSong(song);
        search.forEach(list->{
            System.out.println(list.getSong_name());
            System.out.println(list.getSong_path());
        });
        out.write(ResultUtil.ac("success",search));
    }


    /**
     * 搜索相关歌手
     */
    protected void searchsinger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("搜索歌手请求过来了");
        //通过流获取前端传参
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        SearchUser getuser = JSON.parseObject(s, SearchUser.class);
        SearchUser su = new SearchUser();
        su.setConn(getuser.getConn());
        su.setUuid(getuser.getUuid());
        su.setNowpage(getuser.getNowpage());
        System.out.println("搜索歌手 "+su.getUuid() + " " + su.getConn() + " " + su.getNowpage());
        List<SearchUser>search=new ArrayList<>();
        SearchService searchService = new SearchServiceImpl();
        search= searchService.searchSinger(su);
        search.forEach(list->{
            if(list.getDetail()==null){
                list.setDetail("这个人很懒,什么也没留下");
            }
            if(list.getFocusid()==null){
                list.setFocusid("0");
            }
            if(list.getHeadimg()==null){
                list.setHeadimg("../img/images/默认头像.png");
            }
//            System.out.println(list.getDetail());
//            System.out.println(list.getSearchid());
        });
        out.write(ResultUtil.ac("success",search));
    }




    /**
     * 搜索相关歌单
     */
    protected void searchsonglist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
