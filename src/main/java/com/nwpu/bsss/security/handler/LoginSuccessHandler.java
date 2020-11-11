package com.nwpu.bsss.security.handler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    @ResponseBody
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , Authentication authentication) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}
