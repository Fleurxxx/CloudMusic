package com.mapper;


import com.entity.SearchUser;
import com.entity.Song;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchMapper {
    //查询（根据输入内容查询用户）
    List<SearchUser> searchUser(SearchUser searchUser);

    //查询（根据输入内容查询用户）
    List<Song> searchSong(Song song);

    //查询（根据输入内容查询用户）
    List<SearchUser> searchSinger(SearchUser searchUser);
}
