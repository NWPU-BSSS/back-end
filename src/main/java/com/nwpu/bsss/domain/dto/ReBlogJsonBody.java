package com.nwpu.bsss.domain.dto;

import com.nwpu.bsss.domain.BlogEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class ReBlogJsonBody {
    long blogId;
    long userId;
    String title;
    String tagA;
    String tagB;
    String tagC;
    String preview;
    String avatar;
    String nickname;
    Timestamp lastModifiedTime;
    int favoriteNum;
    int likeNum;
    int commentNum;

    public static ReBlogJsonBody parseJson(BlogEntity blog,String nickname,String avatar){

        ReBlogJsonBody res = new ReBlogJsonBody();

        res.blogId = blog.getId();
        res.userId = blog.getAuthorId();
        res.title = blog.getTitle();
        res.tagA = blog.getTagA();
        res.tagB = blog.getTagB();
        res.tagC = blog.getTagC();
        res.preview = blog.getContent();
        res.avatar = avatar;
        res.nickname = nickname;
        res.lastModifiedTime = blog.getLastModifiedTime();
        res.favoriteNum = 0;
        res.likeNum = 0;
        res.commentNum = 0;

        return res;

    }
//
//    public ReBlogJsonBody(BlogEntity input) {
//        this.blogId = input.getId();
//        this.userId = input.getAuthorId();
//        this.title = input.getTitle();
//        this.tagA = input.getTagA();
//        this.tagB = input.getTagB();
//        this.tagC = input.getTagC();
//        this.preview = input.getContent();
//        this.avatar = "";
//        this.nickname = "";
//        this.lastModifiedTime = input.getLastModifiedTime();
//        this.favoriteNum = 0;
//        this.likeNum = 0;
//        this.commentNum = 0;
//    }
}
