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
    private String gender;
    private String university;
    private String academy;
    private String className;
    private Timestamp graduateTime;
    private long codeAge;
    private int level;
    private String avatar;
}
