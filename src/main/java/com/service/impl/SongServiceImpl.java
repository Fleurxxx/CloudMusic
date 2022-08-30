package com.service.impl;

import com.entity.*;
import com.mapper.SongMapper;
import com.service.SongService;
import com.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SongServiceImpl implements SongService {

    //1.获取sqlSession对象
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    //2.获取Mapper接口的代理对象
    SongMapper mapper = sqlSession.getMapper(SongMapper.class);

    @Override
    public Song selectOfSongId(String songid) {
        Song song = mapper.getSongById(songid);
//        System.out.println(song);
        sqlSession.close();
        return song;
    }

    @Override
    public boolean updateSong(Song song) {
        int res = mapper.updateSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("上传歌曲"+flag);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateSongImg(Song song) {
        int res = mapper.updateSongImg(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("上传封面"+flag);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateSongLrc(Song song) {
        int res = mapper.updateSongLrc(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("上传歌词"+flag);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean addSongMsg(Song song) {
        int res = mapper.addSongMsg(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("上传歌曲"+flag);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }



    @Override
    public List<Song> selectSongsList(Integer userid,Integer page,Integer listRows) {
        List<Song> list = mapper.selectSongsList(userid,page,listRows);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Song> selectLoveSongsList(Integer userid) {
        List<Song> list = mapper.selectLoveSongsList(userid);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Song> selectreCommendSongsList() {
        List<Song> list = mapper.selectreCommendSongsList();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Song> selectUncheckeSongsList() {
        List<Song> list = mapper.selectUncheckeSongsList();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Song> selectPassSongsList() {
        List<Song> list = mapper.selectPassSongsList();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public boolean passVerification(Song song) {
        int res = mapper.passVerification(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean cancelPassVerification(Song song) {
        int res = mapper.cancelPassVerification(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean currentLimiting(Song song) {
        int res = mapper.currentLimiting(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean cancelCurrentLimiting(Song song) {
        int res = mapper.cancelCurrentLimiting(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean banSong(Song song) {
        int res = mapper.banSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean cancelBanSong(Song song) {
        int res = mapper.cancelBanSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean recommendSong(Song song) {
        int res = mapper.recommendSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean cancelRecommendSong(Song song) {
        int res = mapper.cancelRecommendSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean deleteSong(Song song) {
        int res = mapper.deleteSong(song);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("上传歌曲"+flag);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean selectLoveStateNow(LoveSong loveSong) {
        boolean flag;
        if(mapper.selectLoveStateNow(loveSong)==null){//未喜欢
            flag=false;
        }else{
            flag=true;//喜欢
        }
        return flag;
    }

    @Override
    public boolean selectLoveState(LoveSong loveSong) {
        boolean flag;
        if(mapper.selectLoveState(loveSong)){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateLoveAC(LoveSong loveSong) {
        int res = mapper.updateLoveAC(loveSong);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateLoveACC(LoveSong loveSong) {
        int res = mapper.updateLoveACC(loveSong);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean updateLoveWA(LoveSong loveSong) {
        int res = mapper.updateLoveWA(loveSong);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public boolean addSinger(Singer singer) {
        int res = mapper.addSinger(singer);
        boolean flag;
        if(res>0){
            flag = true;
        }else{
            flag = false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }


}
