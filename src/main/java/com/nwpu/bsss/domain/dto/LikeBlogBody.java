package com.nwpu.bsss.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuziyu
 * @desc ...
 * @date 2020-12-10 10:09:14
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class LikeBlogBody {

    private long blogId;
    private boolean like;
}
