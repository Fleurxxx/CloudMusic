package com.service.impl;

import com.entity.SearchUser;
import com.entity.Song;
import com.mapper.SearchMapper;
import com.service.SearchService;
import com.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    SearchMapper mapper = sqlSession.getMapper(SearchMapper.class);

    @Override
    public List<SearchUser> searchUser(SearchUser searchUser) {
        List<SearchUser>searchUsers = mapper.searchUser(searchUser);
        sqlSession.commit();
        sqlSession.close();
        return searchUsers;
    }

    @Override
    public List<Song> searchSong(Song song) {
        List<Song>searchSongs = mapper.searchSong(song);
        sqlSession.commit();
        sqlSession.close();
        return searchSongs;
    }

    @Override
    public List<SearchUser> searchSinger(SearchUser searchUser) {
        List<SearchUser>searchUsers = mapper.searchSinger(searchUser);
        sqlSession.commit();
        sqlSession.close();
        return searchUsers;
    }
}
