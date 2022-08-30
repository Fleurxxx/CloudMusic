package com.mapper;

import com.entity.LoveSong;
import com.entity.Singer;
import com.entity.Song;
import com.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SongMapper {
    //查询，根据歌曲id查询歌曲全部信息
    Song getSongById(String songid);
    //添加，上传歌曲
    int updateSong(Song song);
    //修改，上传封面
    int updateSongImg(Song song);
    //修改，上传歌词
    int updateSongLrc(Song song);
    //修改，上传歌曲信息
    int addSongMsg(Song song);
    //查询，根据用户id查询用户所有歌曲信息
    List<Song> selectSongsList(@Param("userid") Integer userid,@Param("page") Integer page,@Param("listRows") Integer listRows);
    //查询，根据用户id查询用户所有歌曲信息
    List<Song> selectLoveSongsList(Integer userid);
    //查询，查询推荐歌曲
    List<Song> selectreCommendSongsList();
    //查询，查询未审核歌曲
    List<Song> selectUncheckeSongsList();
    //查询，查询未审核歌曲
    List<Song> selectPassSongsList();
    //修改（审核状态）通过审核
    int passVerification(Song song);
    //修改（审核状态）取消通过审核
    int cancelPassVerification(Song song);
    //修改（审核状态）限流
    int currentLimiting(Song song);
    //修改（审核状态）取消限流
    int cancelCurrentLimiting(Song song);
    //修改（审核状态）禁止
    int banSong(Song song);
    //修改（审核状态）取消禁止
    int cancelBanSong(Song song);
    //修改（审核状态）推荐
    int recommendSong(Song song);
    //修改（审核状态）取消推荐
    int cancelRecommendSong(Song song);
    //删除，根据songid删除歌曲
    int deleteSong(Song song);
    //查询，判断是否喜欢过
    Integer selectLoveStateNow(LoveSong loveSong);
    //查询，判断是否存在过数据
    boolean selectLoveState(LoveSong loveSong);
    //添加（喜欢状态）喜欢
    int updateLoveAC(LoveSong loveSong);
    //修改（喜欢状态）喜欢
    int updateLoveACC(LoveSong loveSong);
    //修改（喜欢状态）取消喜欢
    int updateLoveWA(LoveSong loveSong);
    //插入一个用户
    int addSinger(Singer singer);
}
