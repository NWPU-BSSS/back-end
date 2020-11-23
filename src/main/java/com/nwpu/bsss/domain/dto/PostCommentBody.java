package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class PostCommentBody {

    private String content;
    private long commentId;
}
