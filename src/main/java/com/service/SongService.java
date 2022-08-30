package com.service;

import com.entity.*;

import java.util.List;

public interface SongService {
    //查询，根据歌曲id查询歌曲全部信息
    Song selectOfSongId(String songid);
    //添加，上传歌曲
    boolean updateSong(Song song);
    //添加，上传封面
    boolean updateSongImg(Song song);
    //添加，上传歌词
    boolean updateSongLrc(Song song);
    //添加，上传歌曲信息
    boolean addSongMsg(Song song);
    //查询，根据用户id查询用户所有歌曲信息
    List<Song> selectSongsList(Integer userid,Integer page,Integer listRows);
    //查询，根据用户id查询用户所有喜欢歌曲信息
    List<Song> selectLoveSongsList(Integer userid);
    //查询，查询推荐歌曲
    List<Song> selectreCommendSongsList();
    //查询，查询未审核歌曲
    List<Song> selectUncheckeSongsList();
    //查询，查询已审核歌曲
    List<Song> selectPassSongsList();
    //修改（审核状态）通过审核
    boolean passVerification(Song song);
    //修改（审核状态）取消通过审核
    boolean cancelPassVerification(Song song);
    //修改（审核状态）限流
    boolean currentLimiting(Song song);
    //修改（审核状态）取消限流
    boolean cancelCurrentLimiting(Song song);
    //修改（审核状态）禁止
    boolean banSong(Song song);
    //修改（审核状态）取消禁止
    boolean cancelBanSong(Song song);
    //修改（审核状态）推荐
    boolean recommendSong(Song song);
    //修改（审核状态）取消推荐
    boolean cancelRecommendSong(Song song);
    //删除，根据songid删除歌曲
    boolean deleteSong(Song song);
    //查询，判断是否喜欢过
    boolean selectLoveStateNow(LoveSong loveSong);
    //查询，判断存在数据
    boolean selectLoveState(LoveSong loveSong);
    //添加（喜欢状态）喜欢
    boolean updateLoveAC(LoveSong loveSong);
    //修改（喜欢状态）喜欢
    boolean updateLoveACC(LoveSong loveSong);
    //修改（喜欢状态）取消喜欢
    boolean updateLoveWA(LoveSong loveSong);
    //添加，成为音乐人
    boolean addSinger(Singer singer);
}
