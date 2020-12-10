package com.nwpu.bsss.domain.dto;

import lombok.Data;

@Data
public class DeleteUserBody {

    String admin;
    String password;
    String userId;
}
