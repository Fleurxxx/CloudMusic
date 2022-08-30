package com.dao;
import com.entity.*;
import com.mapper.CommentMapper;
import com.mapper.SearchMapper;
import com.mapper.SongMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import com.util.MybatisUtils;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        //查询全部用户
        //第一步：获取SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            //1>getMapper
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> userList = userDao.getUserList();
            
            for (User user : userList) {
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test  //评论列表
    public void commentlist(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        Comment comment = new Comment();
        comment.setSong_id(23423);
        comment.setParentCommentId(-1);
        List<Comment> commentslist = mapper.selectComment(comment);
//        List<Comment> commentslist = mapper.listReplyComment(comment);
        System.out.println(commentslist.size());
        for(int i=0; i<commentslist.size(); i++){
            System.out.println(commentslist);
        }
        commentslist.forEach(list->{
            System.out.println(list.getReply_id());
            System.out.println(list.getReply_name());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test  //评论
    public void Comment(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        Comment comment = new Comment();
        comment.setComment_id(1);
        comment.setSong_id(12);
        comment.setUser_id(-1);
        comment.setReply_id(1);
        comment.setComment("你好");
        comment.setSend_time("2022-8-21 18:59");
        if(mapper.comment(comment)>0){
            System.out.println("评论成功");
        }else{
            System.out.println("评论失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test  //修改歌曲状态
    public void selectSongState(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        String id = "23423";
        int sid = 23423;
        boolean flag;
        Song song = new Song();
        song.setSong_singerid(1);
        song.setSong_id("23423");
        int x = mapper.passVerification(song);
        if(x>0){
            System.out.println(x);
            flag=true;
        }else{
            flag=false;
        }
        System.out.println(flag);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test //查找歌曲状态
    public void selectLoveStateNow(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        LoveSong ls = new LoveSong();
        ls.setUser_id(1);
        ls.setSong_id(23423);
        if(mapper.selectLoveStateNow(ls)==null){
            System.out.println("未喜欢");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test //删除歌曲
    public void deleteSong(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        long id = 234343;
        Song song = new Song();
        song.setSong_id("234343");
        int res = mapper.deleteSong(song);
        if(res>0){
            System.out.println("删除成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test  //待审核的歌曲列表
    public void selectUncheckeSongsList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        int userid = 1;
        List<Song> list = mapper.selectUncheckeSongsList();
        System.out.println(list.size());
        for(int i=0; i<list.size(); i++){
            System.out.println(list);
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test  //喜欢的歌曲列表
    public void selectLoveSongsList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        int userid = 1;
        List<Song> list = mapper.selectLoveSongsList(userid);
        System.out.println(list.size());
        for(int i=0; i<list.size(); i++){
            System.out.println(list);
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test  //歌曲列表
    public void selectSongsList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        int id = 1;
        List<Song> songlist = mapper.selectSongsList(id,6,100);
        System.out.println(songlist.size());
        for(int i=0; i<songlist.size(); i++){
            System.out.println(songlist);
        }
        songlist.forEach(list->{
            System.out.println(list.getSong_name());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//根据id查找歌曲信息
    public void getSongById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        Song song = mapper.getSongById("1");
        System.out.println(song.getSong_singername());
        sqlSession.close();
    }

    @Test//根据id查找个人信息
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = mapper.getUserById(1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test  //查询数据库里面是否有这条数据
    public void selectConcernState(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int id = 1;
        int focusid = 2;
        Fans fans = new Fans();
        fans.setFans_id(id);
        fans.setFans_focusid(focusid);
        boolean flag;
        if(mapper.selectConcernState(fans)){
            flag=true;
        }else{
            flag=false;
        }
        System.out.println(flag);
    }

    @Test  //获取关注/粉丝列表
    public void selectConcernLis(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int id = 1;
        int focusid = 2;
//        List<Fans> fanslist = mapper.selectConcernList(id);
        List<Fans> fanslist = mapper.selectFansList(id);
        System.out.println(fanslist.size());
        for(int i=0; i<fanslist.size(); i++){
            System.out.println(fanslist);
        }
        fanslist.forEach(list->{
            System.out.println(list.getFans_detail());
            System.out.println(list.getFans_fdetail());
            System.out.println(list.getFans_foname());
            System.out.println(list.getFans_type());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//模糊查询用户列表
    public void SearchUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SearchMapper mapper = sqlSession.getMapper(SearchMapper.class);
        Integer id = 1;
        String str = "花";
        SearchUser su = new SearchUser();
        su.setUuid(id);
        su.setConn(str);
        su.setNowpage(0);
        List<SearchUser> search = mapper.searchUser(su);
        System.out.println(search.size());
        for(int i=0; i<search.size(); i++){
            System.out.println(search);
        }
        search.forEach(list->{
            System.out.println(list.getFocusid());
            System.out.println(list.getFans());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//模糊查询歌手列表
    public void SearchSinger(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SearchMapper mapper = sqlSession.getMapper(SearchMapper.class);
        Integer id = 1;
        String str = "三";
        SearchUser su = new SearchUser();
        su.setUuid(id);
        su.setConn(str);
        su.setNowpage(0);
        List<SearchUser> search = mapper.searchSinger(su);
        System.out.println(search.size());
        for(int i=0; i<search.size(); i++){
            System.out.println(search);
        }
        search.forEach(list->{
            System.out.println(list.getFocusid());
            System.out.println(list.getFans());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//模糊查询歌曲列表
    public void searchSong(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SearchMapper mapper = sqlSession.getMapper(SearchMapper.class);
        String str = "车";
        Song song = new Song();
        song.setConn(str);
        song.setType(0);
        List<Song> search = mapper.searchSong(song);
        System.out.println(search.size());
        for(int i=0; i<search.size(); i++){
            System.out.println(search);
        }
        search.forEach(list->{
            System.out.println(list.getSong_path());
            System.out.println(list.getSong_singername());
        });
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }


    @Test  //插入/修改（关注）
    public void updateConcernWA(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int id = 1;
        int focusid = 2;
        int res = mapper.updateConcernAC(focusid,id);
        if(res>0){
//            System.out.println("插入成功");
            System.out.println("修改成功");
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }


    @Test//增删改需要提交事务
    public void addUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUser_id(4);
        user.setEmail("2@2");
        user.setPassword("123");
        user.setUser_phone(324);
        user.setUser_date("---");
        user.setUser_type(0);
        int res = mapper.addUser(user);
        if(res>0){
            System.out.println("插入成功");
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//封面/歌曲/歌词/歌曲信息
    public void updateSong(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SongMapper mapper = sqlSession.getMapper(SongMapper.class);
        Song song = new Song();
        song.setSong_singerid(1);
        song.setSong_state(0);
        song.setSong_id("234343");
        song.setSong_name("444");
        song.setLyric_path("555");
        song.setPhoto_path("5455");
//        int res = mapper.updateSong(song);
//        int res = mapper.updateSongLrc(song);
//        int res = mapper.updateSongImg(song);
        int res = mapper.addSongMsg(song);
        if(res>0){
            System.out.println("上传成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test //上传头像
    public void updateHeaderImg(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUser_id(2);
        user.setUser_photo("44444");
        int res = mapper.updateHeaderImg(user);
        if(res>0){
            System.out.println("上传成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test //修改改资料
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUser_id(1);
        user.setUser_name("彭润泽");
        user.setUser_phone(1231242354);
        int res = mapper.updateUser(user);
        if(res>0){
            System.out.println("修改成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test//修改密码
    public void updatePwd(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setEmail("123123123@qq.com");
        user.setPassword("1111");
        int res = mapper.updatePwd(user);
        if(res>0){
            System.out.println("修改成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUser_id(4);
        int res = mapper.deleteUser(user);
        if(res>0){
            System.out.println("删除成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getUserLike(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUserLike("彭");
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void checkUserName(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        Boolean user = mapper.checkUserName("彭");
        System.out.println(user);
        sqlSession.close();
    }
}
