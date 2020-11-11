package com.nwpu.bsss.domain.dto;

import org.springframework.stereotype.Component;

/**
 * data transfer obj
 * 专门用于发布博客接口request类型的映射
 */
@Component
public class ReleaseBlogBody {

    private String title;
    private long userId;
    private String content;

    public ReleaseBlogBody() {
    }

    public ReleaseBlogBody(String title, long userId, String content) {
        this.title = title;
        this.userId = userId;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
