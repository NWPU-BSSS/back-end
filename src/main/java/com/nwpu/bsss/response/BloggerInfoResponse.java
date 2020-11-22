package com.nwpu.bsss.response;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.serviceimpl.UserServiceImpl;

import javax.persistence.Id;

//Todo：
public class BloggerInfoResponse {
    private String avatar;
    private long codeAge;
    private long level;
    private boolean verfied;
    private String className;
    private long blogNum;
    private long fanNum;
    private long commentNum;
    private long favoriteNum;
    public BloggerInfoResponse(String className){
        this.avatar = null;
        this.codeAge = 1;
        this.level = 1;
        this.verfied = true;
        this.className = className;
        this.blogNum = 0;
        this.fanNum = 0;
        this.commentNum = 0;
        this.favoriteNum = 0;
        this. favoriteNum = 0;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCodeAge() {
        return codeAge;
    }

    public void setCodeAge(long codeAge) {
        this.codeAge = codeAge;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public boolean isVerfied() {
        return verfied;
    }

    public void setVerfied(boolean verfied) {
        this.verfied = verfied;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(long blogNum) {
        this.blogNum = blogNum;
    }

    public long getFanNum() {
        return fanNum;
    }

    public void setFanNum(long fanNum) {
        this.fanNum = fanNum;
    }

    public long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(long commentNum) {
        this.commentNum = commentNum;
    }

    public long getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(long favoriteNum) {
        this.favoriteNum = favoriteNum;
    }
}

