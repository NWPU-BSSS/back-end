package com.nwpu.bsss.controller;

import com.nwpu.bsss.response.BadgeNumResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BadgeNumController {
    
    @GetMapping("/badgeNum")
    public MyResponseEntity<BadgeNumResponse> getBadgeNum(@RequestHeader("accessToken") String accessToken){
        try {
            Long userId = UserController.token2Id.get(accessToken);
            if (userId == null) {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
            }
            BadgeNumResponse badgeNumResponse = new BadgeNumResponse();
            return new MyResponseEntity<>(Code.OK, "ok", badgeNumResponse);
        }catch (NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "ID格式错误", null);
        }catch (NullPointerException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "不存在", null);
        }
    }
}
