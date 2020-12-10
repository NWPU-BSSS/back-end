package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class DeleteBlogBody {

    String admin;
    String password;
    String blogId;
}
