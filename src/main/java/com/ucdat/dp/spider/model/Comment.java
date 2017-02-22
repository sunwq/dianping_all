package com.ucdat.dp.spider.model;

import java.util.List;

/**
 * Created by liyan on 16-12-9.
 */
public class Comment {
    private String user_url;
    private String user_id;
    private String user_name;
    private String user_rank;
    private String comment_rank;
    private String comment_desc;
    private List<String> comment_photos;
    private String comment_time;

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getComment_rank() {
        return comment_rank;
    }

    public void setComment_rank(String comment_rank) {
        this.comment_rank = comment_rank;
    }

    public String getComment_desc() {
        return comment_desc;
    }

    public void setComment_desc(String comment_desc) {
        this.comment_desc = comment_desc;
    }

    public List<String> getComment_photos() {
        return comment_photos;
    }

    public void setComment_photos(List<String> comment_photos) {
        this.comment_photos = comment_photos;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
