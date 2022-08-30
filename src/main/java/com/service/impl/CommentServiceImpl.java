package com.service.impl;

import com.entity.Comment;
import com.entity.Song;
import com.mapper.CommentMapper;
import com.mapper.SearchMapper;
import com.service.CommentService;
import com.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);

    @Override
    public boolean comment(Comment comm) {
        boolean flag;
        if(mapper.comment(comm)>0){
            flag=true;
        }else{
            flag=false;
        }
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public List<Comment> listCommentByBlogId(Comment comm) {
        List<Comment> list = mapper.selectComment(comm);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public List<Comment> listReplyComment(Comment comm) {
        List<Comment> list = mapper.listReplyComment(comm);
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    @Override
    public boolean deletecomment(Comment comm) {
        int res = mapper.deletecomment(comm);
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
