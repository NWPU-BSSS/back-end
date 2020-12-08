package com.nwpu.bsss.controller;

import com.nwpu.bsss.response.BadgeNumResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeNumController {
    
    @GetMapping("/badgeNum")
    public MyResponseEntity<BadgeNumResponse> getBadgeNum(@RequestHeader("accessToken") String accessToken,
                                                          @RequestParam("userId") String userId){
        try {
            BadgeNumResponse badgeNumResponse = new BadgeNumResponse();
            return new MyResponseEntity<>(Code.OK, "ok", badgeNumResponse);
        }catch (NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "ID格式错误", null);
        }catch (NullPointerException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "不存在", null);
        }
    }
}
