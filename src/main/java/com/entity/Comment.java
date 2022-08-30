package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private int comment_id;//评论内容id
    private int parentCommentId;  //上级评论id
    private long song_id;
    private int reply_id;//回复的人id
    private int user_id;//被回复的人id
    private int comm_state; //1评论,0删除
    private String comment;
    private String reply_name;
    private String user_name;
    private String reply_img;
    private String user_img;
    private String send_time;
    //回复评论
    private List<Comment> replyComments = new ArrayList<>();
    private Comment parentComment;
    private String user_state;//评论id与本歌歌手id是否一样
    private String reply_state;




    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getReply_state() {
        return reply_state;
    }

    public void setReply_state(String reply_state) {
        this.reply_state = reply_state;
    }

    public String getReply_img() {
        return reply_img;
    }

    public void setReply_img(String reply_img) {
        this.reply_img = reply_img;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public long getSong_id() {
        return song_id;
    }

    public void setSong_id(long song_id) {
        this.song_id = song_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComm_state() {
        return comm_state;
    }

    public void setComm_state(int comm_state) {
        this.comm_state = comm_state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReply_name() {
        return reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", song_id=" + song_id +
                ", reply_id=" + reply_id +
                ", user_id=" + user_id +
                ", comm_state=" + comm_state +
                ", comment='" + comment + '\'' +
                ", reply_name='" + reply_name + '\'' +
                ", user_name='" + user_name + '\'' +
                ", reply_img='" + reply_img + '\'' +
                ", user_img='" + user_img + '\'' +
                ", send_time='" + send_time + '\'' +
                '}';
    }
}
