package com.nwpu.bsss.response;

public class BadgeNumResponse {
    private int announcement;
    private int comment;
    private int follow;
    private int message;
    private int reply;
    private int notice;
    private int like;

    //TODO release2中，数据库暂时没有对应数据，无法得到具体数据，所以还没有写service层，冯凌畅 2020-11-22
    public BadgeNumResponse(){
        this.announcement = 1;
        this.comment = 1;
        this.follow = 1;
        this.message = 1;
        this.reply = 1;
        this.notice = 1;
        this.like = 1;
    }
    public int getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(int announcement) {
        this.announcement = announcement;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
