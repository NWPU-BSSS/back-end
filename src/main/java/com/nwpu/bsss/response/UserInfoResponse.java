package com.nwpu.bsss.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String username;
    private String nickname;
    private String introduction;
    private String realName;
    private int gender;
    private String university;
    private String academy;
    private String className;
    private String graduateTime;
    private long codeAge;
    private int level;
    private String avatar;
}
