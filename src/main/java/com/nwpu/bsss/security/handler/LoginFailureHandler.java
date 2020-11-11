package com.nwpu.bsss.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    @ResponseBody
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , AuthenticationException e) {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
