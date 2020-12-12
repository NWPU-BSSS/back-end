package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.UnreadMessagesEntity;
import com.nwpu.bsss.repository.UnreadMessagesRepository;
import com.nwpu.bsss.service.UnreadMessagesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UnreadMessagesServiceImpl implements UnreadMessagesService {

    @Resource
    UnreadMessagesRepository unreadMessagesRepository;

    @Override
    public UnreadMessagesEntity getUnreadMessagesByUserId(long userId) {
        return unreadMessagesRepository.findByUserId(userId);
    }

    @Override
    public long createUnreadMessages(UnreadMessagesEntity unreadMessagesEntity, long userId) {

        return unreadMessagesRepository.createUnreadMessage(userId, unreadMessagesEntity.getAnnouncement(),
                unreadMessagesEntity.getFollow(), unreadMessagesEntity.getMessage(),
                unreadMessagesEntity.getReply(), unreadMessagesEntity.getNotice(),
                unreadMessagesEntity.getLike(), unreadMessagesEntity.getComment());
    }

    @Override
    public void deleteUnreadMessageByUserId(long UserId) {
        unreadMessagesRepository.deleteUnreadMessage(UserId);
    }

}
