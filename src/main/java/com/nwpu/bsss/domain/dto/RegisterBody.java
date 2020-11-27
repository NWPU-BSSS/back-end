package com.nwpu.bsss.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegisterBody {
    private String username;
    private String password;
    private String email;
    private long verifyCode;
    private long phone;
}
