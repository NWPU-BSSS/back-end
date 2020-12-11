package com.nwpu.bsss.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author JiangZhe
 */
@Getter
@Setter
@Component
public class UpdateUserInfoBody {

    private String userName;
    private String nickname;
    private String introduction;
    private String realName;
    private String gender;
    private String university;
    private String className;
    private String academy;
}
