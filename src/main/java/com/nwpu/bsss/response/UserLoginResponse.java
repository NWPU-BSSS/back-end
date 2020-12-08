package com.nwpu.bsss.response;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String accessToken;
    private long userId;
}
