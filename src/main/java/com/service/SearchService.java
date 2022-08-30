package com.service;

import com.entity.SearchUser;
import com.entity.Song;

import java.util.List;

public interface SearchService {

    /**
     * 查询（根据输入内容查询用户）
     */
    List<SearchUser> searchUser(SearchUser searchUser);


    /**
     * 查询（根据输入内容查询歌曲）
     */
    List<Song> searchSong(Song song);


    /**
     * 查询（根据输入内容查询歌手）
     */
    List<SearchUser> searchSinger(SearchUser searchUser);

}
