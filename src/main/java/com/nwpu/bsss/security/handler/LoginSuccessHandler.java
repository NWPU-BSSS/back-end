package com.nwpu.bsss.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwpu.bsss.response.Code;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , Authentication authentication) throws IOException {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        Map<String, Object> map = new HashMap<>();
        map.put("code", Code.OK);
        map.put("msg", "登录成功");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();
    }
}
