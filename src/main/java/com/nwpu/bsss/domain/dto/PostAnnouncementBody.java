package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class PostAnnouncementBody {

    String admin;
    String password;
    String title;
    String content;
    String endTime;
    String startTime;
}
