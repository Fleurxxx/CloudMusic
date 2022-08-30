package com.servlet;

import com.alibaba.fastjson.JSON;
import com.entity.*;
import com.service.SongService;
import com.service.UserService;
import com.service.impl.SongServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.JsonUtil;
import com.util.ParseLrc;
import com.util.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/songServlet")
public class SongServlet extends BaseServlet {

    /**
     * 获取歌曲信息
     */
    protected void getSongMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        String songid = getsong.getSong_id();
        SongService songService = new SongServiceImpl();
        Song song = songService.selectOfSongId(songid);
        ParseLrc lrc = new ParseLrc();
        List<Map<String, String>> list = lrc.parse(song.getLyric_path());
        song.setLyric_path(lrc.readLrc(list));;
        out.write(ResultUtil.ac("获取到歌曲信息",song));
    }

    /**
     * 上传音乐
     */
    protected void upMusicMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        String singername = getsong.getSong_singername();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        song.setSong_name(getsong.getSong_name());
        song.setSong_singername(singername);
        song.setPhoto_path(getsong.getPhoto_path());
        song.setLyric_path(getsong.getLyric_path());
        song.setSong_path(getsong.getSong_path());
        System.out.println(getsong.getSong_name());
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        song.setSong_singerid(id);
        SongService songService = new SongServiceImpl();
        if(songService.addSongMsg(song)){
            out.write(ResultUtil.ac("提交信息成功",null));
        }else{
            out.write(ResultUtil.wa("提交信息失败",null));
        }
    }

    /**
     * 查找个人全部歌曲
     */
    protected void findMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        PageBean getpage = JSON.parseObject(s, PageBean.class);
        int page = getpage.getPage();
        int listRows = getpage.getListRows();
        int nowpage = page*listRows+1;
        if(page<0){
            nowpage = 1;
        }
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        System.out.println(page+" "+listRows);
        SongService songService = new SongServiceImpl();
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectSongsList(id,nowpage,listRows);
        System.out.println(songslist.size());
        songslist.forEach(list->{
            LoveSong loveSong = new LoveSong();
            loveSong.setUser_id(id);
            loveSong.setSong_id(Long.parseLong(list.getSong_id().trim()));
            SongService ss = new SongServiceImpl();
            if(ss.selectLoveStateNow(loveSong)){
//                System.out.println("1");
                list.setConn("1");//区别已经喜欢
            }else{
//                System.out.println("0");
                list.setConn("0");//未喜欢
            }
        });
        out.write(ResultUtil.ac("查找歌曲成功",songslist));
    }


    /**
     * 查找他人全部歌曲
     */
    protected void findOtherMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        Integer id = getsong.getSong_singerid();
        System.out.println(id);
        SongService songService = new SongServiceImpl();
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectSongsList(id,1,20);
        out.write(ResultUtil.ac("查找歌曲成功",songslist));
    }

    /**
     * 查找喜欢全部歌曲
     */
    protected void findLoveMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        PageBean getpage = JSON.parseObject(s, PageBean.class);
        int page = getpage.getPage();
        int listRows = getpage.getListRows();
        int nowpage = page*listRows+1;
        if(page<0){
            nowpage = 1;
        }
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        System.out.println(id);
        SongService songService = new SongServiceImpl();
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectLoveSongsList(id);
        out.write(ResultUtil.ac("查找歌曲成功",songslist));
    }

    /**
     * 查找待审核的全部歌曲
     */
    protected void findUncheckedMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        SongService songService = new SongServiceImpl();
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectUncheckeSongsList();
        out.write(ResultUtil.ac("查找待审核歌曲成功",songslist));
    }

    /**
     * 查找已审核的全部歌曲
     */
    protected void findPassMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        SongService songService = new SongServiceImpl();
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectPassSongsList();
        out.write(ResultUtil.ac("查找已审核歌曲成功",songslist));
    }

    /**
     * 通过审核
     */
    protected void PassVerification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
//        long songid = Long.parseLong(getsong.getSong_id());
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.passVerification(song)){
            out.write(ResultUtil.ac("审核通过",null));
        }else{
            out.write(ResultUtil.wa("审核不通过",null));
        }
    }

    /**
     * 取消通过审核
     */
    protected void cancelPassVerification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
//        long songid = Long.parseLong(getsong.getSong_id());
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.cancelPassVerification(song)){
            out.write(ResultUtil.ac("审核通过",null));
        }else{
            out.write(ResultUtil.wa("审核不通过",null));
        }
    }

    /**
     *  歌曲限流
     */
    protected void CurrentLimiting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.currentLimiting(song)){
            out.write(ResultUtil.ac("限流成功",null));
        }else{
            out.write(ResultUtil.wa("限流失败",null));
        }
    }

    /**
     *  取消歌曲限流
     */
    protected void cancelCurrentLimiting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.cancelCurrentLimiting(song)){
            out.write(ResultUtil.ac("取消限流成功",null));
        }else{
            out.write(ResultUtil.wa("取消限流失败",null));
        }
    }

    /**
     *  歌曲禁止上架
     */
    protected void BanSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.banSong(song)){
            out.write(ResultUtil.ac("下架成功",null));
        }else{
            out.write(ResultUtil.wa("下架失败",null));
        }
    }

    /**
     *  取消歌曲禁止上架
     */
    protected void cancelBanSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.cancelBanSong(song)){
            out.write(ResultUtil.ac("上架成功",null));
        }else{
            out.write(ResultUtil.wa("上架失败",null));
        }
    }

    /**
     *  推荐音乐
     */
    protected void recommendSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.recommendSong(song)){
            out.write(ResultUtil.ac("推荐音乐成功",null));
        }else{
            out.write(ResultUtil.wa("推荐音乐失败",null));
        }
    }

    /**
     *  取消推荐音乐
     */
    protected void cancelRecommendSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.cancelRecommendSong(song)){
            out.write(ResultUtil.ac("取消推荐音乐成功",null));
        }else{
            out.write(ResultUtil.wa("取消推荐音乐失败",null));
        }
    }



    /**
     * 获取推荐歌曲列表
     */
    protected void recommendSongs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        SongService songService = new SongServiceImpl();
        List<Song> songslist = new ArrayList<>();
        songslist = songService.selectreCommendSongsList();
        Integer id = (Integer) request.getSession().getAttribute("uuid");
//        for(int i=0; i<songslist.size(); i++){
//            System.out.println(songslist);
//        }
        for (Song list : songslist) {
            LoveSong loveSong = new LoveSong();
            loveSong.setUser_id(id);
            loveSong.setSong_id(Long.parseLong(list.getSong_id().trim()));
            SongService ss = new SongServiceImpl();
            if (ss.selectLoveStateNow(loveSong)) {
                list.setConn("1");//区别已经喜欢
            } else {
                list.setConn("0");//未喜欢
            }
        }
        out.write(ResultUtil.ac("获取推荐歌曲",songslist));
    }


    /**
     * 查询喜欢状态
     */
    protected void lovestate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        long songid = Long.parseLong(getsong.getSong_id().trim());
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        LoveSong loveSong = new LoveSong();
        loveSong.setUser_id(id);
        loveSong.setSong_id(songid);
        SongService songService = new SongServiceImpl();
        if(songService.selectLoveState(loveSong)){
            out.write(ResultUtil.ac("已喜欢",1));
        }else{
            out.write(ResultUtil.ac("未喜欢",0));
        }

    }

    /**
     * 取消喜欢
     */
    protected void cancelLoveSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        long songid = Long.parseLong(getsong.getSong_id());
        LoveSong loveSong = new LoveSong();
        loveSong.setSong_id(songid);
        loveSong.setUser_id(id);
        if(songService.updateLoveWA(loveSong)){
            out.write(ResultUtil.ac("取消喜欢成功",null));
        }else{
            out.write(ResultUtil.wa("取消喜欢失败",null));
        }
    }

    /**
     * 喜欢歌曲
     */
    protected void loveSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        String sid = getsong.getSong_id();
        long songid = Long.parseLong(sid.trim());
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        LoveSong loveSong = new LoveSong();
        loveSong.setSong_id(songid);
        loveSong.setUser_id(id);
        if(songService.selectLoveState(loveSong)){ //表里已有关系数据，只需要修改状态
            System.out.println("有了");
            if(songService.updateLoveACC(loveSong)){
                out.write(ResultUtil.ac("已喜欢",null));
            }else{
                out.write(ResultUtil.wa("喜欢失败",null));
            }
        }else {
            System.out.println("没有");
            if(songService.updateLoveAC(loveSong)){
                out.write(ResultUtil.ac("已喜欢",null));
            }else{
                out.write(ResultUtil.wa("喜欢失败",null));
            }
        }
    }

    /**
     * 删除歌曲
     */
    protected void deleteSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Song getsong = JSON.parseObject(s, Song.class);
        SongService songService = new SongServiceImpl();
        Song song = new Song();
        song.setSong_id(getsong.getSong_id());
        if(songService.deleteSong(song)){
            out.write(ResultUtil.ac("删除歌曲成功",null));
        }else{
            out.write(ResultUtil.wa("删除歌曲失败",null));
        }
    }

    /**
     * 申请成为音乐人
     */
    protected void applySinger(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Singer getuser = JSON.parseObject(s, Singer.class);
//        System.out.println(getuser);
        Singer singer = new Singer();
        singer.setSinger_id((Integer) request.getSession().getAttribute("uuid"));
        singer.setSinger_name(getuser.getSinger_name());
        singer.setSinger_type(getuser.getSinger_type());
        singer.setSinger_brith(getuser.getSinger_brith());
        singer.setSinger_phone(getuser.getSinger_phone());
        singer.setSinger_detail(getuser.getSinger_detail());
        SongService songService = new SongServiceImpl();
        if(songService.addSinger(singer)){
            System.out.println("修改成功");
            out.write(JsonUtil.toJson(new Result(true, 200, "修改成功", null)));
        }else{
            System.out.println("修改失败");
            out.write(JsonUtil.toJson(new Result(false, 400, "修改失败", null)));
        }
    }

}
