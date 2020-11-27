package com.nwpu.bsss.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 目前用于重建 filter
 */
@Getter
@Setter
@Component
public class LoginUserBody {
    private String username;
    private String email;
    private String password;
}
