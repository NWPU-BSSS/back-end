package com.nwpu.bsss.domain.dto;

import com.nwpu.bsss.domain.BlogEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author nolzhou
 * @desc ...
 * @date 2020-12-08 17:15:13
 */
@Component
@Data
public class KeywordBlogJsonBody {
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
    long favoriteNum;
    long likeNum;
    long commentNum;

    public static KeywordBlogJsonBody parseJson(BlogEntity blog, String nickname, String avatar, long favoriteNum, long likeNum, long commentNum){

        KeywordBlogJsonBody res = new KeywordBlogJsonBody();
        final int maxWord = 100;
        String content = blog.getContent();

        res.blogId = blog.getId();
        res.userId = blog.getAuthorId();
        res.title = blog.getTitle();
        res.tagA = blog.getTagA();
        res.tagB = blog.getTagB();
        res.tagC = blog.getTagC();
        res.preview = content.substring(0,Integer.min(content.length(),maxWord));
        res.avatar = avatar;
        res.nickname = nickname;
        res.lastModifiedTime = blog.getLastModifiedTime();
        res.favoriteNum = favoriteNum;
        res.likeNum = likeNum;
        res.commentNum = commentNum;

        return res;
    }
}
