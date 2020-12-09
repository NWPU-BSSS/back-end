package com.nwpu.bsss.domain.dto;

import com.nwpu.bsss.domain.BlogEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author liuziyu
 * @desc ...
 * @date 2020-12-08 11:32:54
 */
@Component
@Data
@Getter
@Setter
public class FavBlogJsonBody {

    protected long blogId;
    protected String title;
    protected String preview;
    protected long bloggerId;
    protected String nickname;
    protected Timestamp lastModifiedTime;

    public static FavBlogJsonBody parseJson(BlogEntity blog, String nickname) {
        FavBlogJsonBody res = new FavBlogJsonBody();
        final int maxWord = 200;

        String content = blog.getContent();
        res.blogId = blog.getId();
        res.title = blog.getTitle();
        res.preview = content.substring(0, Integer.min(content.length() - 1, maxWord));
        res.bloggerId = blog.getAuthorId();
        res.nickname = nickname;
        res.lastModifiedTime = blog.getLastModifiedTime();

        return res;
    }


}
