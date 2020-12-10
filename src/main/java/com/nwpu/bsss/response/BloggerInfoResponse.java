package com.nwpu.bsss.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloggerInfoResponse {
    private String avatar;
    private long codeAge;
    private long level;
    private boolean verified;
    private String className;
    private long blogNum;
    private long fanNum;
    private long commentNum;
    private long favoriteNum;

}

