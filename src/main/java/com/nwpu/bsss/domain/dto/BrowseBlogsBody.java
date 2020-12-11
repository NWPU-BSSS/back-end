package com.nwpu.bsss.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiangZhe
 */
@Data
@Getter
@Setter
public class BrowseBlogsBody {
    long blogId;
    String title;

    public BrowseBlogsBody(long blogId, String title) {
        this.blogId = blogId;
        this.title = title;
    }
}
