package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class PostBlogBody {

    private String title;
    private String content;
    private String tagA;
    private String tagB;
    private String tagC;
}
