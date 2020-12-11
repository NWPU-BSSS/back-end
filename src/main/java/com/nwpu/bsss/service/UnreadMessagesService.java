package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.UnreadMessagesEntity;


public interface UnreadMessagesService {

    UnreadMessagesEntity getUnreadMessagesByUserId(long userId);

    long createUnreadMessages(UnreadMessagesEntity unreadMessagesEntity);

    void deleteUnreadMessageById(long id);
}
