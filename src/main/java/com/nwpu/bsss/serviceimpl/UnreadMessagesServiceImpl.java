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
    public long createUnreadMessages(UnreadMessagesEntity unreadMessagesEntity) {
        return unreadMessagesRepository.save(unreadMessagesEntity).getId();
    }

    @Override
    public void deleteUnreadMessageById(long id) {
        unreadMessagesRepository.delete(unreadMessagesRepository.findById(id));
    }

}
