package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UnreadMessagesEntity;
import com.nwpu.bsss.response.BadgeNumResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.UnreadMessagesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BadgeNumController {

    @Resource
    UnreadMessagesService unreadMessagesService;

    @GetMapping("/badgeNum")
    public MyResponseEntity<BadgeNumResponse> getBadgeNum(@RequestHeader("accessToken") String accessToken,
                                                          @RequestParam("userId") String userId){
        long uId = Long.parseLong(userId);
        BadgeNumResponse badgeNumResponse = new BadgeNumResponse();
        UnreadMessagesEntity unreadMessagesEntity = unreadMessagesService.getUnreadMessagesByUserId(uId);
        badgeNumResponse.setAnnouncement(unreadMessagesEntity.getAnnouncement());
        badgeNumResponse.setComment(unreadMessagesEntity.getComment());
        badgeNumResponse.setFollow(unreadMessagesEntity.getFollow());
        badgeNumResponse.setLike(unreadMessagesEntity.getLike());
        badgeNumResponse.setMessage(unreadMessagesEntity.getMessage());
        badgeNumResponse.setNotice(unreadMessagesEntity.getNotice());
        badgeNumResponse.setReply(unreadMessagesEntity.getReply());
        return new MyResponseEntity<>(Code.OK, "ok", badgeNumResponse);
    }
}
