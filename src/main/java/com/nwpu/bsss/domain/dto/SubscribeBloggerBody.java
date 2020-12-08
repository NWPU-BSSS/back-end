package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class SubscribeBloggerBody {
    private long bloggerId;
    private boolean subscribe;
}
