package com.mapper;

import com.entity.Comment;
import com.entity.Song;

import java.util.List;

public interface CommentMapper {

    //添加，评论
    int comment(Comment comm);

    //查询，根据歌曲id查找评论
    List<Comment> selectComment(Comment comm);

    //查询，根据歌曲id查找评论
    List<Comment> listReplyComment(Comment comm);

    //删除，根据评论id删除评论
    int deletecomment(Comment comm);
}
