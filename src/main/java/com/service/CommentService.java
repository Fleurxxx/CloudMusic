package com.service;

import com.entity.Comment;
import com.entity.Song;

import java.util.List;

public interface CommentService {
    //添加，评论
    boolean comment(Comment comm);
    //查询,获取全部一级评论
    List<Comment> listCommentByBlogId(Comment comm);
    //查询,获取全部二级评论
    List<Comment> listReplyComment(Comment comm);
    //删除，根据评论id删除评论
    boolean deletecomment(Comment comm);
}
