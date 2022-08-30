package com.entity;

public class LoveSong {
    private int state;
    private long song_id;
    private int user_id;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public long getSong_id() {
        return song_id;
    }

    public void setSong_id(long song_id) {
        this.song_id = song_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
