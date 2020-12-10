package com.nwpu.bsss.domain.dto;

import lombok.Data;

/**
 * @author alecHe
 * @desc ...
 * @date 2020-12-11 00:17:27
 */
@Data
public class JsonAvatarResponse {
    private String avatar;

    public JsonAvatarResponse(String avatar) {
        this.avatar = avatar;
    }
}
