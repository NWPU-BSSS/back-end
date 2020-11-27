package com.nwpu.bsss.response;

import lombok.Data;

@Data
public class UserBaseInfoResponse {
    private String avatar;
    private String nickname;
    private int level;
    private int codeAge;
    private int BlogNum;
    private int followNum;
    private int fanNum;
}
