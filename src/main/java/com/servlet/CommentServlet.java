package com.servlet;

import com.alibaba.fastjson.JSON;
import com.entity.Comment;
import com.entity.LoveSong;
import com.entity.Song;
import com.service.CommentService;
import com.service.SongService;
import com.service.impl.CommentServiceImpl;
import com.service.impl.SongServiceImpl;
import com.util.GetID;
import com.util.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/commentServlet")
public class CommentServlet extends BaseServlet {

    /**
     * 发送评论
     */
    protected void sendComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Comment getcomm = JSON.parseObject(s, Comment.class);
        String commentid = new GetID().getid();
        Comment comment1 = new Comment();
        comment1.setComment_id(Integer.parseInt(commentid));
        comment1.setParentCommentId(getcomm.getParentCommentId());
        comment1.setSong_id(getcomm.getSong_id());
        comment1.setComment(getcomm.getComment());
        comment1.setSend_time(getcomm.getSend_time());
        comment1.setReply_id((Integer) request.getSession().getAttribute("uuid"));
        comment1.setUser_id(getcomm.getUser_id());
        System.out.println(getcomm.getParentCommentId()+"  " +getcomm.getUser_id());
        CommentService cs = new CommentServiceImpl();
        if(cs.comment(comment1)){
            out.write(ResultUtil.ac("已评论",null));
        }else{
            out.write(ResultUtil.wa("评论失败",null));
        }

    }


    /**
     * 初始化评论
     */
    protected void initComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Comment getcomm = JSON.parseObject(s, Comment.class);
        CommentService cs = new CommentServiceImpl();
        Comment comment = new Comment();
        comment.setSong_id(getcomm.getSong_id());
        comment.setParentCommentId(-1);
        comment.setComm_state(getcomm.getComm_state()*5);//当前显示评论页数
        List<Comment> comments = cs.listCommentByBlogId(comment);
        Integer id = (Integer) request.getSession().getAttribute("uuid");
        for (Comment list : comments) {
            Comment comment1 = new Comment();
            comment1.setSong_id(getcomm.getSong_id());
            comment1.setParentCommentId(list.getComment_id());
//            System.out.println("ttttt  "+list.getComment_id());
            CommentService cs1 = new CommentServiceImpl();
            List<Comment> replys = cs1.listReplyComment(comment1);
//            System.out.println("回复评论长度"+replys.size());
            list.setReplyComments(replys);
//            System.out.println(list.getReplyComments().size());
            for (Comment list1 : replys){
                if (id.equals(list1.getReply_id())){
                    list1.setReply_state("我");//不一样
                }else{
                    list1.setReply_state("*");
                }
            }
            if (id.equals(list.getReply_id())){
                list.setReply_state("我");//不一样
            }else{
                list.setReply_state("*");
            }
        }

        out.write(ResultUtil.ac("已查询到全部评论",comments));
    }


    /**
     * 删除评论
     */
    protected void deleteComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Comment getcomm = JSON.parseObject(s, Comment.class);
        CommentService cs = new CommentServiceImpl();
        Comment comment = new Comment();
        comment.setComment_id(getcomm.getComment_id());
        if(cs.deletecomment(comment)){
            out.write(ResultUtil.ac("删除评论成功",null));
        }else{
            out.write(ResultUtil.wa("删除评论失败",null));
        }
    }
}
