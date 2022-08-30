package com.entity;

public class Song {
    private String song_id;
    private String song_name;
    private int song_singerid;//歌手id
    private String song_singername;//歌手名
    private String song_path;//歌曲路径
    private String song_album;//专辑名
    private String song_lyric;//歌词
    private String song_date;//歌曲时长
    private int song_state;//状态，0审核，1正常，2限流，3禁止，4首页推荐
    private String song_tag;//标签
    private int type;//歌曲类型
    private String download_count;//下载次数
    private String play_count;//播放次数
    private String publish_date;//上传时间
    private String lyric_path;//歌词路径
    private String photo_path;//封面路径

    private String conn;

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getSong_singername() {
        return song_singername;
    }

    public void setSong_singername(String song_singername) {
        this.song_singername = song_singername;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public int getSong_singerid() {
        return song_singerid;
    }

    public void setSong_singerid(int song_singerid) {
        this.song_singerid = song_singerid;
    }

    public String getSong_path() {
        return song_path;
    }

    public void setSong_path(String song_path) {
        this.song_path = song_path;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public String getSong_lyric() {
        return song_lyric;
    }

    public void setSong_lyric(String song_lyric) {
        this.song_lyric = song_lyric;
    }

    public String getSong_date() {
        return song_date;
    }

    public void setSong_date(String song_date) {
        this.song_date = song_date;
    }

    public int getSong_state() {
        return song_state;
    }

    public void setSong_state(int song_state) {
        this.song_state = song_state;
    }

    public String getSong_tag() {
        return song_tag;
    }

    public void setSong_tag(String song_tag) {
        this.song_tag = song_tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDownload_count() {
        return download_count;
    }

    public void setDownload_count(String download_count) {
        this.download_count = download_count;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getLyric_path() {
        return lyric_path;
    }

    public void setLyric_path(String lyric_path) {
        this.lyric_path = lyric_path;
    }
}
